/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.user.service.impl;

import com.richstonedt.garnet.common.exception.GarnetServiceException;
import com.richstonedt.garnet.system.authority.service.AuthorityService;
import com.richstonedt.garnet.system.user.dao.UserDao;
import com.richstonedt.garnet.system.user.entity.User;
import com.richstonedt.garnet.system.user.service.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <b><code>UserServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/29 14:53
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Service
public class UserServiceImpl implements UserService{

    /**
     * The User dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private UserDao userDao;

    /**
     * The Authority service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private AuthorityService authorityService;

    /**
     * Gets user list.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the user list
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<User> getUserList(Integer page, Integer limit, String searchName) {
        int offset = (page - 1) * limit;
        return userDao.getUserList(searchName,limit,offset);
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    /**
     * Save user.
     *
     * @param user the user
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void saveUser(User user) {
        User userEntity = userDao.getUserByName(user.getUsername());
        if(userEntity != null){
            throw new GarnetServiceException("该用户已存在！");
        }
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        userDao.saveUser(user);

        Integer tmpUserId = userDao.getUserByName(user.getUsername()).getUserId();
        userDao.saveRole(tmpUserId);
    }

    /**
     * Gets all users.
     *
     * @return the all users
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Update user.
     *
     * @param user the user
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void updateUser(User user) {
        if(!StringUtils.isEmpty(user.getPassword())){
            user.setPassword(new Sha256Hash(user.getPassword(), getUserById(user.getUserId()).getSalt()).toHex());
        }
        try {
            userDao.updateUser(user);
        }catch (Exception e){
            throw new GarnetServiceException("该用户已存在！");
        }
    }

    /**
     * Delete users.
     *
     * @param userIdList the user id list
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteUsers(List<Integer> userIdList) {
        userDao.deleteUsers(userIdList);
        authorityService.deleteAuthority(userIdList);
    }

    /**
     * Change password.
     *
     * @param userId      the user id
     * @param password    the password
     * @param newPassword the new password
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void changePassword(Integer userId, String password, String newPassword) {
        User user = getUserById(userId);
        User tmpUser = userDao.getUserByNameAndPassword(user.getUsername(),new Sha256Hash(password,user.getSalt()).toHex());
        if(tmpUser == null){
            throw new GarnetServiceException("原密码不正确，请重新输入");
        }
        userDao.updatePassword(userId,new Sha256Hash(newPassword,user.getSalt()).toHex());
    }

    /**
     * Gets user count.
     *
     * @return the user count
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public int getUserCount() {
        return userDao.getUserCount();
    }
}
