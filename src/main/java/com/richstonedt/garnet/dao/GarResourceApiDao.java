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

    void save(@Param("resourceId") Long resourceId, @Param("apiId") Long apiId);

    void deleteByResourceId(@Param("resourceId") Long resourceId);

    void deleteByResourceIds(@Param("resourceIds") List<Long> resourceIds);

    List<Long> getApiIdsByResourceId(@Param("resourceId") Long resourceId);

    void deleteByApiIds(@Param("apiIds") List<Long> apiIds);

//    List<GarApi> getApiByResourceId(@Param("resourceId") Long resourceId);

}
