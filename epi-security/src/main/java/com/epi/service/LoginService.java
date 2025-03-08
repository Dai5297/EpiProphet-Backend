package com.epi.service;

import com.epi.dto.UserLoginDto;
import com.epi.vo.UserLoginVo;

public interface LoginService {
    /**
     * 登录
     * @param loginDto
     * @return
     */
    UserLoginVo login(UserLoginDto loginDto);
}
