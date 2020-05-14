package com.zigar.zigarcore.myabtisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zigar.zigarcore.utils.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yzh
 * @date 2020-05-12
 * @description mybatis-plus自动填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, DateUtils.now());
        this.strictInsertFill(metaObject, "loginTime", Date.class, DateUtils.now());
        this.strictInsertFill(metaObject, "isEnabled", Integer.class, 1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, DateUtils.now());
    }
}