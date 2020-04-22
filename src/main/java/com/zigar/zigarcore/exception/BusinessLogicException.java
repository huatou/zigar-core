package com.zigar.zigarcore.exception;

/**
 * 自定义逻辑异常类
 * Created by yangzihua on 2019/2/13.
 */
public class BusinessLogicException extends RuntimeException {

    public BusinessLogicException(String message) {
        super(message);
    }
}
