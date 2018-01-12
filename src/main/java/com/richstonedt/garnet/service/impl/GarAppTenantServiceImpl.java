/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarApplicationTenantDao;
import com.richstonedt.garnet.model.GarApplicationTenant;
import com.richstonedt.garnet.service.GarAppTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarAppTenantServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/11/3 15:17
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarAppTenantServiceImpl implements GarAppTenantService {

    @Autowired
    private GarApplicationTenantDao applicationTenantDao;

    /**
     * Save.
     *
     * @param garApplicationTenant the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarApplicationTenant garApplicationTenant) {
        applicationTenantDao.save(garApplicationTenant);
    }

    /**
     * Update.
     *
     * @param garApplicationTenant the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarApplicationTenant garApplicationTenant) {

    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        applicationTenantDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        applicationTenantDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarApplicationTenant queryObject(Long id) {
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
    public List<GarApplicationTenant> queryObjects(Map<String,Object> params) {
        return applicationTenantDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String,Object> params) {
        return applicationTenantDao.queryTotal(params);
    }

    /**
     * Gets application tenant by app id.
     *
     * @param appId the app id
     * @return the application tenant by app id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarApplicationTenant> getApplicationTenantByAppId(Long appId) {
        return applicationTenantDao.getApplicationTenantByAppId(appId);
    }

    /**
     * Gets application tenant by tenant id.
     *
     * @param tenantId the tenant id
     * @return the application tenant by tenant id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarApplicationTenant> getApplicationTenantByTenantId(Long tenantId) {
        return applicationTenantDao.getApplicationTenantByTenantId(tenantId);
    }

    /**
     * Delete role dept by tenant id.
     *
     * @param tenantId the tenant id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteApplicationTenantByTenantId(Long tenantId) {
        applicationTenantDao.deleteRoleDeptByTenantId(tenantId);
    }
}
