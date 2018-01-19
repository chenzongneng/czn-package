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

    String getResourceNameByCode(@Param("code") String code);

    Long getResourceIdByCode(@Param("code") String code);

    List<GarResource> getResourcesByAppId(@Param("appId") Long appId);

    List<GarResource> getResourcesByParams(Map<String, Object> params);

    int getTotalResourceByParam(Map<String, Object> params);

    Set<String> queryResourceNameByParentIds(@Param("parentIds")List<Long> resourceIds);
}
