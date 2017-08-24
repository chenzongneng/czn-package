/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.tenant.service;

import com.richstonedt.garnet.system.tenant.entity.Tenant;

import java.util.List;

/**
 * <b><code>TenantService</code></b>
 * <p/>
 * TenantService
 * <p/>
 * <b>Creation Time:</b> 2017/8/22 11:11.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
public interface TenantService {

    /**
     * Gets tenant list.
     *
     * @return the tenant list
     * @since Garnet 1.0.0
     */
    List<Tenant> getTenantList();

    /**
     * Gets tenant by id.
     *
     * @param id the id
     * @return the tenant by id
     * @since Garnet 1.0.0
     */
    Tenant getTenantById(Long id);

}
