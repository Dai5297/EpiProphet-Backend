package com.epi.controller;

import com.epi.base.Result;
import com.epi.dto.UserLoginDto;
import com.epi.service.LoginService;
import com.epi.vo.UserLoginVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        Object token =  loginService.login(userLoginDto.getUsername(), userLoginDto.getPassword());
        return Result.success(token);
    }
}
