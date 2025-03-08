package com.epi.service.impl;

import com.epi.entity.User;
import com.epi.mapper.UserMapper;
import com.epi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loadUserByUserName(String userName) {
        return userMapper.loadUserByUserName(userName);
    }
}
