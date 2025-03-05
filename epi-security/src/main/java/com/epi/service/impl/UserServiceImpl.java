package com.epi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.epi.constant.SuperConstant;
import com.epi.entity.User;
import com.epi.exception.BaseException;
import com.epi.mapper.UserMapper;
import com.epi.service.UserService;
import com.epi.utils.BeanConv;
import com.epi.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVo findUserVoForLogin(String username) {
        UserVo userVo = UserVo.builder()
                .username(username)
                .dataState(SuperConstant.DATA_STATE_0)
                .build();

        User user = userMapper.findUserVoForLogin(userVo);
        if (ObjectUtil.isEmpty(user)){
            throw new BaseException("账号或密码错误");
        }
        return BeanConv.toBean(user, UserVo.class);
    }
}
