package com.zigar.zigarcore.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 用于精确计算的工具类
 *
 * @author yzh
 * @date 2019-09-23
 */
public class BigDecimalUtils {

    public static final Integer SCALE_TWO = 2;

    public static BigDecimal getDecimalHalfUp(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO.setScale(SCALE_TWO);
        } else {
            return bigDecimal.divide(new BigDecimal("100"), SCALE_TWO, RoundingMode.HALF_UP);
        }
    }

    public static String getMoney(BigDecimal money) {
        if (money == null) {
            return "0";
        }
        NumberFormat nf = NumberFormat.getInstance();
        String ssStr = nf.format(money);
        return ssStr;
    }

    /**
     * 使比较数量不为空并且设置同样的小数位
     *
     * @param b
     */
    public static BigDecimal bigDecimalFilter(BigDecimal b) {
        if (b == null) {
            b = new BigDecimal(0);
        }
        b.setScale(2);
        return b;
    }

    /**
     * 大于
     *
     * @param b1
     * @param b2
     * @return
     */
    public static Boolean bigger(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.compareTo(b2) == 1;
    }

    /**
     * 大于
     *
     * @param b1
     * @param int2
     * @return
     */
    public static Boolean bigger(BigDecimal b1, Integer int2) {
        b1 = bigDecimalFilter(b1);
        BigDecimal b2;
        if (int2 == null) {
            b2 = BigDecimal.ZERO;
        } else {
            b2 = new BigDecimal(int2);
        }
        return b1.compareTo(b2) == 1;
    }

    /**
     * 大于
     *
     * @param b1
     * @param long2
     * @return
     */
    public static Boolean bigger(BigDecimal b1, Long long2) {
        b1 = bigDecimalFilter(b1);
        BigDecimal b2;
        if (long2 == null) {
            b2 = BigDecimal.ZERO;
        } else {
            b2 = new BigDecimal(long2);
        }
        return b1.compareTo(b2) == 1;
    }


    /**
     * 大于等于
     *
     * @param b1
     * @param b2
     * @return
     */
    public static Boolean biggerEquals(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.compareTo(b2) > -1;
    }

    /**
     * 小于
     *
     * @param b1
     * @param b2
     * @return
     */
    public static Boolean smaller(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.compareTo(b2) == -1;
    }

    /**
     * 小于等于
     *
     * @param b1
     * @param b2
     * @return
     */
    public static Boolean smallerEquals(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.compareTo(b2) < 1;
    }

    /**
     * 等于
     *
     * @param b1
     * @param b2
     * @return
     */
    public static Boolean equals(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.compareTo(b2) == 0;
    }

    /**
     * Double转BigDecimal
     *
     * @param d
     * @return
     */
    public static BigDecimal getBigDecimalByDouble(Double d) {
        if (d == null) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(d);
        }
    }


    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.add(b2);
    }

    public static BigDecimal substruct(BigDecimal b1, BigDecimal b2) {
        b1 = bigDecimalFilter(b1);
        b2 = bigDecimalFilter(b2);
        return b1.subtract(b2);
    }

    public static BigDecimal multiply(BigDecimal b1, long l2) {
        b1 = bigDecimalFilter(b1);
        BigDecimal b2 = new BigDecimal(l2);
        return b1.multiply(b2);
    }

}
