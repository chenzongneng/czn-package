/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarApplicationTenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarUserDeptDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 9:45
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Mapper
public interface GarApplicationTenantDao extends BaseDao<GarApplicationTenant> {

    /**
     * Gets application tenant by app id.
     *
     * @param appId the app id
     * @return the application tenant by app id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApplicationTenant> getApplicationTenantByAppId(@Param(value = "appId") Long appId);

    /**
     * Gets application tenant by tenant id.
     *
     * @param tenantId the tenant id
     * @return the application tenant by tenant id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApplicationTenant> getApplicationTenantByTenantId(@Param(value = "tenantId") Long tenantId);

    /**
     * Delete role dept by tenant id.
     *
     * @param tenantId the tenant id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteRoleDeptByTenantId(@Param(value = "tenantId") Long tenantId);
}
