package com.zigar.zigarcore.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Zigar
 * @createTime 2020-01-17 15:25:26
 * @description
 */

public class DateUtils {

    public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public static final String dateFormat = "yyyy-MM-dd";

    public static Date now() {
        return new Date();
    }

    public static String now(String format) {
        return format(now(), format);
    }

    public static String formatNowDate() {
        return format(now(), dateFormat);
    }

    public static String formatDate(Date date) {
        return format(date, dateFormat);
    }

    public static String formatNowTime() {
        return format(now(), dateTimeFormat);
    }

    public static String format(Date date) {
        return format(date, dateTimeFormat);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date formatDate(String date) {
        return format(date, dateFormat);
    }

    public static Date format(String date) {
        Date dt = format(date, dateTimeFormat);
        if (dt == null) {
            return formatDate(date);
        } else {
            return dt;
        }
    }

    public static Date format(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static long nowTimestamp() {
        return new Date().getTime();
    }

    /**
     * 获取两个时间相差多少天
     *
     * @param smallDate
     * @param bigDate
     * @return
     */
    public static int daysBetween(Date smallDate, Date bigDate) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(smallDate);

        long time1 = cal.getTimeInMillis();

        cal.setTime(bigDate);

        long time2 = cal.getTimeInMillis();

        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));

    }

    /**
     * 获取日期大于当前日期多少天
     *
     * @param date2
     * @return
     */
    public static int nowDaysBetween(Date date2) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date());

        long time1 = cal.getTimeInMillis();

        cal.setTime(date2);

        long time2 = cal.getTimeInMillis();

        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));

    }


    /**
     * 获取大于当前日期多少年的日期
     *
     * @param years
     * @return
     */
    public static Date nowDaysAfter(int years) {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);//当前时间减去一年，即一年前的时间
        return calendar.getTime();
    }

}
