package com.zigar.zigarcore.utils;

import org.springframework.lang.Nullable;

/**
 * Assert工具类封装
 */
public class Assert extends org.springframework.util.Assert {

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        } else if (object instanceof String) {
            String str = (String) object;
            if (StringUtils.isBlank(str)) {
                throw new IllegalArgumentException(message);
            }
        }
    }

}
