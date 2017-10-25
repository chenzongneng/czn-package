/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarRoleDeptDao;
import com.richstonedt.garnet.model.GarRoleDept;
import com.richstonedt.garnet.service.GarRoleDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarRoleDeptServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/25 14:23
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarRoleDeptServiceImpl implements GarRoleDeptService {

    /**
     * The Role dept dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleDeptDao roleDeptDao;

    /**
     * Gets role dept by role id.
     *
     * @param roleId the role id
     * @return the role dept by role id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarRoleDept> getRoleDeptByRoleId(Long roleId) {
        return roleDeptDao.getRoleDeptByRoleId(roleId);
    }

    /**
     * Gets role dept by dept id.
     *
     * @param deptId the dept id
     * @return the role dept by dept id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarRoleDept> getRoleDeptByDeptId(Long deptId) {
        return roleDeptDao.getRoleDeptByDeptId(deptId);
    }

    /**
     * Delete role dept by dept id.
     *
     * @param deptId the dept id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteRoleDeptByDeptId(Long deptId) {
        roleDeptDao.deleteRoleDeptByDeptId(deptId);
    }

    /**
     * Save.
     *
     * @param garRoleDept the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarRoleDept garRoleDept) {
        roleDeptDao.save(garRoleDept);
    }

    /**
     * Update.
     *
     * @param garRoleDept the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarRoleDept garRoleDept) {
        roleDeptDao.update(garRoleDept);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        roleDeptDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        roleDeptDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarRoleDept queryObject(Long id) {
        return null;
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
    public List<GarRoleDept> queryObjects(String searchName, Integer page, Integer limit) {
        return null;
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return 0;
    }
}
