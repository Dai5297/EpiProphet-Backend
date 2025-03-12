package com.epi.service;

import com.epi.entity.Role;
import com.epi.entity.User;

/**
 * 角色服务接口
 * @author dai
 */

public interface RoleService {

    /**
     * 根据用户查询角色
     * @param user
     * @return
     */
    Role loadRoleByUser(User user);
}
