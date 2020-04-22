package com.zigar.zigarcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义security配置类
 *
 * @author Zigar
 * @createTime 2019-10-14 15:34:31
 * @description
 */


@Data
@ConfigurationProperties(prefix = JwtProperties.PRE_FIX)
public class JwtProperties {

    public static final String PRE_FIX = "zigar.security.jwt";

    //http中放置token的header的key
    private String jwtHttpHeader = "Authorization";
    //token的开头，如【Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJodWF0b3UiLCJpYXQiOjE1NzEyODExMDcsImV4cCI6MTU3MTM2NzUwNywianRpIjoiMTE4NDY2NDk0NjM1MzU3Mzg5MCJ9.aTfMIfHkgN6-hbvx_d3NW02WaOD8eYx0-vFcx4YpGU1O_oFBYLYZTNI6l5DCYpBZiYn1BmRZy6QX0zw1NrCpMQ】
    private String jwtPrefix = "Bearer ";
    //加密使用的秘钥
    private String secret = "sfu8A!2957&Vn2q129fn";
    //token过期的天数
    private long tokenExpiredDay = 1;
    //24小时的毫秒数
    private static final long TOKEN_EXPIRED_TIME_SECOND = 24 * 60 * 60 * 1000;
    //token过期的毫秒数
    private long tokenExpiredTime = tokenExpiredDay * TOKEN_EXPIRED_TIME_SECOND;

    private String redisTokenName = "[redis token]";
    //同一个用户最大登录点数量
    private Integer maxLoginUsers = 2;
    //登录溢出，达到用户最大登录时是否踢出最先登录那个用户
    private Boolean canLoginOverflow = false;

}
