/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserApiDao;
import com.richstonedt.garnet.model.GarToken;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <b><code>GarShiroServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/4 15:42
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarShiroServiceImpl implements GarShiroService {

    /**
     * The Token service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTokenService tokenService;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

    /**
     * The User api dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserApiDao userApiDao;

    /**
     * Gets user permissions.
     *
     * @param userId the user id
     * @return the user permissions
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Set<String> getUserPermissions(long userId) {
            return userApiDao.getApiPermissionsByUserIdAndAppCode(userId,"garnet");
    }

    /**
     * Gets by token.
     *
     * @param token the token
     * @return the by token
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarToken getByToken(String token) {
        GarToken garToken = tokenService.queryByToken(token);
        return garToken;
    }

    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUser getUser(Long userId) {
        return userService.searchUser(userId);
    }

    /**
     * Gets api permissions by user id and app code.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the api permissions by user id and app code
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public Set<String> getApiPermissionsByUserIdAndAppCode(Long userId, String appCode) {
        return userApiDao.getApiPermissionsByUserIdAndAppCode(userId, appCode);
    }
}
