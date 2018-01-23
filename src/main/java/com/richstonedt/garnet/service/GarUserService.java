/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.view.model.GarVMUser;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarUserService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarUserService extends BaseService<GarUser> {

    /**
     * Gat user by name gar user.
     *
     * @param userName the user name
     * @return the gar user
     * @since garnet-core-be-fe 0.1.0
     */
    GarUser getUserByName(String userName);

    /**
     * Gets user by name and app id.
     *
     * @param userName the user name
     * @param appId    the app id
     * @return the user by name and app id
     * @since garnet-core-be-fe 0.1.0
     */
    GarUser getUserByNameAndAppId(String userName,Long appId);

    /**
     * Query user list list.
     *
     * @param token      the token
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMUser> queryUserList(String token, String searchName, Integer page, Integer limit);

    /**
     * Save user.
     *
     * @param garVMUser the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    void saveUser(GarVMUser garVMUser);

    /**
     * Update user.
     *
     * @param garVMUser the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    void updateUser(GarVMUser garVMUser);

    /**
     * Search user gar vm user.
     *
     * @param userId the user id
     * @return the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMUser searchUser(Long userId);

    /**
     * Change password.
     *
     * @param userId      the user id
     * @param oldPassword the old password
     * @param newPassword the new password
     * @since garnet-core-be-fe 0.1.0
     */
    void changePassword(Long userId, String oldPassword, String newPassword);
}
