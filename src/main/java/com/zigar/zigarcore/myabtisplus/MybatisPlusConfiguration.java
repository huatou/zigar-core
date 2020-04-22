package com.zigar.zigarcore.myabtisplus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zigar
 * @createTime 2020-03-06 20:16:06
 * @description
 */


@Configuration
public class MybatisPlusConfiguration {

    @Bean
    MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }

}
