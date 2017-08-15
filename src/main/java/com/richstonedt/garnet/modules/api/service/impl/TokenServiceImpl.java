/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.service.impl;

import com.richstonedt.garnet.modules.api.dao.TokenDao;
import com.richstonedt.garnet.modules.api.entity.TokenEntity;
import com.richstonedt.garnet.modules.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The type Token service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    /**
     * The Token dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private TokenDao tokenDao;

    /**
     * The constant EXPIRE.  12小时后过期
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private final static int EXPIRE = 3600 * 12;

    /**
     * Query by user id token entity.
     *
     * @param userId the user id
     * @return the token entity
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public TokenEntity queryByUserId(Long userId) {
        return tokenDao.queryByUserId(userId);
    }

    /**
     * Query by token token entity.
     *
     * @param token the token
     * @return the token entity
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public TokenEntity queryByToken(String token) {
        return tokenDao.queryByToken(token);
    }

    /**
     * Save.
     *
     * @param token the token
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void save(TokenEntity token) {
        tokenDao.save(token);
    }

    /**
     * Update.
     *
     * @param token the token
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void update(TokenEntity token) {
        tokenDao.update(token);
    }

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token相关信息
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public Map<String, Object> createToken(long userId) {
        //生成一个token
        String token = UUID.randomUUID().toString();
        //当前时间
        Date now = new Date();

        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        TokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            update(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", EXPIRE);

        return map;
    }
}
