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

    List<GarVMPermission> queryPermissionList(Map<String,Object> params);

    void savePermission(GarVMPermission garVMPermission);

    GarVMPermission searchPermission(Long permissionId);

    void updatePermission(GarVMPermission garVMPermission);

    List<GarVMPermission> queryPermissionListByApplicationId(Long applicationId);
}
