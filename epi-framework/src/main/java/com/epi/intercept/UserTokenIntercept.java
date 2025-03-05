package com.epi.intercept;

import cn.hutool.core.util.ObjectUtil;
import com.epi.constant.SecurityConstant;
import com.epi.constant.UserCacheConstant;
import com.epi.properties.JwtTokenManagerProperties;
import com.epi.utils.JwtUtil;
import com.epi.utils.UserThreadLocal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserTokenIntercept implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //  获取用户信息
        String userToken = request.getHeader(SecurityConstant.USER_TOKEN);
        if (ObjectUtil.isNotEmpty(userToken)){
            String jwtToken = redisTemplate.opsForValue().get(UserCacheConstant.JWT_TOKEN + userToken);
            if (ObjectUtil.isEmpty(jwtToken)){
                return false;
            }
            Object userObj = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtToken).get("currentUser");
            String currentUser = String.valueOf(userObj);
            UserThreadLocal.setSubject(currentUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
