/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarMenuDao</code></b>
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
public interface GarMenuDao extends BaseDao<GarMenu> {

    String getMenuNameByCode(@Param("code") String code);

    List<GarMenu> getMenusByAppId(@Param("appId")Long appId);

    List<GarMenu> queryMenus(@Param(value = "searchName") String searchName, @Param("applicationId")Long applicationId,@Param(value = "limit") Integer limit, @Param(value = "offset") Integer offset);
}
