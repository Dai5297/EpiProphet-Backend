package com.epi.mapper;

import com.epi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper层
 * @author dai
 */

@Mapper
public interface UserMapper {

    User loadUserByUserName(String userName);
}
