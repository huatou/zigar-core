package com.zigar.zigarcore.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zigar.zigarcore.entity.PrivilegeEntity;
import com.zigar.zigarcore.model.AuthUserPrivilege;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
public interface PrivilegeService extends IService<PrivilegeEntity> {

    public Boolean authUser(AuthUserPrivilege authUserPrivilege);

}
