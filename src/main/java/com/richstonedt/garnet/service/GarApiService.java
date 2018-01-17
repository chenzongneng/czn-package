/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarApi;
import com.richstonedt.garnet.model.view.model.GarApiForImport;
import com.richstonedt.garnet.model.view.model.GarVmApi;

import java.util.List;
import java.util.Map;
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
public interface GarApiService extends BaseService<GarApi> {

    List<GarVmApi> queryApiList(Map<String, Object> params);

    Set<String> getPermissionsByIds(Set<Long> ids);

    void importApiFromAnnotation(List<GarApiForImport> permissionList, Long applicationId);

    List<GarVmApi> queryApiListByApplicationId(Long applicationId);

    List<GarApiForImport> getImportApiFromAnnotation(Class controllerClass);

    int queryTotalApi(Map<String, Object> params);

    GarVmApi getApiById(Long permissionsId);

    void importApiByAppCode(List<GarApiForImport> apiList, String appCode);

    Map<String,String> deleteBatchByApiIds(List<Long> apiIds);
}
