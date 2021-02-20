package com.zigar.zigarcore.model;

import lombok.Data;

/**
 * 微信公众号发送到客户的消息迷行
 */
@Data
public class WeChatSendMessage {

    private String touser;
    private String msgtype;
    private TextContent text;

    @Data
    public class TextContent {
        private String content;

        public TextContent(String content) {
            this.content = content;
        }
    }

    public WeChatSendMessage(String touser, String content) {
        this.touser = touser;
        this.msgtype = "text";
        this.text = new TextContent(content);
    }
}
