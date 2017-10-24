/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.config.GarnetServiceErrorCodes;
import com.richstonedt.garnet.config.GarnetServiceException;
import com.richstonedt.garnet.dao.GarUserDao;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.GarUserDept;
import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.*;
import com.richstonedt.garnet.utils.IdGeneratorUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    /**
     * The Tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantService tenantService;

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationService applicationService;

    /**
     * The User dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserDeptService userDeptService;

    /**
     * The Dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
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
        //todo  考虑加入事务
        userDao.deleteBatch(ids);
        userDeptService.deleteBatch(ids);
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

    /**
     * Query user list list.
     *
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMUser> queryUserList(String searchName, Integer page, Integer limit) {
        List<GarUser> garUsers = queryObjects(searchName, page, limit);
        if (CollectionUtils.isEmpty(garUsers)) {
            return null;
        }
        List<GarVMUser> result = new ArrayList<>();
        for (GarUser user : garUsers) {
            GarVMUser vmUser = convertUserToVmUser(user);
            result.add(vmUser);
        }
        return result;
    }

    /**
     * Save user.
     *
     * @param garVMUser the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void saveUser(GarVMUser garVMUser) {
        if (userDao.getUserByName(garVMUser.getUserName()) != null) {
            throw new GarnetServiceException("用户名已存在", GarnetServiceErrorCodes.OBJECT_EXISTS);
        }
        Long deptId = garVMUser.getDeptId();
        if (garVMUser.getUserId() == null) {
            garVMUser.setUserId(IdGeneratorUtil.generateId());
        }
        GarUserDept userDept = new GarUserDept();
        userDept.setDeptId(deptId);
        userDept.setUserId(garVMUser.getUserId());
        save(garVMUser);
        userDeptService.save(userDept);
    }

    /**
     * Update user.
     *
     * @param garVMUser the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updateUser(GarVMUser garVMUser) {
        if (getUserByName(garVMUser.getUserName()) != null) {
            throw new GarnetServiceException("用户名已存在", GarnetServiceErrorCodes.OBJECT_EXISTS);
        }
        if (!StringUtils.isEmpty(garVMUser.getPassword())) {
            garVMUser.setPassword(BCrypt.hashpw(garVMUser.getPassword(), BCrypt.gensalt(12)));
        }
        update(garVMUser);
        GarUserDept userDept = new GarUserDept();
        userDept.setUserId(garVMUser.getUserId());
        userDept.setDeptId(garVMUser.getDeptId());
        userDeptService.update(userDept);
    }

    /**
     * Search user.
     *
     * @param userId the user id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVMUser searchUser(Long userId) {
        return convertUserToVmUser(queryObject(userId));
    }

    /**
     * Search user dept gar user dept.
     *
     * @param userId the user id
     * @return the gar user dept
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUserDept searchUserDept(Long userId) {
        return userDeptService.queryObject(userId);
    }

    /**
     * Convert user to vm user gar vm user.
     *
     * @param user the user
     * @return the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMUser convertUserToVmUser(GarUser user) {
        GarVMUser vmUser = new GarVMUser();
        String tenantName = tenantService.queryObject(user.getTenantId()).getName();
        String appName = applicationService.queryObject(user.getAppId()).getName();
        Long deptId = userDeptService.queryObject(user.getUserId()).getDeptId();
        String deptName = deptService.queryObject(deptId).getName();
        vmUser.setUserId(user.getUserId());
        vmUser.setTenantId(user.getTenantId());
        vmUser.setAppId(user.getAppId());
        vmUser.setDeptId(deptId);
        vmUser.setPassword(user.getPassword());
        vmUser.setAppName(appName);
        vmUser.setDeptName(deptName);
        vmUser.setTenantName(tenantName);
        vmUser.setUserId(user.getUserId());
        vmUser.setUserName(user.getUserName());
        vmUser.setEmail(user.getEmail());
        vmUser.setMobile(user.getMobile());
        vmUser.setStatus(user.getStatus());
        vmUser.setCreateTime(user.getCreateTime());
        return vmUser;
    }
}
