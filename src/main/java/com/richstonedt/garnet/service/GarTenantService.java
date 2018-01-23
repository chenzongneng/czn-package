/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarTenant;
import com.richstonedt.garnet.model.view.model.GarVMTenant;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarTenantService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarTenantService extends BaseService<GarTenant> {

    /**
     * Save tenant.
     *
     * @param vmTenant the vm tenant
     * @since garnet-core-be-fe 0.1.0
     */
    void saveTenant(GarVMTenant vmTenant);

    /**
     * Gets vm tenant by tenant id.
     *
     * @param tenantId the tenant id
     * @return the vm tenant by tenant id
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMTenant getVmTenantByTenantId(Long tenantId);

    /**
     * Query vm tenants list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMTenant> queryVmTenants(Map<String,Object> params);

    /**
     * Update vm tenant.
     *
     * @param vmTenant the vm tenant
     * @since garnet-core-be-fe 0.1.0
     */
    void updateVmTenant(GarVMTenant vmTenant);
}
