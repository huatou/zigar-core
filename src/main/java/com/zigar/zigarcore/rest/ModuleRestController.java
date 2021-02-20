package com.zigar.zigarcore.rest;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.entity.ModuleEntity;
import com.zigar.zigarcore.model.Page;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.service.ModuleService;
import com.zigar.zigarcore.utils.lang.StringUtils;
import com.zigar.zigarcore.utils.web.PageHelperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 模块 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */

@Api(tags = "模块管理")
@RestController
@RequestMapping("/zigar/module")
public class ModuleRestController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PageHelperUtils pageHelperUtils;

    @ApiOperation("获取模块列表/分页（参数：page，pageSize）/单个（参数id不为空表示为获取单个）")
    @GetMapping
    public Results<?> getModules(ModuleEntity moduleEntity, HttpServletRequest httpServletRequest) {

        if (moduleEntity != null && StringUtils.isNotBlank(moduleEntity.getModuleId())) {
            ModuleEntity module = moduleService.getById(moduleEntity.getModuleId());
            return Results.succeed(module);
        }

        Results<Page<ModuleEntity>> pageResults = pageHelperUtils.isPage(httpServletRequest);
        LambdaQueryWrapper<ModuleEntity> userQueryWrapper = Wrappers.lambdaQuery(moduleEntity).orderByAsc(ModuleEntity::getSort);
        if (pageResults.isSuccess()) {
            Page<ModuleEntity> userPage = moduleService.page(pageResults.getData(), userQueryWrapper);
            return Results.succeed(userPage);
        } else {
            List<ModuleEntity> list = moduleService.list(Wrappers.lambdaQuery(moduleEntity));
            return Results.succeed(list);
        }
    }


    @ApiOperation("插入模块")
    @PostMapping
    public Results insertModule(@RequestBody @Validated(RequestInsertAction.class) ModuleEntity moduleEntity) {
        boolean b = moduleService.save(moduleEntity);
        return b ? Results.succeed() : Results.error(("保存失败"));
    }

    @ApiOperation("更新模块")
    @PutMapping
    public Results updateModule(@RequestBody @Validated(RequestUpdateAction.class) ModuleEntity moduleEntity) {
        boolean b = moduleService.updateById(moduleEntity);
        return b ? Results.succeed() : Results.error(("更新失败"));
    }

    @ApiOperation("删除模块")
    @DeleteMapping
    public Results deleteModule(@RequestBody @Validated(RequestDeleteAction.class) ModuleEntity moduleEntity) {
        boolean b = moduleService.removeById(moduleEntity.getModuleId());
        return b ? Results.succeed() : Results.error(("删除失败"));
    }


}

