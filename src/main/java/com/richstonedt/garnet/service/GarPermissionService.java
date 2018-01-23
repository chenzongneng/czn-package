/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarPermission;
import com.richstonedt.garnet.model.view.model.GarVMPermission;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarPermissionService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:44
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarPermissionService extends BaseService<GarPermission> {

    /**
     * Query permission list list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMPermission> queryPermissionList(Map<String,Object> params);

    /**
     * Save permission.
     *
     * @param garVMPermission the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    void savePermission(GarVMPermission garVMPermission);

    /**
     * Search permission gar vm permission.
     *
     * @param permissionId the permission id
     * @return the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMPermission searchPermission(Long permissionId);

    /**
     * Update permission.
     *
     * @param garVMPermission the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    void updatePermission(GarVMPermission garVMPermission);

    /**
     * Query permission list by application id list.
     *
     * @param applicationId the application id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMPermission> queryPermissionListByApplicationId(Long applicationId);
}
