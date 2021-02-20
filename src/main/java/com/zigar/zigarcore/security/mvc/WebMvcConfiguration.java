package com.zigar.zigarcore.security.mvc;//package com.zigar.ticketbackend.zigarcore.security.mvc;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
///**
// * Created by yzh on 2019/4/24.
// * SpringBoot一般重写了support或者adapter方法都会重写自动配置内容，导致自动配置不启用，改用自定义配置
// */
//
//@Configuration
//public class WebMvcConfiguration extends WebMvcConfigurationSupport {
//
//
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        /**
//         * url指定的路径映射到真实的服务器路径
//         * 自动配置内容为：urlPattern=/**，resource为classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/
//         *
//         * 此处配置的urlPattern为所有的静态资源访问路径，如果不经过这些路径无法访问静态资源
//         *
//         */
//
//        //该配置为默认配置，contextPath/后为静态资源路径，省略/static
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//
//        //增加静态资源访问路径contextPath/static/
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//
//        //增加静态资源访问路径contextPath/swagger-ui.html，该swagger-ui.html文件在classpath:/META-INF/resources/
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//
//        //增加静态资源访问路径contextPath/webjars/，webjar用途：把静态资源打包成maven
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
//                .allowCredentials(true).maxAge(3600);
//    }
//
//}