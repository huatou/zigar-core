package com.zigar.zigarcore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.zigar.zigarcore.api",
        "com.zigar.zigarcore.cache",
        "com.zigar.zigarcore.controller",
        "com.zigar.zigarcore.exception",
        "com.zigar.zigarcore.log",
        "com.zigar.zigarcore.myabtisplus",
        "com.zigar.zigarcore.properties",
        "com.zigar.zigarcore.rest",
        "com.zigar.zigarcore.resttemplate",
        "com.zigar.zigarcore.security.password",
        "com.zigar.zigarcore.security.security",
        "com.zigar.zigarcore.service",
        "com.zigar.zigarcore.ui",
        "com.zigar.zigarcore.utils",
})
@MapperScan("com.zigar.zigarcore.mapper")
public class ZigarCoreConfiguration {
}
