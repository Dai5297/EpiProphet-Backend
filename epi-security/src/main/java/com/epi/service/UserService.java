package com.epi.service;

import com.epi.vo.UserVo;

public interface UserService {

    UserVo findUserVoForLogin(String username);
}
