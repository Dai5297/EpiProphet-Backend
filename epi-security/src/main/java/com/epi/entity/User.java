package com.epi.entity;

import com.epi.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ApiModel(description = "用户实体类")
public class User extends BaseEntity {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    private String username;

    @ApiModelProperty(value = "密码（建议存储哈希值）", required = true, example = "hashed_password")
    private String password;

    @ApiModelProperty(value = "用户类型：0-管理人员，1-用户", required = true, example = "0")
    private Integer type;

    @ApiModelProperty(value = "用户昵称", required = true, example = "John Doe")
    private String nickName;

    @ApiModelProperty(value = "性别", required = true)
    private Integer sex;

    @ApiModelProperty(value = "启用/禁用", required = true)
    private Integer dataState;

    @ApiModelProperty(value = "头像地址", example = "http://example.com/avatar.jpg")
    private String avatar;

    @ApiModelProperty(value = "角色ID（关联角色表）", required = true, example = "1")
    private Integer role;

    @ApiModelProperty(value = "状态：0-停用，1-启用", required = true, example = "1")
    private Integer status;

    @ApiModelProperty(value = "邮箱", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "手机号", example = "12345678901")
    private String phone;

}
