/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.controller;


import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.modules.api.annotation.AuthIgnore;
import com.richstonedt.garnet.modules.api.annotation.LoginUser;
import com.richstonedt.garnet.modules.api.entity.TokenEntity;
import com.richstonedt.garnet.modules.api.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API测试接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:47
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/api")
public class ApiTestController {

    /**
     * 获取用户信息
     *
     * @param user the UserEntity
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @GetMapping("userInfo")
    public Result userInfo(@LoginUser UserEntity user){
        return Result.ok().put("user", user);
    }

    /**
     * 忽略Token验证测试
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @AuthIgnore
    @GetMapping("notToken")
    public Result notToken(){
        return Result.ok().put("msg", "无需token也能访问。。。");
    }

    /**
     * 接收JSON数据
     *
     * @param user the UserEntity
     * @param token the TokenEntity
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @PostMapping("jsonData")
    public Result jsonData(@LoginUser UserEntity user, @RequestBody TokenEntity token){
        return Result.ok().put("user", user).put("token", token);
    }
}
