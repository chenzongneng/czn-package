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

    /**
     * Query api list list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVmApi> queryApiList(Map<String, Object> params);

    /**
     * Gets permissions by ids.
     *
     * @param ids the ids
     * @return the permissions by ids
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> getPermissionsByIds(Set<Long> ids);

    /**
     * Import api from annotation.
     *
     * @param permissionList the permission list
     * @param applicationId  the application id
     * @since garnet-core-be-fe 0.1.0
     */
    void importApiFromAnnotation(List<GarApiForImport> permissionList, Long applicationId);

    /**
     * Query api list by application id list.
     *
     * @param applicationId the application id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVmApi> queryApiListByApplicationId(Long applicationId);

    /**
     * Gets import api from annotation.
     *
     * @param controllerClass the controller class
     * @return the import api from annotation
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApiForImport> getImportApiFromAnnotation(Class controllerClass);

    /**
     * Query total api int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    int queryTotalApi(Map<String, Object> params);

    /**
     * Gets api by id.
     *
     * @param permissionsId the permissions id
     * @return the api by id
     * @since garnet-core-be-fe 0.1.0
     */
    GarVmApi getApiById(Long permissionsId);

    /**
     * Import api by app code.
     *
     * @param apiList the api list
     * @param appCode the app code
     * @since garnet-core-be-fe 0.1.0
     */
    void importApiByAppCode(List<GarApiForImport> apiList, String appCode);

    /**
     * Delete batch by api ids map.
     *
     * @param apiIds the api ids
     * @return the map
     * @since garnet-core-be-fe 0.1.0
     */
    Map<String,String> deleteBatchByApiIds(List<Long> apiIds);
}
