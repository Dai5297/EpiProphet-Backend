package com.epi.vo;

import com.epi.base.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserVo extends BaseVo {

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty(value = "选中节点")
    private String[] checkedIds;

    @ApiModelProperty(value = "查询用户：用户角色Ids")
    private Set<String> roleVoIds;

    @ApiModelProperty(value = "构建令牌：用户角色标识")
    private Set<String> roleLabels;

    @ApiModelProperty(value = "角色列表")
    private List<RoleVo> roleList;

    @ApiModelProperty(value = "构建令牌：用户权限路径")
    private Set<String> resourceRequestPaths;

    @ApiModelProperty(value = "角色Id【当前】")
    private Long roleId;

    @ApiModelProperty(value = "用户令牌")
    private String userToken;

    @ApiModelProperty(value = "数据状态(0:启用 1:禁用)")
    private String dataState;

    @Builder
    public UserVo(Long id, String dataState, String username, String password, String userType, String nickName, String post, String dept, String email, String realName, String mobile, String sex, String remark, String[] checkedIds, String openId, Set<String> roleVoIds, Set<String> roleLabels, List<RoleVo> roleList, Set<String> resourceRequestPaths, String deptNo, String postNo, Long roleId, String userToken, String dataState1) {
        super(id, dataState);
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.realName = realName;
        this.mobile = mobile;
        this.sex = sex;
        this.checkedIds = checkedIds;
        this.roleVoIds = roleVoIds;
        this.roleLabels = roleLabels;
        this.roleList = roleList;
        this.resourceRequestPaths = resourceRequestPaths;
        this.roleId = roleId;
        this.userToken = userToken;
        this.dataState = dataState1;
    }
}
