package com.zigar.zigarcore.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.model.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 新增用户
     *
     * @param userEntity
     */
    Boolean insertUser(@Validated(RequestInsertAction.class) UserEntity userEntity);

    /**
     * 更新用户
     *
     * @param userEntity
     */
    Boolean updateUser(UserEntity userEntity);

    /**
     * 用户授权
     *
     * @param authUserPrivilege
     */
    Boolean userAuth(AuthUserPrivilege authUserPrivilege);

    /**
     * 用户注册
     *
     * @param registerUser
     * @return
     */
    Results userRegister(RegisterUser registerUser);

    /**
     * 修改用户密码
     *
     * @param userEntity
     * @return
     */
    Boolean changeUserPassword(UserEntity userEntity);

    /**
     * 绑定微信
     *
     * @param userEntity
     * @return
     */
    Boolean boundWeiXin(UserEntity userEntity);

    /**
     * 绑定手机号
     *
     * @param userEntity
     * @return
     */
    Boolean boundPhone(UserEntity userEntity);

    UserEntity getWeChatUser(WeChatUserInfo weChatUserInfo, WeChatLogin weChatLogin);

}
