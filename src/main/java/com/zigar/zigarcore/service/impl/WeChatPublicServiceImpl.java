package com.zigar.zigarcore.service.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;

import com.zigar.zigarcore.entity.FileEntity;
import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.model.WeChatBaseResult;
import com.zigar.zigarcore.model.WeChatLogin;
import com.zigar.zigarcore.model.WeChatUserInfo;
import com.zigar.zigarcore.properties.SystemProperties;
import com.zigar.zigarcore.resttemplate.ImageDownloadRestTemplate;
import com.zigar.zigarcore.resttemplate.WeChatRestTemplate;
import com.zigar.zigarcore.service.IUserService;
import com.zigar.zigarcore.service.WeChatPublicHandler;
import com.zigar.zigarcore.service.WeChatPublicService;
import com.zigar.zigarcore.utils.date.DateUtils;
import com.zigar.zigarcore.utils.jwt.IdUtils;
import com.zigar.zigarcore.utils.jwt.JwtToken;
import com.zigar.zigarcore.utils.jwt.JwtTokenUtil;
import com.zigar.zigarcore.utils.lang.Assert;
import com.zigar.zigarcore.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Zigar
 * @createTime 2020-06-17 21:36:59
 * @description
 */

@Service
public class WeChatPublicServiceImpl implements WeChatPublicService {

    @Autowired
    private WeChatRestTemplate weChatRestTemplate;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ImageDownloadRestTemplate imageDownloadRestTemplate;

    @Autowired(required = false)
    private WeChatPublicHandler weChatPublicHandler;


    @Override
    public Results<String> weChatLogin(WeChatLogin weChatLogin) {

        Assert.notNull(weChatLogin, "weChatLogin 不能为空");
        String code = weChatLogin.getCode();
        Assert.notNull(code, "code 不能为空");

        Results result = Results.succeed();

        WeChatUserInfo weChatUserInfoByCode = weChatRestTemplate.getWeChatUserInfoByCode(code);
        UserEntity userCustomer = userService.getWeChatUser(weChatUserInfoByCode, weChatLogin);
        JwtToken jwtToken = jwtTokenUtil.generateToken(userCustomer.getUsername(), userCustomer.getUserId());

        String token = jwtToken.getToken();
        result.setData(token);
        return result;

    }

    @Override
    public void handleWeChatMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, ParserConfigurationException, SAXException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("");//回复空字符串，使微信服务器接收消息不超时。后续逻辑使用客服消息处理

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(request.getInputStream());
        Element root = document.getDocumentElement();
        String weChatId = root.getElementsByTagName("ToUserName").item(0).getTextContent();
        String openid = root.getElementsByTagName("FromUserName").item(0).getTextContent();
        String msgType = root.getElementsByTagName("MsgType").item(0).getTextContent();//用户发送内容格式
        String replyContent = null;
        if (StringUtils.equals(msgType, "text")) {
            String msg = root.getElementsByTagName("Content").item(0).getTextContent();//用户发送的内容
            replyContent = this.handleWeChatTextMessage(openid, msg);
        } else if (StringUtils.equals(msgType, "image")) {
            String picUrl = root.getElementsByTagName("PicUrl").item(0).getTextContent();//用户发送的图片
            replyContent = this.handleWeChatImageMessage(openid, picUrl);
        }
        //使用客服消息发送内容到微信用户
        if (StringUtils.isEmpty(replyContent)) {
            this.sendMsgToWeChatUser(openid, replyContent);
        }
    }

    /**
     * 处理公众号消息前置处理
     *
     * @param openid
     * @return
     */
    private Results<UserEntity> preHandleWeChatMessage(String openid) {
        if (StringUtils.isEmpty(openid)) {
            return Results.error("请关注公众号后操作");
        }
        WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
        weChatUserInfo.setOpenid(openid);
        WeChatLogin weChatLogin = new WeChatLogin();
        UserEntity weChatUser = userService.getWeChatUser(weChatUserInfo, weChatLogin);
        return Results.succeed(weChatUser);
    }

    @Override
    public String handleWeChatTextMessage(String openid, String text) {

        String replyContent;

        Results<UserEntity> userEntityResults = this.preHandleWeChatMessage(openid);
        if (!userEntityResults.isSuccess()) {
            replyContent = userEntityResults.getMessage();
        } else {
            UserEntity userCustomer = userEntityResults.getData();
            replyContent = weChatPublicHandler.handleTextMsg(userCustomer, text);
        }
        return replyContent;
    }

    @Override
    public String handleWeChatImageMessage(String openid, String url) {

        String replyContent;

        Results<UserEntity> userEntityResults = this.preHandleWeChatMessage(openid);
        if (!userEntityResults.isSuccess()) {
            replyContent = userEntityResults.getMessage();
        } else {
            UserEntity userCustomer = userEntityResults.getData();
            //将微信图片保存到本地并返回本地绝地路径
            Results<FileEntity> imageResults = imageDownloadRestTemplate.getImageByUrl(url, userCustomer.getUserId());
            if (!imageResults.isSuccess()) {
                return imageResults.getMessage();
            }
            replyContent = weChatPublicHandler.handleImageMsg(userCustomer, imageResults.getData());
        }
        return replyContent;
    }

    @Override
    public Results sendMsgToWeChatUser(String userId, String message) {

        UserEntity collectUerCustomer = userService.getById(userId);
        if (collectUerCustomer == null) {
            throw new BusinessLogicException("发送微信消息的用户不存在");
        }
        if (StringUtils.isEmpty(collectUerCustomer.getWechatOpenId())) {
            throw new BusinessLogicException("发送微信消息的用户没关注公众号");
        }
        Boolean b = weChatRestTemplate.sendMessageToWeChatUser(collectUerCustomer.getWechatOpenId(), message);
        return b ? Results.succeed("发送成功") : Results.error("发送失败");
    }

}
