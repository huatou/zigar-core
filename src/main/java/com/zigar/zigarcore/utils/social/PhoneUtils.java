package com.zigar.zigarcore.utils.social;

import java.util.regex.Pattern;

/**
 * @author yzh
 * @date 2020-07-20
 * @description 手机号工具类
 */
public class PhoneUtils {

    /**
     * 判断手机号是否符合规范，正则：手机号（简单）, 1字头＋10位数字即可
     *
     * @param phone
     * @return
     */
    public static final Boolean isValidated(String phone) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(phone).matches();
    }
}
