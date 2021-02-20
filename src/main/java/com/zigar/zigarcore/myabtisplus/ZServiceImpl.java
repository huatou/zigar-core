package com.zigar.zigarcore.myabtisplus;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.utils.lang.Assert;
import com.zigar.zigarcore.utils.lang.StringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义service基类
 *
 * @param <M>
 * @param <T>
 */
public class ZServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Override
    public boolean saveOrUpdate(T entity) {
        Assert.notNull(entity, "传入的实体" + entity.getClass().getName() + "必须不为空");
        unique(entity);
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean save(T entity) {
        Assert.notNull(entity, "传入的实体" + entity.getClass().getName() + "必须不为空");
        unique(entity);
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        unique(entityList);
        return super.saveBatch(entityList);
    }

    @Override
    public boolean updateById(T entity) {
        Assert.notNull(entity, "传入的实体" + entity.getClass().getName() + "必须不为空");
        unique(entity);
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        unique(entityList);
        return super.updateBatchById(entityList);
    }

    /**
     * 1、判断该id对应的记录是否存在
     * 判断数据的唯一性
     *
     * @param t
     * @throws IllegalAccessException
     */
    private void unique(T t) {
        Class clazz = t.getClass();
        Map<Field, Object> uniqueParamMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        Object id = null;
        String idName = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Unique.class)) {
                Unique bananaAnnotation = field.getAnnotation(Unique.class);
                boolean isUnique = bananaAnnotation.value();
                if (isUnique) {
                    try {
                        field.setAccessible(true);
                        TableId tableIddAnnotation = field.getAnnotation(TableId.class);
                        Object param = field.get(t);
                        if (tableIddAnnotation != null) {
                            id = param;
                            idName = tableIddAnnotation.value();
                        } else {
                            uniqueParamMap.put(field, param);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Object finalId = id;
        String finalIdName = idName;
        if (finalId != null) {
            QueryWrapper<T> queryWrapperById = Wrappers.<T>query().eq(finalIdName, id);
            T one = getOne(queryWrapperById);
            if (one == null) {
                throw new BusinessLogicException("修改的记录不存在");
            }
        }
        uniqueParamMap.forEach((field, param) -> {
            TableField tableFieldAnnotation = field.getAnnotation(TableField.class);
            QueryWrapper<T> queryWrapper = Wrappers.<T>query().eq(tableFieldAnnotation.value(), param);
            if (finalId != null) {
                queryWrapper = queryWrapper.ne(finalIdName, finalId);
            }
            int count = count(queryWrapper);
            if (count > 0) {
                ApiModelProperty apiModelAnnotation = field.getAnnotation(ApiModelProperty.class);
                throw new BusinessLogicException(StringUtils.append(apiModelAnnotation.value(), "：", param + " 已存在"));
            }
        });
    }

    private void unique(Collection<T> tList) {
        if (CollectionUtils.isNotEmpty(tList)) {
            tList.forEach(t -> {
                unique(t);
            });
        }
    }

}