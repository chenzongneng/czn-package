package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarResourceApiDao</code></b>
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
public interface GarResourceApiDao {

    /**
     * Save.
     *
     * @param resourceId the resource id
     * @param apiId      the api id
     * @since garnet-core-be-fe 0.1.0
     */
    void save(@Param("resourceId") Long resourceId, @Param("apiId") Long apiId);

    /**
     * Delete by resource id.
     *
     * @param resourceId the resource id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteByResourceId(@Param("resourceId") Long resourceId);

    /**
     * Delete by resource ids.
     *
     * @param resourceIds the resource ids
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteByResourceIds(@Param("resourceIds") List<Long> resourceIds);

    /**
     * Gets api ids by resource id.
     *
     * @param resourceId the resource id
     * @return the api ids by resource id
     * @since garnet-core-be-fe 0.1.0
     */
    List<Long> getApiIdsByResourceId(@Param("resourceId") Long resourceId);

    /**
     * Delete by api ids.
     *
     * @param apiIds the api ids
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteByApiIds(@Param("apiIds") List<Long> apiIds);

//    List<GarApi> getApiByResourceId(@Param("resourceId") Long resourceId);

}
