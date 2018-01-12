/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserDeptDao;
import com.richstonedt.garnet.model.GarUserDept;
import com.richstonedt.garnet.service.GarUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b><code>GarUserDeptServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:30
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarUserDeptServiceImpl implements GarUserDeptService {

    /**
     * The User dept dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserDeptDao userDeptDao;

    /**
     * Query Object By Dept Id.
     *
     * @param deptId the deptId
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarUserDept> getUserDeptByDeptId(Long deptId) {
        return userDeptDao.getUserDeptByDeptId(deptId);
    }

    /**
     * Delte user dept by dept id.
     *
     * @param deptId the dept id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteUserDeptByDeptId(Long deptId) {
        userDeptDao.deleteUserDeptByDeptId(deptId);
    }

    /**
     * Gets user dept by user id.
     *
     * @param userId the user id
     * @return the user dept by user id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarUserDept> getUserDeptByUserId(Long userId) {
        return userDeptDao.getUserDeptByUserId(userId);
    }

    @Override
    public Set<Long> getDeptIdsByUserId(Long userId) {
        return userDeptDao.getDeptIdsByUserId(userId);
    }

    /**
     * Save.
     *
     * @param garUserDept the garUserDept
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarUserDept garUserDept) {
        userDeptDao.save(garUserDept);
    }

    /**
     * Update.
     *
     * @param garUserDept the garUserDept
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarUserDept garUserDept) {
        userDeptDao.update(garUserDept);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        userDeptDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        userDeptDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUserDept queryObject(Long id) {
        return userDeptDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param limit      the limit
     * @param page       the offset
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarUserDept> queryObjects(Map<String,Object> params) {
        return userDeptDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String,Object> params) {
        return userDeptDao.queryTotal(params);
    }
}
