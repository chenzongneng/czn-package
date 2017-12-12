package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <b><code>GarMenuPermissionDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>18:09
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarMenuPermissionDao {

    void save(@Param("menuId")Long menuId,@Param("permissionId")Long permissionId);

}
