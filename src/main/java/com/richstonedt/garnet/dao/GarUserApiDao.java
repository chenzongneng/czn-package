package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <b><code>GarUserPermissionDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:06
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarUserApiDao {

    Set<String> getApiPermissionByUserIdAndAppId(@Param("userId") Long userId, @Param("appId") Long appId);

    Set<String> getApiPermissionsByUserIdAndAppCode(@Param("userId") Long userId, @Param("appCode") String appCode);
}
