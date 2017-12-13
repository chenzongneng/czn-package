package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarUserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarUserMenuDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:18
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarUserMenuDao {

    List<GarUserMenu> getUserMenuByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);

    List<GarUserMenu> getUserMenuByUserIdAndAppIdAndParentCode(@Param("userId") Long userId,@Param("appId") Long appId, @Param("parentCode") String parentCode);

    List<String> getButtonCodeByUserId(@Param("userId") Long userId,@Param("appId") Long appId);

}
