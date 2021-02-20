package com.zigar.zigarcore.security.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.zigarcore.entity.UserEntity;
import com.zigar.zigarcore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Zigar
 * @createTime 2020-01-15 16:00:22
 * @description
 */

@Component
public class ZUserDetailService implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserEntity> userEntityLambdaQueryWrapper = Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, s);
        UserEntity localUser = userService.getOne(userEntityLambdaQueryWrapper);
        if (localUser == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        return localUser;
    }

}
