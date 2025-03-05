package com.epi.config;

import com.epi.properties.SecurityConfigProperties;
import com.epi.security.JwtAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    SecurityConfigProperties securityConfigProperties;

    @Autowired
    JwtAuthorizationManager jwtAuthorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //忽略地址
        List<String> ignoreUrl = securityConfigProperties.getIgnoreUrl();
        http.authorizeHttpRequests()
                .requestMatchers( ignoreUrl.toArray(new String[0]) )
                .permitAll()
                .anyRequest().access(jwtAuthorizationManager);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS );//关闭session
        http.headers().cacheControl().disable();//关闭缓存

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * BCrypt密码编码
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
