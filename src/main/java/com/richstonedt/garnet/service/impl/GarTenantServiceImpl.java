/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarTenantDao;
import com.richstonedt.garnet.model.GarTenant;
import com.richstonedt.garnet.service.GarTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarTenantServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 15:46
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarTenantServiceImpl implements GarTenantService {

    /**
     * The Tenant dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantDao tenantDao;

    /**
     * Save.
     *
     * @param garTenant the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarTenant garTenant) {
        tenantDao.save(garTenant);
    }

    /**
     * Update.
     *
     * @param garTenant the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarTenant garTenant) {
        tenantDao.update(garTenant);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        tenantDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarTenant queryObject(Integer id) {
        return tenantDao.queryObject(id);
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
    public List<GarTenant> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return tenantDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return tenantDao.queryTotal();
    }
}
