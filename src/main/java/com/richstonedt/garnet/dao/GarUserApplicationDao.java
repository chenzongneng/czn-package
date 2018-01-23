package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarUserApplicationDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:08
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarUserApplicationDao {

    /**
     * Save.
     *
     * @param userId        the user id
     * @param applicationId the application id
     * @since garnet-core-be-fe 0.1.0
     */
    void save(@Param("userId")Long userId, @Param("applicationId")Long applicationId);

    /**
     * Delete by user id.
     *
     * @param userId the user id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * Gets application id by user id.
     *
     * @param userId the user id
     * @return the application id by user id
     * @since garnet-core-be-fe 0.1.0
     */
    List<Long> getApplicationIdByUserId(@Param("userId")Long userId);

}
