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

    Set<String> getPermissionsByIds(@Param("ids") Set<Long> ids);

    List<GarApi> getApiByPermission(@Param("permission") String api);

    List<GarApi> getApiByApplicationIdAndStatus(@Param("applicationId") Long applicationId, @Param("status") int status);

    List<GarApi> getApiByName(@Param("name") String name);

    List<GarApi> getApisByParams(Map<String, Object> params);

    int queryTotalApi(Map<String, Object> params);
}
