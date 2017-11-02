/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.config.log;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <b><code>GarLogSQLConfig</code></b>
 * 获取所需要的 Mybatis log
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/28 16:45
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public class GarLogSQLConfig<E> extends ConsoleAppender<E> {

    /**
     * The constant LOGGER_CLASS_NAME.
     * 需要过滤的日志名字
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final String LOGGER_CLASS_NAME = "com.richstonedt.garnet";

    /**
     * Append
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected void append(E e) {
        super.append(e);
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();
        String url = request.getRequestURI();

        LoggingEvent le = (LoggingEvent) e;
        //如果是Mybatis中的日志，采用自定义的方式处理，否则按默认方式处理
        if (le.getLoggerName().contains(LOGGER_CLASS_NAME)) {
            GarSqlConfig.setSqlWithRequest(le, url + method);
        }
    }
}
