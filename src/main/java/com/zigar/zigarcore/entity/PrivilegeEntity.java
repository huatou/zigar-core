package com.zigar.zigarcore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigar.zigarcore.utils.date.DateUtils;
import com.zigar.zigarcore.utils.jwt.IdUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_privilege")
@ApiModel(value = "PrivilegeEntity对象", description = "权限")
@NoArgsConstructor
public class PrivilegeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    @TableId("privilege_id")
    private String privilegeId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "模块ID")
    @TableField("module_id")
    private String moduleId;

    @ApiModelProperty(value = "操作项，多个以逗号隔开")
    @TableField("actions")
    private String actions;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @JsonIgnore
    @TableField(value = "flag")
    @TableLogic
    private Boolean flag;

    public PrivilegeEntity(String userId, String moduleId, String actions) {
        this.privilegeId = IdUtils.nextStrId();
        this.userId = userId;
        this.moduleId = moduleId;
        this.actions = actions;
        this.createTime = DateUtils.now();
    }

}
