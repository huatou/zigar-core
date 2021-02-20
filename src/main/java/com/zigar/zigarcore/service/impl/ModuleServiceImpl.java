package com.zigar.zigarcore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.zigar.zigarcore.entity.ModuleEntity;
import com.zigar.zigarcore.mapper.ModuleMapper;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import com.zigar.zigarcore.service.ModuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 模块 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Service
public class ModuleServiceImpl extends ZServiceImpl<ModuleMapper, ModuleEntity> implements ModuleService {

    @Override
    public List<ModuleEntity> getEnabledModuleList() {
        LambdaQueryWrapper<ModuleEntity> queryWrapper = Wrappers.<ModuleEntity>lambdaQuery().eq(ModuleEntity::getIsEnabled, true)
                .orderByAsc(ModuleEntity::getSort);
        return this.list(queryWrapper);
    }

}
