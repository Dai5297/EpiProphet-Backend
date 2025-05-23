package com.epi.mapper;

import com.epi.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资源Mapper层
 *
 * @author dai
 */

@Mapper
public interface ResourceMapper {

    /**
     * 根据角色id获取资源
     *
     * @param roleId
     * @return
     */
    List<Resource> getResourcesByRoleId(Long roleId);
}
