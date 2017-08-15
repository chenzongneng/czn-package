/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.exception;

import com.richstonedt.garnet.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 * @since garnet-core-be-fe 1.0.0
 */
@RestControllerAdvice
public class RRExceptionHandler {

	/**
	 * The Logger.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	/**
	 * Handle duplicate key exception r.
	 *
	 * @param e the e
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	/**
	 * Handle authorization exception r.
	 *
	 * @param e the e
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	/**
	 * Handle exception r.
	 *
	 * @param e the e
	 * @return the r
	 * @since garnet-core-be-fe 1.0.0
	 */
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
