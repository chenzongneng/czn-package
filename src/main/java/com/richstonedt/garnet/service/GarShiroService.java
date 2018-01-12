/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;


import com.richstonedt.garnet.model.GarToken;
import com.richstonedt.garnet.model.GarUser;

import java.util.Set;

/**
 * shiro相关接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-06 8:49
 * @since garnet-core-be-fe 1.0.0
 */
public interface GarShiroService {

    /**
     * 获取用户权限列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    Set<String> getUserPermissions(long userId);

    /**
     * Query by token sys user token entity.
     *
     * @param token the token
     * @return the sys user token entity
     * @since garnet-core-be-fe 1.0.0
     */
    GarToken getByToken(String token);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId the User id
     * @since garnet-core-be-fe 1.0.0
     */
    GarUser getUser(Long userId);

    Set<String> getApiPermissionsByUserIdAndAppCode(Long userId, String appCode);

}
