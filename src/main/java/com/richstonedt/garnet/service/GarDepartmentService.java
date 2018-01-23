package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarDepartment;
import com.richstonedt.garnet.model.view.model.GarVMDepartment;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarDepartmentService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:51
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarDepartmentService extends BaseService<GarDepartment> {

    /**
     * Query detp id list list.
     *
     * @param parentDepartmentId the parent department id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<Long> queryDepartmentIdList(Long parentDepartmentId);

    /**
     * Gets user department list.
     *
     * @param userId the user id
     * @return the user department list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMDepartment> getUserDepartmentList(Long userId);

    /**
     * Gets vm department by department id.
     *
     * @param departmentId the department id
     * @return the vm department by department id
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMDepartment getVMDepartmentByDepartmentId(Long departmentId);

    /**
     * Save vm department.
     *
     * @param vmDepartment the vm department
     * @since garnet-core-be-fe 0.1.0
     */
    void saveVMDepartment(GarVMDepartment vmDepartment);

    /**
     * Update vm department.
     *
     * @param vmDepartment the vm department
     * @since garnet-core-be-fe 0.1.0
     */
    void updateVMDepartment(GarVMDepartment vmDepartment);

    /**
     * Delete vm department.
     *
     * @param departmentId the department id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteVMDepartment(Long departmentId);

    /**
     * Query department list by params list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMDepartment> queryDepartmentListByParams(Map<String, Object> params);

    /**
     * Query total menu by param int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    int queryTotalMenuByParam(Map<String, Object> params);

    /**
     * Delete batch by department ids map.
     *
     * @param departmentIds the department ids
     * @return the map
     * @since garnet-core-be-fe 0.1.0
     */
    Map<String,String> deleteBatchByDepartmentIds(List<Long> departmentIds);
}


