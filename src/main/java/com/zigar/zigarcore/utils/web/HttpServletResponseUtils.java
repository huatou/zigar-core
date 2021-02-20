package com.zigar.zigarcore.utils.web;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zigar
 * @description OutputStream工具类封装
 * @date 2020-05-12
 */
public class HttpServletResponseUtils {

    /**
     * 使用httpServletResponse输出字符串
     *
     * @param httpServletResponse
     * @param object
     * @throws IOException
     */
    public static void write(HttpServletResponse httpServletResponse, Object object) throws IOException {
        if (httpServletResponse == null || object == null) {
            return;
        }
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        OutputStream outputStream = httpServletResponse.getOutputStream();
        String outputStr;
        if (object instanceof String) {
            outputStr = (String) object;
        } else {
            outputStr = JSONUtil.toJsonStr(object);
        }
        outputStream.write(outputStr.getBytes());
    }

}
