/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by maquan on 2017/4/24.
 * 注册拦截器使其生效
 */
@Component
public class ConfigInterceptorAdapter extends WebMvcConfigurerAdapter {
    /**
     * The Cross domain interceptor adapter.
     */
    @Autowired
    private CrossDomainInterceptorAdapter crossDomainInterceptorAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossDomainInterceptorAdapter);
    }
}
