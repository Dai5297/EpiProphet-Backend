package com.epi.service;

import com.epi.entity.User;

public interface UserService {

    User loadUserByUserName(String userName);
}
