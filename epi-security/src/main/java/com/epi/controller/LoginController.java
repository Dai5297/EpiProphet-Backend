package com.epi.controller;

import com.epi.base.Result;
import com.epi.dto.UserLoginDto;
import com.epi.service.LoginService;
import com.epi.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 * @author dai
 */

@RestController
@RequestMapping
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Result<UserLoginVo> login(@RequestBody UserLoginDto loginDto) {
        UserLoginVo userLoginVo = loginService.login(loginDto);
        return Result.success(userLoginVo);
    }
}
