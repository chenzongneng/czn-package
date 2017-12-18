package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    void deleteByMenuId(@Param("menuId") Long menuId);

    List<Long> getPermissionIdByMenuId(@Param("menuId") Long menuId);

    List<GarPermission> getPermissionByMenuId(@Param("menuId") Long menuId);

}
