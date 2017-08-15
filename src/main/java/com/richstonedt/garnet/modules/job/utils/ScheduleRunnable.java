/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.utils;

import com.richstonedt.garnet.common.exception.RRException;
import com.richstonedt.garnet.common.utils.SpringContextUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 执行定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月30日 下午12:49:33
 * @since garnet-core-be-fe 1.0.0
 */
public class ScheduleRunnable implements Runnable {

    /**
     * The Target.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Object target;

    /**
     * The Method.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Method method;

    /**
     * The Params.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String params;

    /**
     * Instantiates a new Schedule runnable.
     *
     * @param beanName   the bean name
     * @param methodName the method name
     * @param params     the params
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException     the security exception
     * @since garnet-core-be-fe 1.0.0
     */
    public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
        this.target = SpringContextUtils.getBean(beanName);
        this.params = params;

        if (StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    /**
     * The run.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            throw new RRException("执行定时任务失败", e);
        }
    }

}
