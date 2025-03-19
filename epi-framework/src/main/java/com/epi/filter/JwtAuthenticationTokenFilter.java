package com.epi.filter;

import com.alibaba.fastjson2.JSON;
import com.epi.constant.SecurityConstant;
import com.epi.constant.UserCacheConstant;
import com.epi.exception.BaseException;
import com.epi.properties.JwtTokenManagerProperties;
import com.epi.security.UserAuthorizationManager;
import com.epi.utils.JwtUtil;
import com.epi.vo.UserLoginVo;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Jwt请求校验过滤器
 * @author dai
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  判断是否为登录请求
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        //  判断token是否存在
        String token = request.getHeader(SecurityConstant.USER_TOKEN);
        if (StringUtil.isNullOrEmpty(token)) {
            throw new BaseException("Token不存在");
        }
        //  获取Jwt
        String jwt = (String) redisTemplate.opsForValue().get(UserCacheConstant.JWT_TOKEN + token);
        Claims claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwt);
        UserLoginVo userLoginVo = JSON.parseObject(String.valueOf(claims.get("user")), UserLoginVo.class);

        String uuid = (String) redisTemplate.opsForValue().get(UserCacheConstant.USER_TOKEN + userLoginVo.getUsername());
        if (!token.equals(uuid)) {
            redisTemplate.delete(UserCacheConstant.USER_TOKEN + userLoginVo.getUsername());
            throw new BaseException("Token已失效");
        }

        //  续期redis中token
        Long remainTimeToLive = redisTemplate.opsForValue().getOperations().getExpire(UserCacheConstant.JWT_TOKEN + token);
        if (remainTimeToLive.longValue()<= 600){
            //jwt生成的token也会过期，所以需要重新生成jwttoken
            Map<String, Object> claim = new HashMap<>();
            String userVoJsonString = String.valueOf(claims.get("currentUser"));
            claim.put("currentUser", userVoJsonString);

            //jwtToken令牌颁布
            String newJwtToken = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claim);
            long ttl = Long.valueOf(jwtTokenManagerProperties.getTtl()) / 1000;

            redisTemplate.opsForValue().set(UserCacheConstant.JWT_TOKEN + token, newJwtToken, ttl, TimeUnit.SECONDS);
            redisTemplate.expire(UserCacheConstant.USER_TOKEN + userLoginVo.getUsername(), ttl, TimeUnit.SECONDS);
        }

        //  获取请求用户的权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String resource : userLoginVo.getResources()) {
            authorities.add(new SimpleGrantedAuthority(resource));
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userLoginVo,null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
