/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.controller.GarPermissionController;
import com.richstonedt.garnet.model.GarPermission;
import com.richstonedt.garnet.model.view.model.GarPermissionForImport;
import com.richstonedt.garnet.model.view.model.GarVmPermission;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarPermissionService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:24
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarPermissionService extends BaseService<GarPermission> {

    List<GarVmPermission> queryPermissionList(String searchName,Long applicationId, Integer page, Integer limit);

    Set<String> getPermissionsByIds(Set<Long> ids);

    void importPermissionFromAnnotation(List<GarPermissionForImport> permissionList, Long applicationId);

    List<GarVmPermission> queryPermissionListByApplicationId(Long applicationId);

    List<GarPermissionForImport> getImportPermissionFromAnnotation(Class controllerClass, Long applicationId);

    int queryTotalPermission(String searchName, Long applicationId);
}
