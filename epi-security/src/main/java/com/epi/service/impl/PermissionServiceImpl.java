package com.epi.service.impl;

import com.alibaba.fastjson2.JSON;
import com.epi.base.LocalThreadEntity;
import com.epi.entity.Resource;
import com.epi.entity.Role;
import com.epi.mapper.ResourceMapper;
import com.epi.service.PermissionService;
import com.epi.utils.UserThreadLocal;
import com.epi.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限服务实现类
 * @author dai
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> getResourcesByRole(Role role) {
        Long id = role.getId();
        return resourceMapper.getResourcesByRoleId(id);
    }

    @Override
    public List<String> getCurrentUserPermission() {
        String subject = UserThreadLocal.getSubject();
        UserLoginVo userLoginVo = JSON.parseObject(subject, UserLoginVo.class);
        List<Resource> resourcesByRole = getResourcesByRole(userLoginVo.getRole());
        return resourcesByRole.stream().map(Resource::getPerms).toList();
    }
}
