/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarUserRoleDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:23
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarUserRoleDao {

    void save(@Param("userId")Long userId, @Param("roleId")Long roleId);

    void deleteByUserId(@Param("userId") Long userId);

    void deleteByRoleId(@Param("roleId") Long roleId);

    List<Long> getRoleIdByUserId(@Param("userId")Long userId);

    List<Long> getUserIdByRoleId(@Param("roleId")Long roleId);

}
