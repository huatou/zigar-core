package com.zigar.zigarcore.utils.social;

import java.util.Random;

/**
 * @author yzh
 * @date 2020-07-20
 * @description 码类工具类
 */
public class CodeUtils {

    /**
     * 获取随机数字符串
     *
     * @return
     */
    public static String randomCode(int length) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * 获取6位短信验证码
     *
     * @return
     */
    public static String getMessageCode() {
        return randomCode(6);
    }

}
