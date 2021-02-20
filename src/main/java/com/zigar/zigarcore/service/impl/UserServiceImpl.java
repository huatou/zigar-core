package com.zigar.zigarcore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.zigar.zigarcore.entity.ModuleEntity;
import com.zigar.zigarcore.entity.PrivilegeEntity;
import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.mapper.UserMapper;
import com.zigar.zigarcore.model.*;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import com.zigar.zigarcore.service.IUserService;
import com.zigar.zigarcore.service.PrivilegeService;
import com.zigar.zigarcore.utils.date.DateUtils;
import com.zigar.zigarcore.utils.jwt.IdUtils;
import com.zigar.zigarcore.utils.jwt.JwtToken;
import com.zigar.zigarcore.utils.jwt.JwtTokenUtil;
import com.zigar.zigarcore.utils.lang.Assert;
import com.zigar.zigarcore.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@Service
public class UserServiceImpl extends ZServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Boolean insertUser(UserEntity userEntity) {
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        Assert.notNull(username, "username不能为空");
        Assert.notNull(password, "password不能为空");
        String encodePassword = passwordEncoder.encode(password);
        userEntity.setPassword(encodePassword);
        userEntity.setPwdResetTime(DateUtils.now());
        userEntity.setIsAccountNonExpired(true);
        userEntity.setIsAccountNonLocked(true);
        userEntity.setIsCredentialsNonExpired(true);
        userEntity.setIsEnabled(true);
        if (StringUtils.isEmpty(userEntity.getRole())) {
            userEntity.setRole(UserEntity.ROLE_USER);
        }
        return this.saveOrUpdate(userEntity);
    }

    @Override
    public Boolean updateUser(UserEntity userEntity) {
        Assert.notNull(userEntity.getUserId(), "userId不能为空");
        return this.updateById(userEntity);
    }

    @Override
    public Boolean userAuth(AuthUserPrivilege authUserPrivilege) {

        Assert.notNull(authUserPrivilege, "authUserPrivilege 不能为空");
        String userId = authUserPrivilege.getUserId();
        Assert.notNull(userId, "userId 不能为空");

        LambdaQueryWrapper<PrivilegeEntity> privilegeQueryWrapper = Wrappers.<PrivilegeEntity>lambdaQuery().eq(PrivilegeEntity::getUserId, userId);
        privilegeService.remove(privilegeQueryWrapper);

        List<PrivilegeEntity> privilegeList = authUserPrivilege.getPrivilegeList();
        List<PrivilegeEntity> insertPrivilegeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(privilegeList)) {
            privilegeList.forEach(privilegeEntity -> {
                if (privilegeEntity != null) {
                    Assert.notNull(privilegeEntity.getModuleId(), "moduleId不能为空");
                    PrivilegeEntity insertPrivilegeEntity = new PrivilegeEntity();
                    insertPrivilegeEntity.setModuleId(privilegeEntity.getModuleId());
                    insertPrivilegeEntity.setActions(ModuleEntity.ACTION_MENU);
                    insertPrivilegeEntity.setUserId(userId);
                    insertPrivilegeList.add(insertPrivilegeEntity);
                }
            });
        }
        return privilegeService.saveBatch(insertPrivilegeList);
    }

    @Override
    public Results userRegister(RegisterUser registerUser) {
        String username = registerUser.getUsername();
        String displayName = registerUser.getDisplayName();
        String password = registerUser.getPassword();
        String againPassword = registerUser.getAgainPassword();
        Assert.notNull(username, "username不能为空");
        Assert.notNull(displayName, "displayName");
        Assert.notNull(password, "password不能为空");
        Assert.notNull(againPassword, "againPassword");

        if (!StringUtils.equals(password, againPassword)) {
            throw new BusinessLogicException("两次密码不一致");
        }
        UserEntity registerUserEntity = new UserEntity(username, password, displayName);
        this.insertUser(registerUserEntity);

        JwtToken jwtToken = jwtTokenUtil.generateToken(registerUserEntity.getUsername(), registerUserEntity.getUserId());

        return new Results(true, "登录成功", jwtToken.getToken());
    }

    @Override
    public Boolean changeUserPassword(UserEntity userEntity) {

        Assert.notNull(userEntity, "请提交实体");
        Assert.notNull(userEntity.getUserId(), "userId 不能为空");
        Assert.notNull(userEntity.getPassword(), "password 不能为空");

        //修改密码，密码必须加密
        String password = userEntity.getPassword();
        if (StringUtils.isNotBlank(password)) {
            String encodePassword = passwordEncoder.encode(password);
            userEntity.setPassword(encodePassword);
            userEntity.setPwdResetTime(DateUtils.now());
        }

        return this.updateById(userEntity);
    }

    @Override
    public Boolean boundWeiXin(UserEntity userEntity) {

        String wechatOpenId = userEntity.getWechatOpenId();
        Assert.notNull(wechatOpenId, "请输入微信公众号用户openId");

        UserEntity updateUser = new UserEntity();
        updateUser.setUserId(userEntity.getUserId());
        updateUser.setWechatOpenId(wechatOpenId);

        return this.updateById(updateUser);
    }


    @Override
    public Boolean boundPhone(UserEntity userEntity) {

        UserEntity updateUser = new UserEntity();
        updateUser.setUserId(userEntity.getUserId());
        String phone = userEntity.getPhone();
        Assert.notNull(phone, "请输入真实手机号");
        updateUser.setDisplayName(phone);

        return this.updateById(updateUser);
    }

    @Override
    public UserEntity getWeChatUser(WeChatUserInfo weChatUserInfo, WeChatLogin weChatLogin) {

        Assert.notNull(weChatUserInfo, "getUserCustomer weChatUserInfo 不能为空");
        Assert.notNull(weChatLogin, "getUserCustomer weChatLogin 不能为空");
        String openId = weChatUserInfo.getOpenid();
        Assert.notNull(openId, "getUserCustomer openid 不能为空");

        //根据openId判断当前客户是否存在
        LambdaQueryWrapper<UserEntity> userQueryWrapper;
        userQueryWrapper = Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getWechatOpenId, openId);

        UserEntity localUser = this.getOne(userQueryWrapper);
        if (localUser != null) {
            //如果此处是从网页跳转携带用户信息，则更新到数据库
            Boolean isUpdate = false;
            if (StringUtils.isNotEmpty(weChatUserInfo.getNickname())) {
                localUser.setWechatNickName(weChatUserInfo.getNickname());
                isUpdate = true;
            }
            if (StringUtils.isNotEmpty(weChatUserInfo.getSex())) {
                localUser.setSex(weChatUserInfo.getSex());
                isUpdate = true;
            }
            if (StringUtils.isNotEmpty(weChatUserInfo.getHeadImgUrl())) {
                localUser.setWechatHeadPic(weChatUserInfo.getHeadImgUrl());
                isUpdate = true;
            }
            if (isUpdate) {
                this.updateById(localUser);
            }
            return localUser;
        } else {

            //客户不存在则需要插入客户信息
            UserEntity addCustomerUser = new UserEntity();
            addCustomerUser.setWechatNickName(weChatUserInfo.getNickname());
            addCustomerUser.setWechatHeadPic(weChatUserInfo.getHeadImgUrl());
            addCustomerUser.setSex(weChatUserInfo.getSex());

            //用户名密码使用随机id，因为客户不需要登录
            addCustomerUser.setUsername(IdUtils.nextStrId());
            addCustomerUser.setPassword(IdUtils.nextStrId());

            addCustomerUser.setWechatOpenId(openId);
            addCustomerUser.setRole(UserEntity.ROLE_WECHAT_USER);
            this.insertUser(addCustomerUser);
            //密码置空后返回
            addCustomerUser.setPassword("");

            return addCustomerUser;
        }
    }

}
