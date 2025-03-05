package com.epi.security;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.epi.constant.SecurityConstant;
import com.epi.constant.UserCacheConstant;
import com.epi.properties.JwtTokenManagerProperties;
import com.epi.utils.JwtUtil;
import com.epi.vo.UserVo;
import groovy.util.logging.Slf4j;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class JwtAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestAuthorizationContext) {
        //  用户当前请求路径
        String method = requestAuthorizationContext.getRequest().getMethod();
        String requestURI = requestAuthorizationContext.getRequest().getRequestURI();
        String targetUrl = (method+requestURI);

        //  更具uuid获取token
        String userToken = requestAuthorizationContext.getRequest().getHeader(SecurityConstant.USER_TOKEN);

        if (ObjectUtil.isEmpty(userToken)) {
            return new AuthorizationDecision(false);
        }

        String jwtToken = stringRedisTemplate.opsForValue().get(UserCacheConstant.JWT_TOKEN + userToken);

        if (ObjectUtil.isEmpty(jwtToken)){
            return new AuthorizationDecision(false);
        }

        //  解析token
        Claims cla = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtToken);

        if (ObjectUtil.isEmpty(cla)) {
            //  token失效
            return new AuthorizationDecision(false);
        }

        //  根据userName从Redis获取uuid
        UserVo userVo = JSONObject.parseObject(String.valueOf(cla.get("currentUser")),UserVo.class);
        String userName = userVo.getUsername();

        //用户剔除校验:redis中最新的userToken与出入的userToken不符合，则认为当前用户被后续用户剔除
        //key:username  value：uuid
        String currentUserToken = stringRedisTemplate.opsForValue().get(UserCacheConstant.USER_TOKEN + userName);

        if (!userToken.equals(currentUserToken)){
            return new AuthorizationDecision(false);
        }

        //  判断ttl是否少于10分钟 是则续期token
        Long remainTimeToLive = stringRedisTemplate.opsForValue().getOperations().getExpire(jwtToken);

        if (remainTimeToLive.longValue()<600){
            Map<String, Object> claim = new HashMap<>();
            claim.put("currentUser", String.valueOf(cla.get("currentUser")));
            String JwtToken = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(),
                    jwtTokenManagerProperties.getTtl(), claim);

            //  续期Redis中的token
            long ttl = Long.valueOf(jwtTokenManagerProperties.getTtl()) / 1000;
            stringRedisTemplate.opsForValue().set(UserCacheConstant.JWT_TOKEN + userToken, JwtToken, ttl, TimeUnit.SECONDS);
            stringRedisTemplate.expire(UserCacheConstant.USER_TOKEN + userVo.getUsername(), ttl, TimeUnit.SECONDS);
        }
        return new AuthorizationDecision(true);
    }
}
