package com.epi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.epi.entity.Resource;
import com.epi.service.PermissionService;
import com.epi.service.RoleService;
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
import java.util.stream.Collectors;

/**
 * 用户详情服务实现类
 * @author dai
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  获取用户信息
        User user = userService.loadUserByUserName(username);

        if(ObjectUtil.isNull(user) || ObjectUtil.isEmpty(user)) {
            throw new BaseException("用户不存在");
        }

        //  添加用户的角色
        user.setRole(roleService.loadRoleByUser(user));
        //  获取用户的权限
        List<Resource> resourcesByRole = permissionService.getResourcesByRole(user.getRole());
        List<String> menus= resourcesByRole.stream().map(Resource::getPath).toList();

        //  返回UserDetails对象
        return new LoginUserDetails(user, menus);
    }
}
