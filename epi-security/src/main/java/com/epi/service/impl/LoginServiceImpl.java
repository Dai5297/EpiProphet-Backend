package com.epi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.epi.constant.UserCacheConstant;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(upat);
        } catch (AuthenticationException e) {
            throw new BaseException("401", "用户名或密码错误");
        }
        //  3.获取登录用户详情
        LoginUserDetails principal = (LoginUserDetails) authenticate.getPrincipal();
        User user = principal.getUser();
        //  4.消除敏感数据
        user.setRole(roleService.loadRoleByUser(user));
        user.setPassword(null);
        //  5.转换为VO
        UserLoginVo loginVo = BeanUtil.toBean(user, UserLoginVo.class);
        loginVo.setResources(principal.getMenus());
        //  6.生成JWT UUID
        String uuid = UUID.randomUUID().toString();
        loginVo.setUuid(uuid);
        String jsonStr = JSONUtil.toJsonStr(loginVo);
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", jsonStr);
        String jwt = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claims);
        //  7.将UUID JWT存入redis 并移除之前的登录信息
        String prevUuid = String.valueOf(redisTemplate.opsForValue().get(UserCacheConstant.USER_TOKEN + user.getUsername()));
        if (!ObjectUtil.isNull(prevUuid)){
            redisTemplate.delete(UserCacheConstant.JWT_TOKEN + prevUuid);
        }
        long ttl = Long.valueOf(jwtTokenManagerProperties.getTtl()) / 1000;
        redisTemplate.opsForValue().set(UserCacheConstant.USER_TOKEN + user.getUsername(), uuid, ttl, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(UserCacheConstant.JWT_TOKEN + uuid, jwt, ttl, TimeUnit.SECONDS);
        return loginVo;
    }
}
