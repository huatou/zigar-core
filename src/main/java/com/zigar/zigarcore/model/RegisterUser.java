package com.zigar.zigarcore.model;

import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author Zigar
 * @createTime 2020-04-27 20:16:35
 * @description 注册用户模型
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RegisterUser {

    @NotBlank(groups = {RequestInsertAction.class}, message = "用户名不能为空")
    private String username;

    @NotBlank(groups = {RequestInsertAction.class}, message = "姓名不能为空")
    private String displayName;

    @NotBlank(groups = {RequestInsertAction.class}, message = "密码不能为空")
    private String password;

    @NotBlank(groups = {RequestInsertAction.class}, message = "重复密码不能为空")
    private String againPassword;

}
