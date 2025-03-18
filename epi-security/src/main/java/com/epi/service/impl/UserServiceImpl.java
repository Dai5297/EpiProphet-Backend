package com.epi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.epi.entity.User;
import com.epi.mapper.UserMapper;
import com.epi.service.UserService;
import com.epi.utils.UserThreadLocal;
import com.epi.vo.UserLoginVo;
import com.epi.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * @author dai
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loadUserByUserName(String userName) {
        return userMapper.loadUserByUserName(userName);
    }

    @Override
    public UserVo getCurrentUser() {
        String subject = UserThreadLocal.getSubject();
        UserLoginVo userLoginVo = JSON.parseObject(subject, UserLoginVo.class);
        return BeanUtil.toBean(userLoginVo, UserVo.class);
    }
}
