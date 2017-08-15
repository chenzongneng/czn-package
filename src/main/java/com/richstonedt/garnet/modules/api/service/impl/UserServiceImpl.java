/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.service.impl;

import com.richstonedt.garnet.common.exception.RRException;
import com.richstonedt.garnet.common.validator.Assert;
import com.richstonedt.garnet.modules.api.dao.UserDao;
import com.richstonedt.garnet.modules.api.entity.UserEntity;
import com.richstonedt.garnet.modules.api.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type User service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     * The User dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private UserDao userDao;

    /**
     * Query object user entity.
     *
     * @param userId the user id
     * @return the user entity
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public UserEntity queryObject(Long userId) {
        return userDao.queryObject(userId);
    }

    /**
     * Query list list.
     *
     * @param map the map
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<UserEntity> queryList(Map<String, Object> map) {
        return userDao.queryList(map);
    }

    /**
     * Query total int.
     *
     * @param map the map
     * @return the int
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public int queryTotal(Map<String, Object> map) {
        return userDao.queryTotal(map);
    }

    /**
     * Save.
     *
     * @param mobile   the mobile
     * @param password the password
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void save(String mobile, String password) {
        UserEntity user = new UserEntity();
        user.setMobile(mobile);
        user.setUsername(mobile);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setCreateTime(new Date());
        userDao.save(user);
    }

    /**
     * Update.
     *
     * @param user the user
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void update(UserEntity user) {
        userDao.update(user);
    }

    /**
     * Delete.
     *
     * @param userId the user id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    /**
     * Delete batch.
     *
     * @param userIds the user ids
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteBatch(Long[] userIds) {
        userDao.deleteBatch(userIds);
    }

    /**
     * Query by mobile user entity.
     *
     * @param mobile the mobile
     * @return the user entity
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public UserEntity queryByMobile(String mobile) {
        return userDao.queryByMobile(mobile);
    }

    /**
     * 用户登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return 返回用户ID
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public long login(String mobile, String password) {
        UserEntity user = queryByMobile(mobile);
        Assert.isNull(user, "手机号或密码错误");

        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            throw new RRException("手机号或密码错误");
        }

        return user.getUserId();
    }
}
