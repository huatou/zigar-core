package com.zigar.zigarcore.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Zigar
 * @createTime 2020-06-15 21:33:00
 * @description
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class WeChatLogin extends WeChatBaseResult {

    /**
     * 授权成功后跳转到的页面中获取的code值，5分钟失效
     */
    @ApiModelProperty(value = "授权成功后跳转到的页面中获取的code值，5分钟失效")
    private String code;
    /**
     * 授权成功后跳转到的页面中获取的state
     */
    @ApiModelProperty(value = "授权成功后跳转到的页面中获取的state")
    private String state;


}
