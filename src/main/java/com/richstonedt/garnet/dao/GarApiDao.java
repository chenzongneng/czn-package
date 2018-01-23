/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <b><code>GarApiDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/4 18:58
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarApiDao extends BaseDao<GarApi> {

    /**
     * Query permissions by ids set.
     *
     * @param ids the ids
     * @return the set
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> queryPermissionsByIds(@Param("ids") Set<Long> ids);

    /**
     * Query api by permission list.
     *
     * @param api the api
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApi> queryApiByPermission(@Param("permission") String api);

    /**
     * Query api by application id and status list.
     *
     * @param applicationId the application id
     * @param status        the status
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApi> queryApiByApplicationIdAndStatus(@Param("applicationId") Long applicationId, @Param("status") int status);

    /**
     * Query api by name list.
     *
     * @param name the name
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApi> queryApiByName(@Param("name") String name);

    /**
     * Query apis by params list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarApi> queryApisByParams(Map<String, Object> params);

    /**
     * Query total api int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    int queryTotalApi(Map<String, Object> params);

    /**
     * Query api name by parent ids set.
     *
     * @param apiIds the api ids
     * @return the set
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> queryApiNameByParentIds(@Param("apiIds")List<Long> apiIds);
}
