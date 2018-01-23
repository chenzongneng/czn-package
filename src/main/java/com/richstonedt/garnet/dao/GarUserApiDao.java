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

    /**
     * Gets api permission by user id and app id.
     *
     * @param userId the user id
     * @param appId  the app id
     * @return the api permission by user id and app id
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> getApiPermissionByUserIdAndAppId(@Param("userId") Long userId, @Param("applicationId") Long appId);

    /**
     * Gets api permissions by user id and app code.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the api permissions by user id and app code
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> getApiPermissionsByUserIdAndAppCode(@Param("userId") Long userId, @Param("appCode") String appCode);
}
