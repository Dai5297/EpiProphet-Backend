package com.epi.mapper;

import com.epi.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User loadUserByUserName(String userName);
}
