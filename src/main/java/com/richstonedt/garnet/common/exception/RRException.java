/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.exception;

/**
 * 自定义异常
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:11:27
 * @since garnet-core-be-fe 1.0.0
 */
public class RRException extends RuntimeException {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Msg.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String msg;

	/**
	 * The Code.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private int code = 500;

	/**
	 * Instantiates a new Rr exception.
	 *
	 * @param msg the msg
	 * @since garnet-core-be-fe 1.0.0
	 */
	public RRException(String msg) {
		super(msg);
		this.msg = msg;
	}

	/**
	 * Instantiates a new Rr exception.
	 *
	 * @param msg the msg
	 * @param e   the e
	 * @since garnet-core-be-fe 1.0.0
	 */
	public RRException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	/**
	 * Instantiates a new Rr exception.
	 *
	 * @param msg  the msg
	 * @param code the code
	 * @since garnet-core-be-fe 1.0.0
	 */
	public RRException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	/**
	 * Instantiates a new Rr exception.
	 *
	 * @param msg  the msg
	 * @param code the code
	 * @param e    the e
	 * @since garnet-core-be-fe 1.0.0
	 */
	public RRException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	/**
	 * Gets msg.
	 *
	 * @return the msg
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets msg.
	 *
	 * @param msg the msg
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Gets code.
	 *
	 * @return the code
	 * @since garnet-core-be-fe 1.0.0
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets code.
	 *
	 * @param code the code
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
