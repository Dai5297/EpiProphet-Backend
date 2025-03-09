package com.epi.service;

import com.epi.entity.Resource;
import com.epi.entity.Role;

import java.util.List;

public interface PermissionService {

    /**
     * 根据角色获取对应权限
     * @param role
     * @return
     */
    List<Resource> getResourcesByRole(Role role);

    /**
     * 获取当前用户权限
     */
    List<String> getCurrentUserPermission();
}
