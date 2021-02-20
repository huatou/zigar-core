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
        FileProperties.class,
        JwtProperties.class,
        SecurityProperties.class,
        SystemProperties.class,
        WebProperties.class,
        WeChatProperties.class,
})
public class PropertiesConfiguration {
}
