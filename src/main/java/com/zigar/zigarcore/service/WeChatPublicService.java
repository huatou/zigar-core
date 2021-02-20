package com.zigar.zigarcore.service;


import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.model.WeChatLogin;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Zigar
 * @createTime 2020-06-17 21:36:08
 * @description
 */

public interface WeChatPublicService {

    /**
     * 通过code进行微信登录返回token
     *
     * @param weChatLogin
     */
    Results<String> weChatLogin(WeChatLogin weChatLogin);

    /**
     * 处理微信公众号的信息
     *
     * @param request
     * @param response
     * @return
     */
    void handleWeChatMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException;

    /**
     * 接受微信公众号回收端的文字消息
     *
     * @param openid
     * @param msg
     * @return
     */
    String  handleWeChatTextMessage(String openid, String msg);

    /**
     * 接受微信公众号回收端的图片消息
     *
     * @param openid
     * @param url
     * @return
     */
    String handleWeChatImageMessage(String openid, String url) throws Exception;

    /**
     * 主动发送消息到客户
     *
     * @param userId
     * @param message
     * @return
     */
    Results sendMsgToWeChatUser(String userId, String message);

}

