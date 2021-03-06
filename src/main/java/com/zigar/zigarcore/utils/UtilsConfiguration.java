package com.zigar.zigarcore.utils;

import com.zigar.zigarcore.utils.web.PageHelperUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zigar
 * @createTime 2020-03-07 12:37:33
 * @description
 */

@Configuration
public class UtilsConfiguration {

    @Bean
    @ConditionalOnMissingBean
    PageHelperUtils pageHelperUtils() {
        return new PageHelperUtils();
    }
}
