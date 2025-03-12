package com.epi.mapper;

import com.epi.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色Mapper层
 * @author dai
 */

@Mapper
public interface RoleMapper {
    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    Role loadRoleByUser(Long userId);
}
