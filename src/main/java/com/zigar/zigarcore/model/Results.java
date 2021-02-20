package com.zigar.zigarcore.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangzihua on 2018/10/16.
 * 返回数据模型
 */

@Data
public class Results<T> implements Serializable {

    private Boolean success;
    private String message;
    private T data;

    public Results() {
    }

    public Results(Boolean success) {
        this.success = success;
    }

    public Results(T data) {
        this.data = data;
    }

    public Results(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Results(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Results(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static Results succeed() {
        return new Results(true);
    }

    public static Results succeed(String message) {
        return new Results(true, message);
    }

    public static <T> Results<T> succeed(T t) {
        return new Results(true, t);
    }

    public static Results error() {
        return new Results(false);
    }

    public static <T> Results<T> error(String message, T t) {
        return new Results(false, message, t);
    }

    public static Results error(String message) {
        return new Results(false, message);
    }

    public boolean isSuccess() {
        return this.success;
    }


}
