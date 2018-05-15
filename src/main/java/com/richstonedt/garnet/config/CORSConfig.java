/*
 * Copyright (c) 2018. @author lev-gc
 */

package com.richstonedt.garnet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <b><code>CORSConfig</code></b>
 * <p/>
 * The bean of CORSConfig
 * <p/>
 * <b>Creation Time:</b> 2016/12/4 17:15.
 *
 * @author Elvis
 */
@Configuration
public class CORSConfig {

    /**
     * Build config cors configuration.
     *
     * @return the cors configuration
     */
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");                // 1.allow all domain
        corsConfiguration.addAllowedHeader("*");                // 2.allow all Header
        corsConfiguration.addAllowedMethod("*");                // 3.allow all method(post,get etc.）
        return corsConfiguration;
    }

    /**
     * Cors filter.
     *
     * @return the cors filter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
