/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.service;

import com.richstonedt.garnet.system.department.entity.Department;

import java.util.List;

/**
 * <b><code>DepartmentService</code></b>
 * <p/>
 * DepartmentService
 * <p/>
 * <b>Creation Time:</b> 2017/8/21 10:22.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
public interface DepartmentService {

    /**
     * Gets department list.
     *
     * @param tenantId    the tenant id
     * @param containThis the contain this
     * @return the department list
     * @since Garnet 1.0.0
     */
    List<Department> getDepartmentList(Long tenantId, boolean containThis);

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     * @since Garnet 1.0.0
     */
    Department getDepartmentById(Long id);

    /**
     * Delete department by id int.
     *
     * @param id the id
     * @since Garnet 1.0.0
     */
    void deleteDepartmentById(Long id);

    /**
     * Update department.
     *
     * @param department the department
     * @since Garnet 1.0.0
     */
    void updateDepartment(Department department);

    /**
     * Add department long.
     *
     * @param department the department
     * @return the long
     * @since Garnet 1.0.0
     */
    Long addDepartment(Department department);

    /**
     * Gets parent dept.
     *
     * @return the parent dept
     * @since Garnet 1.0.0
     */
    List<Department> getParentDept();
}
