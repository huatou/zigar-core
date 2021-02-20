package com.zigar.zigarcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Zigar
 * @createTime 2020-07-04 11:22:28
 * @description 调用微信返回基础数据模型
 */

@Data
public class WeChatBaseResult {

    @JsonProperty("errcode")
    private String errCode;

    @JsonProperty("errmsg")
    private String errMsg;


    /**
     * 获取当前请求是否成功。现以errCode为空表示成功
     *
     * @return
     */
    public Boolean isSuccess() {
        return StringUtils.isEmpty(errCode);
    }


}
