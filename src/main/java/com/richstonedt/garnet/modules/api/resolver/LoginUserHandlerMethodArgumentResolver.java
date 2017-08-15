/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.resolver;

import com.richstonedt.garnet.modules.api.annotation.LoginUser;
import com.richstonedt.garnet.modules.api.entity.UserEntity;
import com.richstonedt.garnet.modules.api.interceptor.AuthorizationInterceptor;
import com.richstonedt.garnet.modules.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 22:02
 * @since garnet-core-be-fe 1.0.0
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private UserService userService;

    /**
     * The supports Parameter.
     *
     * @param parameter the Method Parameter
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * The resolve Argument.
     *
     * @param parameter the Method Parameter
     * @param container the Model And View Container
     * @param request the Native WebRequest
     * @param factory the Web Data Binder Factory
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }

        //获取用户信息
        UserEntity user = userService.queryObject((Long) object);

        return user;
    }
}
