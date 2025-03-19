package com.epi.config;

import com.epi.filter.JwtAuthenticationTokenFilter;
import com.epi.properties.SecurityConfigProperties;
import com.epi.security.UserAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * Security配置类
 * @author dai
 */

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private UserAuthorizationManager authorizationManager;

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        List<String> ignoreUrl = securityConfigProperties.getIgnoreUrl();
        //  关闭csrf
        http.csrf(AbstractHttpConfigurer::disable);
        //  配置请求拦截方式
        //  requestsMatchers() 表示某个请求不用认证
        http.authorizeHttpRequests(auth->
                auth
                        //  放行登录
                        .requestMatchers(ignoreUrl.toArray(new String[ignoreUrl.size()])).permitAll()
                        //  验证其余所有请求
                        .anyRequest().access(authorizationManager)
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
