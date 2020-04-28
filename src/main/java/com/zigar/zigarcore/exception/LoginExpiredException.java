package com.zigar.zigarcore.exception;

/**
 * @author Zigar
 * @createTime 2020-04-28
 * @description 定义异常登录过期（token失效）
 */

public class LoginExpiredException extends RuntimeException {

    public LoginExpiredException(String message) {
        super(message);
    }
}

