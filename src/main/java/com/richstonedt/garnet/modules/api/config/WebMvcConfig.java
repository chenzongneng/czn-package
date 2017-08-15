/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.config;

import com.richstonedt.garnet.modules.api.interceptor.AuthorizationInterceptor;
import com.richstonedt.garnet.modules.api.resolver.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * MVC配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 22:30
 * @since garnet-core-be-fe 1.0.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * The Authorization interceptor.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    /**
     * The Login user handler method argument resolver.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    /**
     * add  Interceptors
     *
     * @param registry the InterceptorRegistry
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/api/**");
    }

    /**
     * the add Argument Resolvers
     *
     * @param argumentResolvers the List<HandlerMethodArgumentResolver>
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }
}