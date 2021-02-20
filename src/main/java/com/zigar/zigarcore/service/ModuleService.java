package com.zigar.zigarcore.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zigar.zigarcore.entity.ModuleEntity;

import java.util.List;

/**
 * <p>
 * 模块 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
public interface ModuleService extends IService<ModuleEntity> {

    public List<ModuleEntity> getEnabledModuleList();

}
