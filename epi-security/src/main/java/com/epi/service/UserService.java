package com.epi.service;

import com.epi.entity.User;
import com.epi.vo.UserVo;

/**
 * 用户服务接口
 * @author dai
 */

public interface UserService {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return User
     */
    User loadUserByUserName(String userName);

    /**
     * 获取当前用户
     * @return UserVo
     */
    UserVo getCurrentUser();
}
