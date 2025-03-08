package com.epi.entity;

import com.epi.base.BaseEntity;
import lombok.Data;

/**
 * 资源实体
 */
@Data
public class Resource extends BaseEntity {

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单状态(0显示 1隐藏)
     */
    private Character visible;

    /**
     * 菜单状态(0正常 1停用)
     */
    private Character status;

    /**
     * 权限标志
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 删除标记(0未删除 1已删除)
     */
    private Integer defFlag;

    /**
     * 备注
     */
    private String remark;
}
