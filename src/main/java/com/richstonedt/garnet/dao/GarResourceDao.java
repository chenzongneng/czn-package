/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b><code>GarResourceDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:39
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarResourceDao extends BaseDao<GarResource> {

    /**
     * Gets resource name by code.
     *
     * @param code the code
     * @return the resource name by code
     * @since garnet-core-be-fe 0.1.0
     */
    String getResourceNameByCode(@Param("code") String code);

    /**
     * Gets resource id by code.
     *
     * @param code the code
     * @return the resource id by code
     * @since garnet-core-be-fe 0.1.0
     */
    Long getResourceIdByCode(@Param("code") String code);

    /**
     * Gets resources by app id.
     *
     * @param appId the app id
     * @return the resources by app id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarResource> getResourcesByAppId(@Param("applicationId") Long appId);

    /**
     * Gets resources by params.
     *
     * @param params the params
     * @return the resources by params
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarResource> getResourcesByParams(Map<String, Object> params);

    /**
     * Gets total resource by param.
     *
     * @param params the params
     * @return the total resource by param
     * @since garnet-core-be-fe 0.1.0
     */
    int getTotalResourceByParam(Map<String, Object> params);

    /**
     * Query resource name by parent ids set.
     *
     * @param resourceIds the resource ids
     * @return the set
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> queryResourceNameByParentIds(@Param("parentIds")List<Long> resourceIds);
}
