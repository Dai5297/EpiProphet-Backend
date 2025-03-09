package com.epi.controller;

import com.epi.base.Result;
import com.epi.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
@Api(tags = "权限相关接口")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @ApiOperation(value = "当前用户获取权限")
    public Result getPermission() {
        List<String> permissions = permissionService.getCurrentUserPermission();
        return Result.success(permissions);
    }

}
