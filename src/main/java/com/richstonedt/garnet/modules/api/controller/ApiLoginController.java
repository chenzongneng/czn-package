/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.controller;


import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.common.validator.Assert;
import com.richstonedt.garnet.modules.api.annotation.AuthIgnore;
import com.richstonedt.garnet.modules.api.service.TokenService;
import com.richstonedt.garnet.modules.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * API登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/api")
public class ApiLoginController {

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private UserService userService;

    /**
     * The Token service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @AuthIgnore
    @PostMapping("login")
    public Result login(String mobile, String password){
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        //用户登录
        long userId = userService.login(mobile, password);

        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return Result.ok(map);
    }

}
