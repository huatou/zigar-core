package com.zigar.zigarcore.utils.lang;

/**
 * @author Zigar
 * @createTime 2020-06-25 14:27:10
 * @description
 */

public class IntegerUtils {

    public static final Integer IntegerFilter(Integer integer) {
        return integer == null ? 0 : integer;
    }

    public static final Integer add(Integer integer1, Integer integer2) {
        integer1 = IntegerFilter(integer1);
        integer2 = IntegerFilter(integer2);
        return integer1 + integer2;
    }

    public static final Boolean bigger(Integer integer1, Integer integer2) {
        integer1 = IntegerFilter(integer1);
        integer2 = IntegerFilter(integer2);
        return integer1 > integer2;
    }

}
