package com.zigar.zigarcore.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Object工具类
 *
 * @author zigar
 * @date 2020-04-23
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {


    /**
     * object转HashMap
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToHashMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            Class objClass = obj.getClass();
            Field[] fieldArr = objClass.getDeclaredFields();
            if (fieldArr != null && fieldArr.length != 0) {
                for (int i = 0; i < fieldArr.length; i++) {
                    Field field = fieldArr[i];
                    // 设置些属性是可以访问的
                    field.setAccessible(true);
                    String key = field.getName();
                    Object value = null;
                    try {
                        value = field.get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        }
        return map;
    }
}