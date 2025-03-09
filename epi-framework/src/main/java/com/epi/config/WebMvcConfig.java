package com.epi.config;

import com.epi.intercept.UserTokenIntercept;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserTokenIntercept userTokenIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  配置LocalThread的拦截器

        registry.addInterceptor(userTokenIntercept)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
