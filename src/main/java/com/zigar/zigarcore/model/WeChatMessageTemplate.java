package com.zigar.zigarcore.model;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信公众号发送到客户的消息迷行
 */
@Data
@NoArgsConstructor
public class WeChatMessageTemplate {

    /**
     * 注意：消息模板需要在公众平台中加入后才可以使用
     */

    /**
     * 订单创建消息模板ID
     * {{first.DATA}}
     * 时间：{{keyword1.DATA}}
     * 商品名称：{{keyword2.DATA}}
     * 订单号：{{keyword3.DATA}}
     * {{remark.DATA}}
     */
    public static final String ORDER_CREATE_TEMPLATE_ID = "hJoVfC0wloToyEz1FdShpoILWHsET0xNnbB9q5IFJzI";


    /**
     * 订单审核消息模板
     * {{first.DATA}}
     * 订单编号：{{keyword1.DATA}}
     * 审核时间：{{keyword2.DATA}}
     * 审核结果：{{keyword3.DATA}}
     * {{remark.DATA}}
     */
    public static final String ORDER_CHECK_TEMPLATE_ID_ = "s4B7z1WhnVN4qYFmBjWRSPohQ8phNQge6loA550FYD8";

    /**
     * 零钱入账通知消息模板
     * {{first.DATA}}
     * 入账金额：{{keyword1.DATA}}
     * 入账时间：{{keyword2.DATA}}
     * 入账类型：{{keyword3.DATA}}
     * 入账详情：{{keyword4.DATA}}
     * 交易单号：{{keyword5.DATA}}
     * {{remark.DATA}}
     */
    public static final String MONEY_GET_TEMPLATE_ID_ = "SsRgABeJ6tnfNz-Wqo4Ya03yZglWSYn72Nxn4LJMfQY";


    /**
     * 发送的客户openid
     */
    private String touser;
    /**
     * 消息模板Id
     */
    private String template_id;
    /**
     * 客户点击消息跳转的url
     */
    private String url;
    private Data data;

    @lombok.Data
    public class Data {
        private DataItem first;
        private DataItem keyword1;
        private DataItem keyword2;
        private DataItem keyword3;
        private DataItem keyword4;
        private DataItem keyword5;
        private DataItem remark;

        public Data(String first, String keyword1, String keyword2, String keyword3, String remark) {
            this.first = new DataItem(first);
            this.keyword1 = new DataItem(keyword1);
            this.keyword2 = new DataItem(keyword2);
            this.keyword3 = new DataItem(keyword3);
            this.remark = new DataItem(remark, "blue");
        }

        public Data(String first, String keyword1, String keyword2, String keyword3, String keyword4, String keyword5, String remark) {
            this.first = new DataItem(first);
            this.keyword1 = new DataItem(keyword1);
            this.keyword2 = new DataItem(keyword2);
            this.keyword3 = new DataItem(keyword3);
            this.keyword4 = new DataItem(keyword4);
            this.keyword5 = new DataItem(keyword5);
            this.remark = new DataItem(remark, "blue");
        }

    }

    @lombok.Data
    public class DataItem {
        private String value;
        private String color;

        public DataItem(String value) {
            this.value = value;
            this.color = "#173177";
        }

        public DataItem(String value, String color) {
            this.value = value;
            this.color = color;
        }

    }

    public WeChatMessageTemplate(String touser, String template_id, String url, String first, String keyword1, String keyword2, String keyword3, String remark) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.data = new Data(first, keyword1, keyword2, keyword3, remark);
    }

    public WeChatMessageTemplate(String touser, String template_id, String url, String first, String keyword1, String keyword2, String keyword3, String keyword4, String keyword5, String remark) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.data = new Data(first, keyword1, keyword2, keyword3, keyword4, keyword5, remark);
    }



    /*    {
        "touser":"OPENID",
            "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
            "url":"http://weixin.qq.com/download",
            "miniprogram":{
        "appid":"xiaochengxuappid12345",
                "pagepath":"index?foo=bar"
    },
        "data":{
            "first": {
                "value":"恭喜你购买成功！",
                        "color":"#173177"
            },
            "keyword1":{
                "value":"巧克力",
                        "color":"#173177"
            },
            "keyword2": {
                "value":"39.8元",
                        "color":"#173177"
            },
            "keyword3": {
                "value":"2014年9月22日",
                        "color":"#173177"
            },
            "remark":{
                "value":"欢迎再次购买！",
                        "color":"#173177"
            }
        }
    }*/
}
