package com.system.glue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Dandan
 * @date 2023/11/15 15:07
 **/

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // 允许所有路径进行跨域请求
                        .allowedOriginPatterns("*")  // 允许所有源地址
                        .allowedMethods("*")  // 允许所有请求方法
                        .allowedHeaders("*")  // 允许所有请求头
                        .allowCredentials(true);  // 允许发送身份验证信息（如cookies等）
            }
        };
    }

}
