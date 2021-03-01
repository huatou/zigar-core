package com.zigar.zigarcore.properties;

import cn.hutool.core.util.ArrayUtil;
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
    private String[] permitAllUrls = {"/test",};

    //默认不需要登录接口访问的url地址
    private String[] defaultPermitAllUrls = {"/api/zigarcore/user/register", "/", "/zigarcore"};

    private String loginProcessingUrl = "/api/zigarcore/login";

    private String loginMethod = "POST";

    public String[] getPermitAllUrls() {
        return ArrayUtil.addAll(permitAllUrls, defaultPermitAllUrls);
    }
}
