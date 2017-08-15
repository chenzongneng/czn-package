/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context 工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月29日 下午11:45:51
 * @since garnet-core-be-fe 1.0.0
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	/**
	 * The constant applicationContext.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static ApplicationContext applicationContext;

	/**
	 * set  ApplicationContext.
	 *
	 * @param applicationContext the ApplicationContext
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	}

	/**
	 * Gets bean.
	 *
	 * @param name the name
	 * @return the bean
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * Gets bean.
	 *
	 * @param <T>          the type parameter
	 * @param name         the name
	 * @param requiredType the required type
	 * @return the bean
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * Contains bean boolean.
	 *
	 * @param name the name
	 * @return the boolean
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * Is singleton boolean.
	 *
	 * @param name the name
	 * @return the boolean
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static boolean isSingleton(String name) {
		return applicationContext.isSingleton(name);
	}

	/**
	 * Gets type.
	 *
	 * @param name the name
	 * @return the type
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static Class<? extends Object> getType(String name) {
		return applicationContext.getType(name);
	}

}