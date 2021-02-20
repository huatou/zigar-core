package com.zigar.zigarcore.cache;

import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.model.WeChatAccessToken;
import com.zigar.zigarcore.properties.WeChatProperties;
import com.zigar.zigarcore.resttemplate.WeChatRestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 微信的缓存
 */

@Component
public class WeChatCache {

    public static final String KEY_ACCESS_TOKEN = "zigarWeChatAccessToken";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private WeChatRestTemplate weChatRestTemplate;

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 获取回收端AccessToken到redis
     */
    public String getWeChatAccessToken() {

        String accessToken = redisTemplate.opsForValue().get(KEY_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            WeChatAccessToken weChatCollectAccessToken = weChatRestTemplate.getWeChatAccessToken(weChatProperties.getAppId(), weChatProperties.getAppSecret());
            if (!weChatCollectAccessToken.isSuccess()) {
                throw new BusinessLogicException("获取回收端AccessToken失败：" + weChatCollectAccessToken.getErrMsg());
            }
            setWeChatAccessToken(weChatCollectAccessToken.getAccessToken());
            return weChatCollectAccessToken.getAccessToken();
        }
        return accessToken;
    }

    /**
     * @param accessToken 设置AccessToken到redis
     */
    public void setWeChatAccessToken(String accessToken) {
        redisTemplate.opsForValue().set(KEY_ACCESS_TOKEN, accessToken);
    }


}
