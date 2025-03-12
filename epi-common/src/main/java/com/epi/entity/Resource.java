package com.epi.entity;

import com.epi.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源实体
 * @author dai
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Resource extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

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
     * 删除标志(0代表存在 1代表删除)
     */
    private Integer delFlag;

    /**
     * 备注
     */
    private String remark;
}
