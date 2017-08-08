/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.service;

import com.richstonedt.garnet.modules.api.entity.TokenEntity;

import java.util.Map;

/**
 * 用户Token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 */
public interface TokenService {

    TokenEntity queryByUserId(Long userId);

    TokenEntity queryByToken(String token);

    void save(TokenEntity token);

    void update(TokenEntity token);

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token相关信息
     */
    Map<String, Object> createToken(long userId);

}
