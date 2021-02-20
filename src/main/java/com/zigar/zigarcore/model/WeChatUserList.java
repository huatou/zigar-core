package com.zigar.zigarcore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 微信公众号客户列表模型
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class WeChatUserList extends WeChatBaseResult {

    private Integer total;
    private Integer count;
    private WeChatUser data;
    private String next_openid;

    @Data
    public class WeChatUser {
        List<String> openid;
    }

}
