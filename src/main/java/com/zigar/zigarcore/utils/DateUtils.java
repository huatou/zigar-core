package com.zigar.zigarcore.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        return new Date(System.currentTimeMillis());
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

}
