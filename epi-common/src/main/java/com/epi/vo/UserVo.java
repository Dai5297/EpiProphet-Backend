package com.epi.vo;

import com.epi.base.BaseEntity;
import com.epi.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户对象
 * @author dai
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends BaseEntity {
    /**
     * 用户名
     */
    private String username;

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
}
