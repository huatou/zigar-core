package com.zigar.zigarcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zigar
 * @createTime 2020-07-10 15:10:47
 * @description 支付宝配置
 */

@Data
@ConfigurationProperties(prefix = SystemProperties.PRE_FIX)
public class SystemProperties {

    public static final String PRE_FIX = "zigar.system";

    /**
     * 本系统域名
     */
    private String domain;

    /**
     * 系统名称
     */
    private String name;

}
