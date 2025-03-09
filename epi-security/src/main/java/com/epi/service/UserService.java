package com.epi.service;

import com.epi.entity.User;
import com.epi.vo.UserVo;

public interface UserService {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    User loadUserByUserName(String userName);

    /**
     * 获取当前用户
     * @return
     */
    UserVo getCurrentUser();
}
