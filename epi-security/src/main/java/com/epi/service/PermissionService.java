package com.epi.service;

import com.epi.entity.Resource;
import com.epi.entity.Role;

import java.util.List;

/**
 * 权限服务接口
 * @author dai
 */

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
