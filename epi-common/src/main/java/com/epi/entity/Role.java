package com.epi.entity;

import com.epi.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * 用户实体
 * @author dai
 */
@Data
public class Role extends BaseEntity {
    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 角色状态(0正常 1停用)
     */
    private Character status;

    /**
     * 删除标志(0未删除 1已删除)
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 资源
     */
    private List<Resource> resources;
}
