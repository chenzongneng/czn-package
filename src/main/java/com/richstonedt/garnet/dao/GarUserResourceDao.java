package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarUserResourceDao</code></b>
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
public interface GarUserResourceDao {

    /**
     * Gets code by user id.
     *
     * @param userId the user id
     * @param appId  the app id
     * @return the code by user id
     * @since garnet-core-be-fe 0.1.0
     */
    List<String> getCodeByUserId(@Param("userId") Long userId, @Param("applicationId") Long appId);

    /**
     * Gets resource code by user id and app code.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the resource code by user id and app code
     * @since garnet-core-be-fe 0.1.0
     */
    Set<String> getResourceCodeByUserIdAndAppCode(@Param("userId") Long userId, @Param("appCode") String appCode);

}
