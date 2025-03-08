package com.epi.entity;

import com.epi.base.BaseEntity;
import lombok.Data;

/**
 * 用户实体
 */
@Data
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态(0停用 1启用)
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 性别(0男 1女 2未知)
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型(0管理员 1普通用户)
     */
    private Integer userType;

    /**
     * 删除标志(0代表存在 1代表删除)
     */
    private Integer deleted;

    /**
     * 角色
     */
    private Role role;

}
