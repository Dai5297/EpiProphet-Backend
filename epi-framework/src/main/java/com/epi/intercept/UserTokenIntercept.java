package com.epi.intercept;

import com.alibaba.fastjson2.JSON;
import com.epi.constant.SecurityConstant;
import com.epi.constant.UserCacheConstant;
import com.epi.properties.JwtTokenManagerProperties;
import com.epi.utils.JwtUtil;
import com.epi.utils.StringUtils;
import com.epi.utils.UserThreadLocal;
import com.epi.vo.UserLoginVo;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserTokenIntercept implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader(SecurityConstant.USER_TOKEN);
        if (!StringUtil.isNullOrEmpty(header)){
            String jwt = String.valueOf(redisTemplate.opsForValue().get(UserCacheConstant.JWT_TOKEN + header));
            if (!StringUtil.isNullOrEmpty(jwt)){
                Claims claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwt);
                String userJson = String.valueOf(claims.get("user"));
                UserThreadLocal.setSubject(userJson);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.removeSubject();
    }
}
