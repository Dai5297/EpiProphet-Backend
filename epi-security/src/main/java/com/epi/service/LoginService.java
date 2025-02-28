package com.epi.service;

import com.epi.base.Result;
import com.epi.vo.UserLoginVo;

public interface LoginService {
    String login(String username, String password);
}
