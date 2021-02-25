package com.zigar.zigarcore.rest;

import cn.hutool.core.lang.Assert;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.model.WeChatLogin;
import com.zigar.zigarcore.service.WeChatPublicService;
import com.zigar.zigarcore.utils.jwt.PassToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zigar
 * @createTime 2020-06-24 21:09:44
 * @description
 */

@Api(tags = "公众号登录管理")
@RestController
@RequestMapping("/api/zigarcore/login")
public class WeChatLoginRestController {

    private final WeChatPublicService weChatPublicService;

    public WeChatLoginRestController(WeChatPublicService weChatPublicService) {
        this.weChatPublicService = weChatPublicService;
    }

    @ApiOperation("公众号登录")
    @PassToken
    @PostMapping
    public Results<String> weChatLogin(@RequestBody WeChatLogin weChatLogin) {

        String code = weChatLogin.getCode();
        Assert.notBlank(code, "code 不能为空");
        return weChatPublicService.weChatLogin(weChatLogin);
    }

}
