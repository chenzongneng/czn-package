package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarDepartmentDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:11
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarDepartmentDao extends BaseDao<GarDepartment> {

    /**
     * Query department id list list.
     *
     * @param parentDepartmentId the parent dept id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<Long> queryDepartmentIdList(@Param(value = "parentDepartmentId") Long parentDepartmentId);

    /**
     * Query objects list.
     *
     * @param tenantId the tenant Id
     * @param subList  the sub list
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<GarDepartment> queryDepartmentList(@Param(value = "tenantId") Long tenantId, @Param(value = "subList") String subList);

    /**
     * Gets dept by parent dept id.
     *
     * @param parentDepartmentId the parent dept id
     * @return the dept by parent dept id
     * @since garnet-core-be-fe 0.1.0
     */
    GarDepartment getDepartmentByParentDepartmentId(@Param(value = "parentDepartmentId") Long parentDepartmentId);

    /**
     * Gets total department by param.
     *
     * @param params the params
     * @return the total department by param
     * @since garnet-core-be-fe 0.1.0
     */
    int getTotalDepartmentByParam(Map<String, Object> params);
}
