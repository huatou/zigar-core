package com.zigar.zigarcore.security.security;


import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.utils.jwt.JwtToken;
import com.zigar.zigarcore.utils.jwt.JwtTokenUtil;
import com.zigar.zigarcore.utils.web.HttpServletResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zigar on 2019/3/31.
 */

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private CaptchaCacheHandler captchaCacheHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 用户名密码验证成功后
     * 1.生成token
     * 2.保存token至redis
     * 3.记录登录日志
     * 4.清除该用户对应的验证码
     * 5.返回登录成功消息和token到前台
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException  {

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        JwtToken jwtToken = jwtTokenUtil.generateToken(currentUser.getUsername(), currentUser.getUserId());
        captchaCacheHandler.clearCaptcha(currentUser.getUsername());
        Results<String> results = new Results<>(true, "登录成功", jwtToken.getToken());
        HttpServletResponseUtils.write(httpServletResponse,results);
    }


}
