/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.interceptor;

import com.richstonedt.garnet.common.exception.RRException;
import com.richstonedt.garnet.modules.api.annotation.AuthIgnore;
import com.richstonedt.garnet.modules.api.entity.TokenEntity;
import com.richstonedt.garnet.modules.api.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:38
 * @since garnet-core-be-fe 1.0.0
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    /**
     * The Token service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private TokenService tokenService;

    /**
     * The constant LOGIN_USER_KEY.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    /**
     * The pre Handle.
     *
     * @param  request the HttpServletRequest
     * @param  response the HttpServletResponse
     * @param  handler the Object
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if (annotation != null) {
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        //token为空
        if (StringUtils.isBlank(token)) {
            throw new RRException("token不能为空");
        }

        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            throw new RRException("token失效，请重新登录");
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
