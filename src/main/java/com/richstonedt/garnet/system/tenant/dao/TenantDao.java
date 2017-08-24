/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.tenant.dao;

import com.richstonedt.garnet.system.tenant.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>TenantDao</code></b>
 * <p/>
 * TenantDao
 * <p/>
 * <b>Creation Time:</b> 2017/8/22 11:10.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
@Mapper
public interface TenantDao {

    /**
     * Gets tenant list.
     *
     * @return the tenant list
     * @since Garnet 1.0.0
     */
    List<Tenant> getTenantList();

    /**
     * Add tenant int.
     *
     * @param tenant the tenant
     * @return the int
     * @since Garnet 1.0.0
     */
    int addTenant(Tenant tenant);

    /**
     * Update tenant int.
     *
     * @param tenant the tenant
     * @return the int
     * @since Garnet 1.0.0
     */
    int updateTenant(Tenant tenant);

    /**
     * Delete by id int.
     *
     * @param id the id
     * @return the int
     * @since Garnet 1.0.0
     */
    int deleteById(@Param(value = "id") Long id);

    /**
     * Gets tenant by id.
     *
     * @param id the id
     * @return the tenant by id
     * @since Garnet 1.0.0
     */
    Tenant getTenantById(@Param(value = "id") Long id);
}
