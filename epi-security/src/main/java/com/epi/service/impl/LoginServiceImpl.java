package com.epi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.epi.base.Result;
import com.epi.entity.User;
import com.epi.exception.BaseException;
import com.epi.mapper.LoginMapper;
import com.epi.service.LoginService;
import com.epi.utils.JwtUtil;
import com.epi.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${epi.framework.jwt.base64-encoded-secret-key}")
    private String jwtSecret;

    @Value("${epi.framework.jwt.ttl}")
    private int jwtExpiration;

    @Override
    public String login(String username, String password) {
        User user = loginMapper.getByUserName(username);
        //  TODO 完善判断机制
        if (ObjectUtil.isNull(user) || ObjectUtil.isEmpty(user)) {
            return "用户不存在";
        }
        if (!user.getPassword().equals(password)) {
            return "密码错误";
        }
        //  防止同时登陆
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(username, uuid);
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user.getUsername());
        String token = JwtUtil.createJWT(jwtSecret, jwtExpiration, claims);
        redisTemplate.opsForValue().set(uuid, token);
        return token;
    }
}
