/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 * @since garnet-core-be-fe 1.0.0
 */
public class R extends HashMap<String, Object> {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new R.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public R() {
		put("code", 0);
	}

	/**
	 * Error r.
	 *
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	/**
	 * Error r.
	 *
	 * @param msg the msg
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	/**
	 * Error r.
	 *
	 * @param code the code
	 * @param msg  the msg
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	/**
	 * Ok r.
	 *
	 * @param msg the msg
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	/**
	 * Ok r.
	 *
	 * @param map the map
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	/**
	 * Ok r.
	 *
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public static R ok() {
		return new R();
	}

	/**
	 * Ok r.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
