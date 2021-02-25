package com.zigar.zigarcore.controller;


import com.zigar.zigarcore.utils.jwt.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zigar
 * @createTime 2020-06-23 20:22:28
 * @description 页面控制器，为了防止页面刷新时影响vue路由的使用
 */

@Controller
public class ZigarCoreIndexController {

    /**
     * 后台管理系统页面
     *
     * @return
     */
    @PassToken
    @RequestMapping({"/zigarcore/**"})
    public String index() {
        return "webjars/zigar-core/index";
    }

}
