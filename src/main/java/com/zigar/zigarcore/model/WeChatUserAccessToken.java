package com.zigar.zigarcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zigar
 * @createTime 2020-07-04 11:16:25
 * @description 获取微信access_token的返回数据模型
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class WeChatUserAccessToken extends WeChatBaseResult {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("openid")
    private String openId;

    private String scope;

//    {
//      \"access_token\":\"36_txMjosFh884Iiams7nNgj92gysie4XFKvLjBPBtWkjd9SDP1Z3D9yEfOFaY5rlr9FnGV8PmYzzE4ckPqWOSvmIXANH0dYVk8Pa-5g8ngpnQ\",
//      \"expires_in\":7200,
//      \"refresh_token\":\"36_LLbEZOEfv5B3Zgkenv6lW7vtfI9QBnQS4_I_z3MYaD9p0TraXprHbA8xcoIQQQROHdUtmGjzWHS_4nY3O7EUiN0HnsiZINfu7DgldQ3M57s\",
//      \"openid\":\"oWzpdwXtdt2ynQsZR9Cp-UmWwZDI\",
//      \"scope\":\"snsapi_userinfo\"
//    }
}
