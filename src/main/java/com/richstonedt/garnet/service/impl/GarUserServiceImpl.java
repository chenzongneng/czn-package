/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserDao;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.*;
import com.richstonedt.garnet.utils.IdGeneratorUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarUserServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 11:27
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarUserServiceImpl implements GarUserService {

    /**
     * The User dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserDao userDao;

    @Autowired
    private GarTenantService tenantService;

    @Autowired
    private GarApplicationService applicationService;

    @Autowired
    private GarUserDeptService userDeptService;

    @Autowired
    private GarDeptService deptService;

    /**
     * Save.
     *
     * @param garUser the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarUser garUser) {
        garUser.setUserId(IdGeneratorUtil.generateId());
        String password = BCrypt.hashpw(garUser.getPassword(), BCrypt.gensalt(12));
        garUser.setPassword(password);
        userDao.save(garUser);
    }

    /**
     * Update.
     *
     * @param garUser the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarUser garUser) {
        userDao.update(garUser);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        userDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUser queryObject(Long id) {
        return userDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param page       the offset
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarUser> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return userDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return userDao.queryTotal();
    }

    /**
     * Gat user by name gar user.
     *
     * @param userName the user name
     * @return the gar user
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUser getUserByName(String userName) {
        return userDao.getUserByName(userName);
    }

    @Override
    public List<GarVMUser> queryUserList(String searchName, Integer page, Integer limit) {
        List<GarUser> garUsers = queryObjects(searchName, page, limit);
        if (CollectionUtils.isEmpty(garUsers)) {
            return null;
        }
        List<GarVMUser> result = new ArrayList<>();
        for (GarUser user : garUsers) {
            GarVMUser vmUser = new GarVMUser();
            String tenantName = tenantService.queryObject(user.getTenantId()).getName();
            String appName = applicationService.queryObject(user.getAppId()).getName();
            Long deptId = userDeptService.queryObject(user.getUserId()).getDeptId();
            String deptName = deptService.queryObject(deptId).getName();
            vmUser.setAppName(appName);
            vmUser.setDeptName(deptName);
            vmUser.setTenantName(tenantName);
            vmUser.setUserId(user.getUserId());
            vmUser.setUserName(user.getUserName());
            vmUser.setEmail(user.getEmail());
            vmUser.setMobile(user.getMobile());
            vmUser.setStatus(user.getStatus());
            vmUser.setCreateTime(user.getCreateTime());
            result.add(vmUser);
        }
        return result;
    }
}
