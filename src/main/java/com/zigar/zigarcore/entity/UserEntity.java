package com.zigar.zigarcore.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.myabtisplus.Unique;
import com.zigar.zigarcore.utils.date.DateUtils;
import com.zigar.zigarcore.utils.lang.StringUtils;
import com.zigar.zigarcore.utils.math.MathUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("z_user")
@ApiModel(value = "UserEntity对象", description = "用户")
@NoArgsConstructor
public class UserEntity implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 定义用户角色类型常量
     */
    public static final String ROLE_ROOT = "root";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String ROLE_WECHAT_USER = "wechatUser";

    public static final String ROLE_ROOT_NAME = "根管理员";
    public static final String ROLE_ADMIN_NAME = "管理员";
    public static final String ROLE_USER_NAME = "普通后台用户";
    public static final String ROLE_WECHAT_USER_NAME = "微信用户";

    public static final String SEX_MALE = "1";
    public static final String SEX_FEMALE = "2";
    public static final String SEX_UN_KNOW = "0";

    public static final String SEX_MALE_NAME = "男";
    public static final String SEX_FEMALE_NAME = "女";
    public static final String SEX_UN_KNOW_NAME = "未知";

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.pwdResetTime = DateUtils.now();
    }

    public UserEntity(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.pwdResetTime = DateUtils.now();
    }


    @NotBlank(groups = {RequestUpdateAction.class, RequestDeleteAction.class}, message = "userId不能为空")
    @ApiModelProperty(value = "用户ID")
    @TableId("user_id")
    private String userId;

    @NotBlank(groups = RequestInsertAction.class, message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    @TableField("username")
    @Unique
    private String username;

    @NotBlank(groups = RequestInsertAction.class, message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "姓名")
    @TableField("display_name")
    private String displayName;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    @Unique
    private String phone;

    @ApiModelProperty(value = "最后登录时间")
    @TableField("last_login_time")
    private Date lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @JsonIgnore
    @TableField(value = "pwd_reset_time")
    private Date pwdResetTime;

    @JsonIgnore
    @TableField(value = "role")
    private String role;

    @JsonIgnore
    @TableField(value = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    @JsonIgnore
    @TableField(value = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @JsonIgnore
    @TableField(value = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "是否可用")
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private Boolean isEnabled;

    @JsonIgnore
    @TableField(value = "flag")
    @TableLogic
    private Boolean flag;

    @ApiModelProperty(value = "微信公众号openId")
    @TableField(value = "wechat_open_id")
    private String wechatOpenId;

    @ApiModelProperty(value = "微信用户昵称")
    @TableField(value = "wechat_nick_name")
    private String wechatNickName;

    @ApiModelProperty(value = "微信用户头像")
    @TableField(value = "wechat_head_pic")
    private String wechatHeadPic;

    /**
     * 用户拥有的菜单列表
     */
    @TableField(exist = false)
    private List<ModuleEntity> menuList;

    /**
     * 用户拥有的权限列表
     */
    @TableField(exist = false)
    private List<PrivilegeEntity> privilegeList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (!StringUtils.isEmpty(role)) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            grantedAuthorityList.add(simpleGrantedAuthority);
        }
        return grantedAuthorityList;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * 获取当前用户的性别名称
     *
     * @return
     */
    public String getSexName() {
        String sexName = "";
        if (StringUtils.equals(sex, SEX_MALE)) {
            sexName = SEX_MALE_NAME;
        } else if (StringUtils.equals(sex, SEX_FEMALE)) {
            sexName = SEX_FEMALE_NAME;
        } else if (StringUtils.equals(sex, SEX_UN_KNOW)) {
            sexName = SEX_UN_KNOW_NAME;
        }
        return sexName;
    }

    public Boolean isRoot() {
        return StringUtils.equals(this.role, ROLE_ROOT);
    }

    public Boolean isAdmin() {
        return StringUtils.equals(this.role, ROLE_ADMIN);
    }


    /**
     * 获取用户角色名称
     *
     * @return
     */
    public String getRoleName() {
        if (ROLE_ROOT.equals(ROLE_ROOT)) {
            return ROLE_ROOT_NAME;
        } else if (ROLE_ADMIN.equals(role)) {
            return ROLE_ADMIN_NAME;
        } else if (ROLE_USER.equals(role)) {
            return ROLE_USER_NAME;
        } else if (ROLE_WECHAT_USER.equals(role)) {
            return ROLE_WECHAT_USER_NAME;
        }
        return "";
    }

}
