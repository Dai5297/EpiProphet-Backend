package com.epi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.epi.vo.LoginUserDetails;
import com.epi.entity.User;
import com.epi.exception.BaseException;
import com.epi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  获取用户信息
        User user = userService.loadUserByUserName(username);

        if(ObjectUtil.isNull(user) || ObjectUtil.isEmpty(user)) {
            throw new BaseException("用户不存在");
        }

        // TODO 获取用户权限
        List<String> menus =  new ArrayList<>();
        menus.add("user:add");
        menus.add("user:update");

        //  返回UserDetails对象
        return new LoginUserDetails(user, menus);
    }
}
