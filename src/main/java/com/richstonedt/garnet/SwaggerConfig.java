/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <b><code>SwaggerConfig</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/5/30 11:48
 *
 * @author sunjinpeng
 * @version 0.1.0
 * @since cmcznm-be 0.1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Create rest api docket.
     *
     * @return the docket
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.host("192.168.6.23") 用于改变请求的url
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.richstonedt.garnet"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api info api info.
     *
     * @return the api info
     * @since garnet-core-be-fe 1.0.0
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("权限管理系统后端 RESTful APIs")
                .description("garnet-core-be RESTful APIs")
                .version("0.1.0")
                .license("Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,All rights reserved.")
                .build();
    }
}
