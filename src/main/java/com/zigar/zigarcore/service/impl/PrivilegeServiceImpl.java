package com.zigar.zigarcore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.zigar.zigarcore.entity.ModuleEntity;
import com.zigar.zigarcore.entity.PrivilegeEntity;
import com.zigar.zigarcore.mapper.PrivilegeMapper;
import com.zigar.zigarcore.model.AuthUserPrivilege;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import com.zigar.zigarcore.service.ModuleService;
import com.zigar.zigarcore.service.PrivilegeService;
import com.zigar.zigarcore.utils.lang.Assert;
import com.zigar.zigarcore.utils.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Service
public class PrivilegeServiceImpl extends ZServiceImpl<PrivilegeMapper, PrivilegeEntity> implements PrivilegeService {

    @Autowired
    private ModuleService moduleService;

    /**
     * 对某个用户授权
     * 1.先删除用户的所有权限
     * 2.再重新添加权限
     */
    @Override
    public Boolean authUser(AuthUserPrivilege authUserPrivilege) {

        String userId = authUserPrivilege.getUserId();
        List<PrivilegeEntity> privilegeList = authUserPrivilege.getPrivilegeList();

        Assert.notNull(userId, "请传入userId");

        //如果提交的权限为空，则删除全部权限
        LambdaQueryWrapper<PrivilegeEntity> lambdaQueryWrapper = Wrappers.<PrivilegeEntity>lambdaQuery().eq(PrivilegeEntity::getUserId, userId);
        this.remove(lambdaQueryWrapper);

        if (CollectionUtils.isNotEmpty(privilegeList)) {
            //插入权限列表
            List<PrivilegeEntity> insertPrivilegeList = new ArrayList<>();
            //获取可用的模块
            List<ModuleEntity> enabledModuleList = moduleService.getEnabledModuleList();
            privilegeList.forEach(privilegeEntity -> {
                //判断用户权限列表的模块中是否有模块过期或被删除。
                boolean isExits = enabledModuleList
                        .stream().anyMatch(moduleEntity ->
                                StringUtils.equals(moduleEntity.getModuleId(), privilegeEntity.getModuleId()));
                if (isExits) {
                    PrivilegeEntity PrivilegeEntity = new PrivilegeEntity(userId, privilegeEntity.getModuleId(), privilegeEntity.getActions());
                    insertPrivilegeList.add(PrivilegeEntity);
                }
            });
            return this.saveBatch(insertPrivilegeList);
        }
        return true;
    }


}
