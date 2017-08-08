/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <b><code>CorsConfig</code></b>
 * <p/>
 * 跨域配置
 * <p/>
 * <b>Creation Time:</b> 2016/12/4 17:15.
 *
 * @author sunjinpeng
 * @version $Revision$ 2016/12/4 17:15.
 * @since garnet-core-be-fe 1.0.0
 */
@Configuration
public class CorsConfig {

    /**
     * Build config cors configuration.
     *
     * @return the cors configuration
     * @since garnet-core-be-fe 1.0.0
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1.允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 2.允许任何头
        corsConfiguration.addAllowedMethod("*"); // 3.允许任何方法（post、get等）
        return corsConfiguration;
    }

    /**
     * Cors filter cors filter.
     *
     * @return the cors filter
     * @since garnet-core-be-fe 1.0.0
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
