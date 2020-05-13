//package com.zigar.zigarcore.json;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
//import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
//import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//
///**
// * 实体返回json时localDateTime格式校正
// */
//@Configuration
//@EnableConfigurationProperties(JacksonProperties.class)
//public class LocalDateTimeSerializerConfiguration {
//
//    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
//
//    @Bean
//    public LocalDateTimeSerializer localDateTimeDeserializer(JacksonProperties jacksonProperties) {
//        String dateFormat = jacksonProperties.getDateFormat();
//        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(StringUtils.isEmpty(dateFormat) ? DEFAULT_DATE_FORMAT : dateFormat));
//    }
//
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(LocalDateTimeSerializer localDateTimeDeserializer) {
//        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer);
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        JavaTimeModule module = new JavaTimeModule();
//        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
//        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
//                .modules(module)
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .build();
//        return objectMapper;
//    }
//
//}