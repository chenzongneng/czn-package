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

    @Autowired
    private GarTokenService tokenService;

    @Autowired
    private GarUserService userService;

    @Autowired
    private GarUserApiDao userApiDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
            return userApiDao.getApiPermissionsByUserIdAndAppCode(userId,"garnet");
    }

    @Override
    public GarToken getByToken(String token) {
        GarToken garToken = tokenService.queryByToken(token);
        return garToken;
    }

    @Override
    public GarUser getUser(Long userId) {
        return userService.searchUser(userId);
    }

    @Override
    public Set<String> getApiPermissionsByUserIdAndAppCode(Long userId, String appCode) {
        return userApiDao.getApiPermissionsByUserIdAndAppCode(userId, appCode);
    }
}
