/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.service;

import com.richstonedt.garnet.modules.api.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 * @since garnet-core-be-fe 1.0.0
 */
public interface UserService {

    /**
     * Query object user entity.
     *
     * @param userId the user id
     * @return the user entity
     * @since garnet-core-be-fe 1.0.0
     */
    UserEntity queryObject(Long userId);

    /**
     * Query list list.
     *
     * @param map the map
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<UserEntity> queryList(Map<String, Object> map);

    /**
     * Query total int.
     *
     * @param map the map
     * @return the int
     * @since garnet-core-be-fe 1.0.0
     */
    int queryTotal(Map<String, Object> map);

    /**
     * Save.
     *
     * @param mobile   the mobile
     * @param password the password
     * @since garnet-core-be-fe 1.0.0
     */
    void save(String mobile, String password);

    /**
     * Update.
     *
     * @param user the user
     * @since garnet-core-be-fe 1.0.0
     */
    void update(UserEntity user);

    /**
     * Delete.
     *
     * @param userId the user id
     * @since garnet-core-be-fe 1.0.0
     */
    void delete(Long userId);

    /**
     * Delete batch.
     *
     * @param userIds the user ids
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteBatch(Long[] userIds);

    /**
     * Query by mobile user entity.
     *
     * @param mobile the mobile
     * @return the user entity
     * @since garnet-core-be-fe 1.0.0
     */
    UserEntity queryByMobile(String mobile);

    /**
     * 用户登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return 返回用户ID
     * @since garnet-core-be-fe 1.0.0
     */
    long login(String mobile, String password);
}
