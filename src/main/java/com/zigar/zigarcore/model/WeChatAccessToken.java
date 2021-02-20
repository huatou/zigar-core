package com.zigar.zigarcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * app的accessToken，区别于user的accessToken
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WeChatAccessToken extends WeChatBaseResult {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

}
