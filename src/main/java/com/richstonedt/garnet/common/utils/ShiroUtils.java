/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import com.richstonedt.garnet.common.exception.RRException;
import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月12日 上午9:49:19
 * @since garnet-core-be-fe 1.0.0
 */
public class ShiroUtils {

	/**
	 * Gets session.
	 *
	 * @return the session
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * Gets subject.
	 *
	 * @return the subject
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * Gets user entity.
	 *
	 * @return the user entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static SysUserEntity getUserEntity() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static Long getUserId() {
		return getUserEntity().getUserId();
	}

	/**
	 * Sets session attribute.
	 *
	 * @param key   the key
	 * @param value the value
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * Gets session attribute.
	 *
	 * @param key the key
	 * @return the session attribute
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * Is login boolean.
	 *
	 * @return the boolean
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static boolean isLogin() {
		return getSubject().getPrincipal() != null;
	}

	/**
	 * Logout.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static void logout() {
		getSubject().logout();
	}

	/**
	 * Gets kaptcha.
	 *
	 * @param key the key
	 * @return the kaptcha
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new RRException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

}
