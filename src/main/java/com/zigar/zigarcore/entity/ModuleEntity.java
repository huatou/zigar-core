package com.zigar.zigarcore.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.myabtisplus.Unique;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 模块
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_module")
@ApiModel(value = "ModuleEntity对象", description = "模块")
public class ModuleEntity implements Serializable {

    /**
     * 定义操作项常量
     */
    public static final String ACTION_MENU = "menu";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_DELETE = "delete";

    /**
     * 模块类型：0业务模块，1系统模块
     */
    public static final Integer TYPE_BUSINESS = 0;
    public static final Integer TYPE_SYSTEM = 1;


    private static final long serialVersionUID = 1L;

    @NotBlank(groups = {RequestUpdateAction.class, RequestDeleteAction.class}, message = "缺少模块id")
    @ApiModelProperty(value = "模块ID")
    @TableId("module_id")
    private String moduleId;

    @NotBlank(groups = RequestInsertAction.class, message = "新建请输入模块名")
    @ApiModelProperty(value = "模块名")
    @TableField("name")
    private String name;

    @NotBlank(groups = RequestInsertAction.class, message = "新建请输入模块唯一编码")
    @ApiModelProperty(value = "模块编码")
    @TableField("code")
    @Unique
    private String code;

    @ApiModelProperty(value = "排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private Boolean isEnabled;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "路径")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "操作类型，多个用逗号隔开")
    @TableField("actions")
    private String actions;

    @ApiModelProperty(value = "模块类型：0业务模块，1系统模块")
    @JsonIgnore
    @TableField(value = "type")
    private Integer type;

    @JsonIgnore
    @TableField(value = "flag")
    @TableLogic
    private Boolean flag;

    /**
     * 获取当前模块是否为系统模块
     *
     * @return
     */
    public Boolean isSystemModule() {
        return this.type != null && this.type == TYPE_SYSTEM;
    }

}
