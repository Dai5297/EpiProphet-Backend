package com.epi.mapper;

import com.epi.entity.User;
import com.epi.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserVoForLogin(UserVo userVo);
}
