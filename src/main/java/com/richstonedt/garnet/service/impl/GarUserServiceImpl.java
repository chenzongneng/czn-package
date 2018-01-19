/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.config.GarnetServiceErrorCodes;
import com.richstonedt.garnet.config.GarnetServiceException;
import com.richstonedt.garnet.dao.GarUserApplicationDao;
import com.richstonedt.garnet.dao.GarUserDao;
import com.richstonedt.garnet.model.GarDept;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.GarUserDept;
import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.*;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

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
     * The Token service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTokenService tokenService;

    @Autowired
    private GarUserApplicationDao userApplicationDao;

    /**
     * Save.
     *
     * @param garUser the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarUser garUser) {
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
        userDeptService.deleteBatch(ids);
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
    public List<GarUser> queryObjects(Map<String,Object> params) {
        return userDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String,Object> params) {
        return userDao.queryTotal(params);
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
    public GarUser getUserByNameAndAppId(String userName, Long appId) {
        return userDao.getUserByNameAndAppId(userName,appId);
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
    public List<GarVMUser> queryUserList(String token, String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        GarUser currentUser = tokenService.getUserInfoByToken(token);
        List<GarUser> garUsers = userDao.queryUserList(currentUser.getTenantId(), searchName, limit, offset);
        if (CollectionUtils.isEmpty(garUsers)) {
            return null;
        }
        List<GarVMUser> result = new ArrayList<>();
        for (GarUser user : garUsers) {
            result.add(convertUserToVmUser(user));
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
            throw new GarnetServiceException("用户账号已存在", GarnetServiceErrorCodes.OBJECT_EXISTS);
        }
        if (garVMUser.getUserId() == null) {
            garVMUser.setUserId(IdGeneratorUtil.generateId());
        }
        garVMUser.setCreateTime(new Date());
        save(garVMUser);
        saveUserDept(garVMUser);
        saveUserApplication(garVMUser);
    }

    /**
     * Update user.
     *
     * @param garVMUser the gar vm user
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updateUser(GarVMUser garVMUser) {
        if (!StringUtils.isEmpty(garVMUser.getPassword())) {
            garVMUser.setPassword(BCrypt.hashpw(garVMUser.getPassword(), BCrypt.gensalt(12)));
        }
        userDeptService.deleteById(garVMUser.getUserId());
        saveUserDept(garVMUser);
        userApplicationDao.deleteByUserId(garVMUser.getUserId());
        saveUserApplication(garVMUser);
        update(garVMUser);
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
     * Change password.
     *
     * @param userId      the user id
     * @param oldPassword the old password
     * @param newPassword the new password
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        GarUser user = queryObject(userId);
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new GarnetServiceException("原密码不正确，请重新输入");
        }
        userDao.updatePassword(userId, BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
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
//        String tenantName = tenantService.queryObject(user.getTenantId()).getName();
//        String appName = applicationService.queryObject(user.getAppId()).getName();

        // 获取该用户的部门列表
        List<String> deptNameList = new ArrayList<>();
        List<Long> deptIdList = new ArrayList<>();
        List<GarUserDept> userDeptList = userDeptService.getUserDeptByUserId(user.getUserId());
        if (!CollectionUtils.isEmpty(userDeptList)) {
            for (GarUserDept userDept : userDeptList) {
                deptIdList.add(userDept.getDeptId());
                GarDept dept = deptService.queryObject(userDept.getDeptId());
                if (!ObjectUtils.isEmpty(dept)) {
                    deptNameList.add(dept.getName());
                }
            }
        }
        vmUser.setApplicationIdList(userApplicationDao.getApplicationIdByUserId(user.getUserId()));
        vmUser.setDeptIdList(deptIdList);
        vmUser.setDeptNameList(deptNameList);

        vmUser.setUserId(user.getUserId());
        vmUser.setTenantId(user.getTenantId());
        vmUser.setAppId(user.getAppId());
        vmUser.setPassword(user.getPassword());
        vmUser.setName(user.getName());
        vmUser.setUserName(user.getUserName());
        vmUser.setEmail(user.getEmail());
        vmUser.setMobile(user.getMobile());
        vmUser.setStatus(user.getStatus());
        vmUser.setCreateTime(user.getCreateTime());
        return vmUser;
    }

    /**
     * Save user dept.
     *
     * @param vmUser the vm user
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveUserDept(GarVMUser vmUser) {
        List<Long> deptIdList = GarnetRsUtil.parseStringToList(vmUser.getDeptIds());
        if (!CollectionUtils.isEmpty(deptIdList)) {
            for (Long deptId : deptIdList) {
                GarUserDept userDept = new GarUserDept();
                userDept.setUserId(vmUser.getUserId());
                userDept.setDeptId(deptId);
                userDeptService.save(userDept);
            }
        }
    }
    /**
     * Save user application.
     *
     * @param vmUser the vm user
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveUserApplication(GarVMUser vmUser) {
        List<Long> applicationIdList = GarnetRsUtil.parseStringToList(vmUser.getApplicationIds());
        if (!CollectionUtils.isEmpty(applicationIdList)) {
            for (Long applicationId : applicationIdList) {
                userApplicationDao.save(vmUser.getUserId(),applicationId);
            }
        }
    }
}
