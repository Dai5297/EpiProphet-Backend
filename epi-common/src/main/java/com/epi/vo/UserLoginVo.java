package com.epi.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.epi.base.BaseEntity;
import com.epi.entity.Role;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
public class UserLoginVo extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    /**
     * 用户类型(0:管理员,1:普通用户)
     */
    private Integer userType;

    /**
     * 状态(0:正常,1:禁用)
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 性别(0:男,1:女)
     */
    private Integer sex;


    /**
     * 唯一标识
     */
    private String uuid;

    /**
     * 角色
     */
    Role role;

    /**
     *
     */
    List<SimpleGrantedAuthority> resources;
}
