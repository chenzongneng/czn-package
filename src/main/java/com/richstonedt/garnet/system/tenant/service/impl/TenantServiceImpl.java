/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.tenant.service.impl;

import com.richstonedt.garnet.system.tenant.dao.TenantDao;
import com.richstonedt.garnet.system.tenant.entity.Tenant;
import com.richstonedt.garnet.system.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>TenantServiceImpl</code></b>
 * <p/>
 * TenantServiceImpl
 * <p/>
 * <b>Creation Time:</b> 2017/8/22 11:11.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
@Service
public class TenantServiceImpl implements TenantService {

    /**
     * The Tenant dao.
     *
     * @since Garnet 1.0.0
     */
    private final TenantDao tenantDao;

    @Autowired
    public TenantServiceImpl(TenantDao tenantDao) {
        this.tenantDao = tenantDao;
    }

    /**
     * Gets tenant list.
     *
     * @return the tenant list
     */
    @Override
    public List<Tenant> getTenantList() {
        return tenantDao.getTenantList();
    }

    /**
     * Gets tenant by id.
     *
     * @param id the id
     * @return the tenant by id
     * @since Garnet 1.0.0
     */
    @Override
    public Tenant getTenantById(Long id) {
        return tenantDao.getTenantById(id);
    }

}