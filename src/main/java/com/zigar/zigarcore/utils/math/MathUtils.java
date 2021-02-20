package com.zigar.zigarcore.utils.math;

/**
 * @author Zigar
 * @createTime 2020-06-30 20:41:46
 * @description 数学方法工具类
 */


/**
 * 等比数列求和 q^0+q^1+q^2+...+q^n
 */
public class MathUtils {

    /**
     * 求等比数列及其前n项和
     *
     * @param num
     * @param n
     * @return
     * @author lei
     * public static class Compares {
     * /**
     * 一个浮点数的n次方
     */
    public static double getFactorial(double num, int n) {
        if (num == 0)
            return 0.0;
        if (n < 0)
            return -1;
        return n > 0 ? num * getFactorial(num, n - 1) : 1;
    }

    /**
     * 一个整数的n次方
     *
     * @param num
     * @param n
     * @return
     */
    public static int getFactorial(int num, int n) {
        if (num == 0)
            return 0;
        if (n < 0)
            return -1;
        return n > 0 ? num * getFactorial(num, n - 1) : 1;
    }

    /**
     * 等比数列前n项和,系数和比例都为浮点数
     *
     * @param coefficient 系数
     * @param proportion  比例
     * @param n           项数
     * @return
     */
    public static double getCompareSum(double coefficient, double proportion, int n) {
        return coefficient * getCompareSum(proportion, n);
    }

    /**
     * 等比数列前n项和 系数为浮点数
     *
     * @param coefficient 系数
     * @param proportion  比例
     * @param n           项数
     * @return
     */
    public static double getCompareSum(double coefficient, int proportion, int n) {
        return coefficient * getCompareSum(proportion, n);
    }

    /**
     * 等比数列前n项和 系数和比例都为整数
     *
     * @param coefficient 系数
     * @param proportion  比例
     * @param n           项数
     * @return
     */
    public static int getCompareSum(int coefficient, int proportion, int n) {
        return coefficient * getCompareSum(proportion, n);
    }

    /**
     * 等比数列前n项和，系数为1
     *
     * @param p 比例为整数
     * @param n 前n项
     * @return
     */
    public static int getCompareSum(int p, int n) {
        if (p == 0)
            return 0;
        if (n < 0)
            return -1;
        return n > 0 ? getCompareSum(p, n - 1) + getFactorial(p, n) : 1;
    }

    /**
     * 等比数列前n项和，系数为1
     *
     * @param p 比例为整数
     * @param n 前n项
     * @return
     */
    public static double getCompareSum(double p, int n) {
        if (p == 0)
            return 0;
        if (n < 0)
            return -1;
        return n > 0 ? getCompareSum(p, n - 1) + getFactorial(p, n) : 1;
    }
}

