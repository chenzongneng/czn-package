/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.user.service;

import com.richstonedt.garnet.system.user.entity.User;

import java.util.List;

/**
 * <b><code>UserService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/29 14:52
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public interface UserService {

    /**
     * Gets user list.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the user list
     * @since garnet-core-be-fe 1.0.0
     */
    List<User> getUserList(Integer page,Integer limit,String searchName);

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     * @since garnet-core-be-fe 1.0.0
     */
    User getUserById(Integer userId);

    /**
     * Save user.
     *
     * @param user the user
     * @since garnet-core-be-fe 1.0.0
     */
    void saveUser(User user);

    /**
     * Gets all users.
     *
     * @return the all users
     * @since garnet-core-be-fe 1.0.0
     */
    List<User> getAllUsers();

    /**
     * Update user.
     *
     * @param user the user
     * @since garnet-core-be-fe 1.0.0
     */
    void updateUser(User user);

    /**
     * Delete users.
     *
     * @param userIdList the user id list
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteUsers(List<Integer> userIdList);

    /**
     * Change password.
     *
     * @param userId      the user id
     * @param password    the password
     * @param newPassword the new password
     * @since garnet-core-be-fe 1.0.0
     */
    void changePassword(Integer userId,String password,String newPassword);

    /**
     * Gets user count.
     *
     * @return the user count
     * @since garnet-core-be-fe 1.0.0
     */
    int getUserCount();
}
