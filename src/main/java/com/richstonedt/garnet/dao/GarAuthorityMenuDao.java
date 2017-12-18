package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarAuthorityMenuDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>17:25
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarAuthorityMenuDao {

    void save(@Param("authorityId")Long authorityId,@Param("menuId")Long menuId);

    void deleteByAuthorityId(@Param("authorityId") Long authorityId);

    List<Long> getMenuIdByAuthorityId(@Param("authorityId")Long authorityId);
}
