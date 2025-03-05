package com.epi.controller;

import com.epi.base.Result;
import com.epi.dto.UserLoginDto;
import com.epi.service.LoginService;
import com.epi.vo.UserVo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    @ApiImplicitParam(name = "userVo",value = "登录对象",required = true,dataType = "UserVo")
    @ApiOperationSupport(includeParameters ={"userVo.username","userVo.password"} )
    public Result<UserVo> login(@RequestBody UserLoginDto userLoginDto) {
        UserVo userVo =  loginService.login(userLoginDto);
        return Result.success(userVo);
    }
}
