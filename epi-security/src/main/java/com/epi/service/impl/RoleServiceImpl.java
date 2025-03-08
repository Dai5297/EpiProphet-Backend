package com.epi.service.impl;

import com.epi.entity.Role;
import com.epi.entity.User;
import com.epi.mapper.RoleMapper;
import com.epi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role loadRoleByUser(User user) {
        return roleMapper.loadRoleByUser(user.getId());
    }
}
