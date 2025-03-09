package com.epi.controller;

import com.epi.base.Result;
import com.epi.entity.User;
import com.epi.service.UserService;
import com.epi.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    @ApiOperation(value = "获取当前用户信息")
    public Result<UserVo> getCurrentUser() {
        return Result.success(userService.getCurrentUser());
    }
}
