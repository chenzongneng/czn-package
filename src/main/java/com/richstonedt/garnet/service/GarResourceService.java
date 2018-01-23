/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarResource;
import com.richstonedt.garnet.model.view.model.GarVMResource;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarResourceService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:44
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarResourceService extends BaseService<GarResource> {

    /**
     * Query resource list by params list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMResource> queryResourceListByParams(Map<String, Object> params);

    /**
     * Query resource list by app id list.
     *
     * @param appId the app id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMResource> queryResourceListByAppId(Long appId);

    /**
     * Save resource.
     *
     * @param garVMResource the gar vm resource
     * @since garnet-core-be-fe 0.1.0
     */
    void saveResource(GarVMResource garVMResource);

    /**
     * Search resource gar vm resource.
     *
     * @param resourceId the resource id
     * @return the gar vm resource
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMResource searchResource(Long resourceId);

    /**
     * Update resource.
     *
     * @param garVMResource the gar vm resource
     * @since garnet-core-be-fe 0.1.0
     */
    void updateResource(GarVMResource garVMResource);

    /**
     * Query total resource by param int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    int queryTotalResourceByParam(Map<String, Object> params);

    /**
     * Delete batch by resource ids map.
     *
     * @param resourceIds the resource ids
     * @return the map
     * @since garnet-core-be-fe 0.1.0
     */
    Map<String, String> deleteBatchByResourceIds(List<Long> resourceIds);
}
