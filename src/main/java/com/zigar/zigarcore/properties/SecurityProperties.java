package com.zigar.zigarcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义security配置类
 *
 * @author Zigar
 * @createTime 2019-10-14 15:34:31
 * @description
 */


@Data
@ConfigurationProperties(prefix = SecurityProperties.PRE_FIX)
public class SecurityProperties {

    public static final String PRE_FIX = "zigar.security";

    //不需要登录就可访问的路径
//    private String[] permitAllUrls = {"/test", "/zigar/login", "/zigar/logout"};
    private String[] permitAllUrls = {"/*"};

    private String loginProcessingUrl = "/zigar/login";

    private String loginMethod = "POST";

}
