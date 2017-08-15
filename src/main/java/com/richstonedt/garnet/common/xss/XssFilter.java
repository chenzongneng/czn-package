/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-01 10:20
 * @since garnet-core-be-fe 1.0.0
 */
public class XssFilter implements Filter {

	/**
	 * init
	 *
	 * @param config  the FilterConfig
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	/**
	 * doFilter
	 *
	 * @param request  the ServletRequest
	 * @param response  the ServletResponse
	 * @param chain  the FilterChain
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	/**
	 * destroy
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void destroy() {
	}

}