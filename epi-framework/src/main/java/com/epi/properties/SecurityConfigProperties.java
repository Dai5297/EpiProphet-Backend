package com.epi.properties;

import groovy.util.logging.Slf4j;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@ConfigurationProperties(prefix = "epi.framework.security")
@Configuration
public class SecurityConfigProperties {

    String defaulePassword ;

    List<String> ignoreUrl = new ArrayList<>();

    List<String> origins = new ArrayList<>();

    String loginPage;

    //令牌有效时间
    Integer accessTokenValiditySeconds = 3*24*3600;

    //刷新令牌有效时间
    Integer refreshTokenValiditySeconds= 7*24*3600;
}
