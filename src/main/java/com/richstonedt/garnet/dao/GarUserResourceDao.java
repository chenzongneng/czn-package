package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<String> getCodeByUserId(@Param("userId") Long userId, @Param("appId") Long appId);

}
