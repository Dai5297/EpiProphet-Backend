package com.epi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.epi.properties.JwtTokenManagerProperties;
import com.epi.utils.JwtUtil;
import com.epi.vo.LoginUserDetails;
import com.epi.dto.UserLoginDto;
import com.epi.entity.User;
import com.epi.exception.BaseException;
import com.epi.service.LoginService;
import com.epi.service.RoleService;
import com.epi.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public UserLoginVo login(UserLoginDto loginDto) {
        //  1.调用Authentication中的Authenticate方法进行认证
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        //  2.实际是调用重写的UserDetailsImpl中的重写方法loadUserByUsername 最后会返回一个UserDetails对象(LoginUser) 存入Authentication的Principal
        Authentication authenticate = authenticationManager.authenticate(upat);
        if (ObjectUtil.isEmpty(authenticate) || ObjectUtil.isEmpty(authenticate.getPrincipal())){
            throw new BaseException("账号或密码错误");
        }
        //  3.获取登录用户详情
        LoginUserDetails principal = (LoginUserDetails) authenticate.getPrincipal();
        User user = principal.getUser();
        //  4.添加用户的角色并消除敏感数据
        user.setRole(roleService.loadRoleByUser(user));
        user.setPassword(null);
        //  5.转换为VO
        UserLoginVo loginVo = BeanUtil.toBean(user, UserLoginVo.class);
        loginVo.setResources((List<SimpleGrantedAuthority>) principal.getAuthorities());
        //  6.生成JWT UUID
        String uuid = UUID.randomUUID().toString();
        loginVo.setUuid(uuid);
        String jsonStr = JSONUtil.toJsonStr(loginVo);
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", jsonStr);
        String jwt = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claims);
        //  7.将UUID JWT存入redis
        redisTemplate.opsForValue().set(user.getUserName(), uuid);
        redisTemplate.opsForValue().set(uuid, jwt);
        return loginVo;
    }
}
