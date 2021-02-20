package com.zigar.zigarcore.rest;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.entity.ModuleEntity;
import com.zigar.zigarcore.entity.PrivilegeEntity;
import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.model.AuthUserPrivilege;
import com.zigar.zigarcore.model.Page;
import com.zigar.zigarcore.model.RegisterUser;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.service.IUserService;
import com.zigar.zigarcore.service.ModuleService;
import com.zigar.zigarcore.service.PrivilegeService;
import com.zigar.zigarcore.utils.lang.StringUtils;
import com.zigar.zigarcore.utils.security.SecurityUtils;
import com.zigar.zigarcore.utils.web.PageHelperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */

@Api(tags = "用户管理")
@RestController
@RequestMapping("/zigar/user")
public class UserRestController {

    public static final String CURRENT_USER_URL = "current";

    @Autowired
    private IUserService userService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private PageHelperUtils pageHelperUtils;

    /**
     * 获取后台普通用户列表
     *
     * @param httpServletRequest
     * @param userEntity
     * @return
     */
    @ApiOperation("获取用户列表/分页（参数：page，pageSize）/单个（参数id不为空表示为获取单个）")
    @GetMapping
    public Object getUsers(UserEntity userEntity, HttpServletRequest httpServletRequest) {
        if (userEntity != null && StringUtils.isNotBlank(userEntity.getUserId())) {
            return getUserByUserId(userEntity.getUserId());
        }
        Results<Page<UserEntity>> pageResults = pageHelperUtils.isPage(httpServletRequest);
        LambdaQueryWrapper<UserEntity> userQueryWrapper = Wrappers.query(userEntity).lambda();
        //关键字搜索
        String keyword = httpServletRequest.getParameter("keyword");
        if (StringUtils.isNotEmpty(keyword)) {
            userQueryWrapper.like(UserEntity::getDisplayName, keyword)
                    .or().like(UserEntity::getPhone, keyword)
                    .or().like(UserEntity::getWechatNickName, keyword);
        }
        userQueryWrapper.select(i -> !StringUtils.equals(i.getProperty(), "password"));
        if (pageResults.isSuccess()) {
            Page<UserEntity> userPage = userService.page(pageResults.getData(), userQueryWrapper);
            return Results.succeed(userPage);
        } else {
            List<UserEntity> list = userService.list(userQueryWrapper);
            return Results.succeed(list);
        }
    }

    public Results getUserByUserId(String userId) {
        if (StringUtils.equals(userId, CURRENT_USER_URL)) {
            UserEntity currentUser = SecurityUtils.getCurrentUser();
            userId = currentUser.getUserId();
        }
        UserEntity localUser = userService.getById(userId);
        if (localUser != null) {
            //防止密码泄露
            localUser.setPassword(null);
            /**
             * 查询用户对应的菜单
             * 1.获取所有的模块
             * 2.获取所有包含操作项为menu的模块
             * 3.根据不同的用户角色赋予不同的权限
             *  3.1root角色拥有所有菜单
             *  3.2admin角色拥有所有业务菜单
             *  3.3user角色只拥有被授权的菜单
             */
            List<ModuleEntity> userMenuList = new ArrayList<>();
            localUser.setMenuList(userMenuList);
            //获取所有可用模块
            List<ModuleEntity> list = moduleService.getEnabledModuleList();
            //获取所有可用菜单模块
            List<ModuleEntity> menuList = list.stream()
                    .filter(moduleEntity -> StringUtils.contains(moduleEntity.getActions(), ModuleEntity.ACTION_MENU))
                    .collect(Collectors.toList());
            //获取所有可用业务菜单模块
            List<ModuleEntity> businessMenuList = menuList.stream()
                    .filter(moduleEntity -> !moduleEntity.isSystemModule())
                    .collect(Collectors.toList());
            if (localUser.isRoot()) {
                userMenuList.addAll(menuList);
            } else if (localUser.isAdmin()) {
                userMenuList.addAll(businessMenuList);
            } else {
                //获取用户的权限列表
                LambdaQueryWrapper<PrivilegeEntity> privilegeQueryWrapper = Wrappers.<PrivilegeEntity>lambdaQuery()
                        .eq(PrivilegeEntity::getUserId, localUser.getUserId());
                List<PrivilegeEntity> privilegeList = privilegeService.list(privilegeQueryWrapper);
                localUser.setPrivilegeList(privilegeList);
                //判断用户的权限列表是否拥有对应模块，并且拥有菜单操作项权限
                if (CollectionUtils.isNotEmpty(privilegeList)) {
                    List<ModuleEntity> privilegeMenuList = new ArrayList<>();
                    businessMenuList.forEach(businessMenuItem -> {
                        boolean anyMatch = privilegeList
                                .stream()
                                .anyMatch(privilegeEntity ->
                                        StringUtils.equals(privilegeEntity.getModuleId(), businessMenuItem.getModuleId())
                                                && StringUtils.contains(privilegeEntity.getActions(), ModuleEntity.ACTION_MENU));
                        //判断用户的权限中是否
                        if (anyMatch) {
                            privilegeMenuList.add(businessMenuItem);
                        }
                    });
                    userMenuList.addAll(privilegeMenuList);
                }
            }
            return Results.succeed(localUser);
        }
        return Results.error("用户不存在");
    }

    @ApiOperation("后端插入后端用户")
    @PostMapping
    public Results insertUser(@RequestBody @Validated(RequestInsertAction.class) UserEntity userEntity) {
        boolean b = userService.insertUser(userEntity);
        return b ? Results.succeed() : Results.error(("保存失败"));
    }

    @ApiOperation("后端修改后端用户")
    @PutMapping
    public Results updateUser(@RequestBody @Validated(RequestUpdateAction.class) UserEntity userEntity) {
        boolean b = userService.updateUser(userEntity);
        return b ? Results.succeed() : Results.error(("更新失败"));
    }

    @ApiOperation("后端删除后端用户")
    @DeleteMapping
    public Results deleteUser(@RequestBody @Validated(RequestDeleteAction.class) UserEntity userEntity) {
        boolean b = userService.removeById(userEntity.getUserId());
        return b ? Results.succeed() : Results.error(("删除失败"));
    }

    @ApiOperation("后端用户注册")
    @PostMapping("/register")
    public Results userRegister(@RequestBody RegisterUser registerUser) {
        Results results = userService.userRegister(registerUser);
        return results;
    }

    @ApiOperation("对后端用户授权")
    @PostMapping("/auth")
    public Results userAuth(@RequestBody @Validated(RequestUpdateAction.class) AuthUserPrivilege authUserPrivilege) {
        userService.userAuth(authUserPrivilege);
        return Results.succeed();
    }

    @ApiOperation("对后端用户修改密码")
    @PostMapping("/change-password")
    public Results changeUserPassword(@RequestBody UserEntity userEntity) {
        boolean b = userService.changeUserPassword(userEntity);
        return b ? Results.succeed() : Results.error(("修改失败"));
    }

}

