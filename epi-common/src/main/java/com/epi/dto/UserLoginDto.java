package com.epi.dto;

import lombok.Data;

/**
 * 用户登录dto
 * @author dai
 */

@Data
public class UserLoginDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
