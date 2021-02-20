package com.zigar.zigarcore.resttemplate;

import cn.hutool.core.lang.Assert;

import com.zigar.zigarcore.cache.WeChatCache;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.model.*;
import com.zigar.zigarcore.properties.SystemProperties;
import com.zigar.zigarcore.properties.WeChatProperties;
import com.zigar.zigarcore.utils.date.DateUtils;
import com.zigar.zigarcore.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Zigar
 * @createTime 2020-07-04 11:12:34
 * @description 封装微信公众号的请求
 */

@Component
public class WeChatRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatCache weChatCache;

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 获取微信AccessToken的url
     */
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * 获取微信用户的url
     */
    public static final String GET_USER_LIST = "https://api.weixin.qq.com/cgi-bin/user/get";
    /**
     * 发送模板消息到用户
     */
    public static final String POST_TEMPLATE_MSG_TO_CUSTOMER_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    /**
     * 主动推送消息个客户（客服回复消息）的url
     */
    public static final String POST_MSG_TO_CUSTOMER_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    /**
     * 获取用户AccessToken的url
     */
    public static final String GET_USER_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 刷新用户AccessToken的url
     */
    public static final String REFRESH_USER_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    /**
     * 刷新用户信息的url
     */
    public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 获取 access_token
     *
     * @param appid
     * @param secret
     * @return
     */
    public WeChatAccessToken getWeChatAccessToken(String appid, String secret) {

        Assert.notBlank(appid, "appid 不能为空");
        Assert.notBlank(secret, "secret 不能为空");

        String url = GET_ACCESS_TOKEN_URL + "?appid=" + appid + "&secret=" + secret + "&grant_type=client_credential";

        ResponseEntity<WeChatAccessToken> responseEntity = restTemplate.getForEntity(url, WeChatAccessToken.class);
        WeChatAccessToken weChatAccessToken = responseEntity.getBody();
        if (weChatAccessToken.isSuccess()) {
            return weChatAccessToken;
        }
        throw new BusinessLogicException("获取 wechat_access_token 失败；errCode：" + weChatAccessToken.getErrCode() + "errMsg：" + weChatAccessToken.getErrMsg());
    }

    /**
     * 获取 user_access_token
     *
     * @param code
     * @return
     */
    public WeChatUserAccessToken getUserAccessToken(String code, String appid, String secret) {

        Assert.notBlank(code, "getUserAccessToken code 不能为空");

        String url = GET_USER_ACCESS_TOKEN_URL + "?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";

        ResponseEntity<WeChatUserAccessToken> responseEntity = restTemplate.getForEntity(url, WeChatUserAccessToken.class);
        WeChatUserAccessToken weChatUserAccessToken = responseEntity.getBody();
        if (weChatUserAccessToken.isSuccess()) {
            return weChatUserAccessToken;
        }
        throw new BusinessLogicException("获取 access_token 失败；errCode：" + weChatUserAccessToken.getErrCode() + "errMsg：" + weChatUserAccessToken.getErrMsg());
    }

    /**
     * 刷新 access_token
     *
     * @param refreshToken
     * @param appId
     * @return
     */
    public WeChatUserAccessToken refreshUserAccessToken(String refreshToken, String appId) {

        Assert.notBlank(refreshToken, "refreshToken 不能为空");

        String url = REFRESH_USER_ACCESS_TOKEN_URL + "?appid=" + appId + "&grantType=grantType" + "&refresh_token=refreshToken";

        ResponseEntity<WeChatUserAccessToken> responseEntity = restTemplate.getForEntity(url, WeChatUserAccessToken.class);
        WeChatUserAccessToken weChatUserAccessToken = responseEntity.getBody();
        if (weChatUserAccessToken.isSuccess()) {
            return weChatUserAccessToken;
        }
        throw new BusinessLogicException("刷新 access_token 失败");
    }


    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken
     * @param openId
     */
    public WeChatUserInfo getWeChatUserInfo(String accessToken, String openId) {

        Assert.notBlank(accessToken, "getWeChatUserInfo accessToken 不能为空");
        Assert.notBlank(openId, "getWeChatUserInfo openid 不能为空");

        String url = GET_USER_INFO_URL + "?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";

        ResponseEntity<WeChatUserInfo> responseEntity = restTemplate.getForEntity(url, WeChatUserInfo.class);
        WeChatUserInfo WeChatUserInfo = responseEntity.getBody();
        if (WeChatUserInfo.isSuccess()) {
            return WeChatUserInfo;
        }
        throw new BusinessLogicException("获取 user_info 失败");
    }

    /**
     * 通过code直接获取到userInfo
     *
     * @param code 前端通过回调地址返回的参数
     * @return
     */
    public WeChatUserInfo getWeChatUserInfoByCode(String code) {
        WeChatUserAccessToken accessToken = getUserAccessToken(code, weChatProperties.getAppId(), weChatProperties.getAppSecret());
        WeChatUserInfo weChatUserInfo = getWeChatUserInfo(accessToken.getAccessToken(), accessToken.getOpenId());
        return weChatUserInfo;
    }


    /**
     * 发送客服消息到具体微信用户，如果用户48小时内未发消息到公众号，则会发送失败
     *
     * @param openId
     * @param message
     * @return
     */
    public Boolean sendMessageToWeChatUser(String openId, String message) {

        Assert.notBlank(openId, "openid 不能为空");
        Assert.notBlank(message, "message 不能为空");

        String accessToken = weChatCache.getWeChatAccessToken();
        String url = POST_MSG_TO_CUSTOMER_URL + "?access_token=" + accessToken;

        WeChatSendMessage WeChatSendMessage = new WeChatSendMessage(openId, message);

        ResponseEntity<WeChatBaseResult> responseEntity = restTemplate.postForEntity(url, WeChatSendMessage, WeChatBaseResult.class);
        WeChatBaseResult weChatBaseResult = responseEntity.getBody();
        return weChatBaseResult.isSuccess();
    }

    /**
     * 发送模板信息到微信用户
     *
     * @param weChatMessageTemplate
     * @return
     */
    public WeChatBaseResult sendTemplateMsgToCollectCustomer(WeChatMessageTemplate weChatMessageTemplate) {

        String accessToken = weChatCache.getWeChatAccessToken();
        String url = POST_TEMPLATE_MSG_TO_CUSTOMER_URL + "?access_token=" + accessToken;

        ResponseEntity<WeChatBaseResult> responseEntity = restTemplate.postForEntity(url, weChatMessageTemplate, WeChatBaseResult.class);
        WeChatBaseResult weChatBaseResult = responseEntity.getBody();
        return weChatBaseResult;
    }


}
