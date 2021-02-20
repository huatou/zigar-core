package com.zigar.zigarcore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author zigar
 * @since 2020-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_file")
@ApiModel(value = "FileEntity对象", description = "文件表")
public class FileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    @TableId("file_id")
    private String fileId;

    @ApiModelProperty(value = "文件大小")
    @TableField("size")
    private Long size;

    @ApiModelProperty(value = "文件名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "文件名称md5")
    @TableField("md5")
    private String md5;

    @ApiModelProperty(value = "文件后缀")
    @TableField("suffix")
    private String suffix;

    @ApiModelProperty(value = "存储路径")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "上传人ID")
    @TableField("upload_user_id")
    private String uploadUserId;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @JsonIgnore
    @ApiModelProperty(value = "逻辑删除")
    @TableField("flag")
    @TableLogic
    private Boolean flag;


}
