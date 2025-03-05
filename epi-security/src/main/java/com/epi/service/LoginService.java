package com.epi.service;

import com.epi.dto.UserLoginDto;
import com.epi.vo.UserVo;

public interface LoginService {
    UserVo login(UserLoginDto userLoginDto);
}
