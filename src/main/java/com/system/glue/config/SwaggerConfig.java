package com.system.glue.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author Dandan
 * @date 2023/11/15 15:07
 **/


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                // 调用apiInfo方法,创建一个ApiInfo实例,
                // 里面是展示在文档页面信息内容
                .apiInfo(webApiInfo())
                .select()
                // 不显示错误的接口地址
                // .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                // .apis(RequestHandlerSelectors.basePackage("com.example.pmp.controller.fox"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/.*"))// 对根下所有路径进行监控
                .build();

    }


    // api文档的详细信息
    private ApiInfo webApiInfo() {
        // 作者信息
        Contact contact = new Contact("早起的虫儿有果儿吃", "", "");
        return new ApiInfo(
                "API接口测试文档",
                "今天的不开心就到此为止吧，明天依旧光芒万丈啊！",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }

}
