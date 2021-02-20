package com.zigar.zigarcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zigar
 * @createTime 2020-07-10 15:10:47
 * @description 微信公众号配置类
 */

@Data
@ConfigurationProperties(prefix = WeChatProperties.PRE_FIX)
public class WeChatProperties {

    public static final String PRE_FIX = "zigar.wechat";

    private String encodingAesKey;

    private String token;

    private String appId;

    private String appSecret;


}
