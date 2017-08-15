/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.oauth2;

import com.google.gson.Gson;
import com.richstonedt.garnet.common.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-20 13:00
 * @since garnet-core-be-fe 1.0.0
 */
public class OAuth2Filter extends AuthenticatingFilter {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static Logger LOG = LoggerFactory.getLogger(OAuth2Filter.class);

    /**
     * The create  Token.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new OAuth2Token(token);
    }

    /**
     *is  Access  Allowed
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * 没有权限
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String json = new Gson().toJson(Result.error(HttpStatus.SC_UNAUTHORIZED, "invalid token"));
            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    /**
     * 登录失败
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            Result result = Result.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

            String json = new Gson().toJson(result);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            LOG.error(e1.getMessage());
        }

        return false;
    }

    /**
     * 获取请求的token
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }


}
