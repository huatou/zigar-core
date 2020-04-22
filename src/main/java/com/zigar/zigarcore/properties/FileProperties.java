package com.zigar.zigarcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zigar
 * @createTime 2020-01-25 15:50:18
 * @description
 */


@Data
@ConfigurationProperties(prefix = FileProperties.PRE_FIX)
public class FileProperties {

    public static final String PRE_FIX = "zigar.file";

    private String localFilePath = "/Users/yangzihua/uploadFile";

    private String imageFilePath = "/images";

}
