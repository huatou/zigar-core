package com.zigar.zigarcore.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zigar
 * @createTime 2020-03-06 20:13:20
 * @description
 */


@Configuration
public class ExceptionConfiguration {

    @Bean
    GlobalExceptionController globalExceptionController() {
        return new GlobalExceptionController();
    }

}
