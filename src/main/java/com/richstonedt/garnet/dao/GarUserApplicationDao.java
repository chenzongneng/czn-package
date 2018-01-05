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

    void save(@Param("userId")Long userId, @Param("applicationId")Long applicationId);

    void deleteByUserId(@Param("userId") Long userId);

    List<Long> getApplicationIdByUserId(@Param("userId")Long userId);

}
