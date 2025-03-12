package com.epi.config;

import com.epi.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 * @author dai
 */

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        //  关闭csrf
        http.csrf(csrf -> csrf.disable());
        //  配置请求拦截方式
        //  requestsMatchers() 表示某个请求不用认证
        http.authorizeHttpRequests(auth->
                auth
                        //  放行登录
                        .requestMatchers("/login").permitAll()
                        //  验证其余所有请求
                        .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtAuthenticationTokenFilter,  UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * 获取AuthenticationManager对象 登录时需要调用Authentication的Authenticate方法
     *
     * @param config
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 设置加密方式为Bcrypt
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
