package com.epi.mapper;

import com.epi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper层
 * @author dai
 */

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    User loadUserByUserName(String userName);
}
