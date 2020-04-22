package com.zigar.zigarcore.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zigar
 * @createTime 2020-01-17 11:01:33
 * @description
 */

@Configuration
@EnableConfigurationProperties({
        SecurityProperties.class,
        JwtProperties.class,
        WebProperties.class,
        FileProperties.class})
public class PropertiesConfiguration {
}
