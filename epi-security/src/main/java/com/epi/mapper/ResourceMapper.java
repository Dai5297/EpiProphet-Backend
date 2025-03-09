package com.epi.mapper;

import com.epi.entity.Resource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceMapper {

    List<Resource> getResourcesByRoleId(Long roleId);
}
