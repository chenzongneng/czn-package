/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <b><code>Application</code></b>
 * <p/>
 * 程序入口
 * <p/>
 * <b>Creation Time:</b> 2017/8/8 11:01.
 *
 * @author chenzechao
 * @version $Revision$ 2017/8/8 11:01.
 * @since garnet-core-be-fe 1.0.0
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class Application extends SpringBootServletInitializer {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    /**
     * Configure spring application builder.
     *
     * @param application the application
     * @return the spring application builder
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @since garnet-core-be-fe 1.0.0
     */
    public static void main(String[] args) {
        LOG.info("Application Start!");
        SpringApplication.run(Application.class, args);
    }

}
