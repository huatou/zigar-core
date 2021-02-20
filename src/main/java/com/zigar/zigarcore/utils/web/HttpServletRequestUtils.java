package com.zigar.zigarcore.utils.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zigar
 * @createTime 2020-07-09 23:15:57
 * @description
 */

public class HttpServletRequestUtils {

    public static final String PARAM_KEYWORD = "keyword";

    public static final String getKeyword(HttpServletRequest httpServletRequest) {
        String keyword = httpServletRequest.getParameter(PARAM_KEYWORD);
        return keyword;
    }
}
