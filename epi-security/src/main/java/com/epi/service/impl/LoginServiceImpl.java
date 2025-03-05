package com.epi.service.impl;

import com.epi.dto.UserLoginDto;
import com.epi.mapper.LoginMapper;
import com.epi.service.LoginService;
import com.epi.utils.BeanConv;
import com.epi.vo.UserAuth;
import com.epi.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserVo login(UserLoginDto loginDto) {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(upat);

        //认证Authentication对象获得内置对象UserAuth
        UserAuth userAuth = (UserAuth) authenticate.getPrincipal();
        userAuth.setPassword("");
        UserVo userVoResult = BeanConv.toBean(userAuth, UserVo.class);

        //  TODO 获取资源列表
        return null;
    }
}
