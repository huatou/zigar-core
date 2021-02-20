package com.zigar.zigarcore.resttemplate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Zigar
 * @createTime 2020-07-04 11:10:18
 * @description RestTemplate的注入
 */

@Configuration
public class RestTemplateConfiguration {

    @Bean
    @ConditionalOnMissingBean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

}
