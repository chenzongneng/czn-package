/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.dao;

import com.richstonedt.garnet.system.department.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>DepartmentDao</code></b>
 * <p/>
 * DepartmentDao
 * <p/>
 * <b>Creation Time:</b> 2017/8/21 10:26.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
@Mapper
public interface DepartmentDao {

    /**
     * Gets department list.
     *
     * @param tenantId    the tenant id
     * @param containThis the contain this
     * @return the department list
     * @since Garnet 1.0.0
     */
    List<Department> getDepartmentList(@Param("tenantId") Long tenantId, @Param("containThis") boolean containThis);

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     * @since Garnet 1.0.0
     */
    Department getDepartmentById(@Param("id") Long id);

    /**
     * Delete int.
     *
     * @param id the id
     * @return the int
     * @since Garnet 1.0.0
     */
    int deleteDepartmentById(@Param("id") Long id);

    /**
     * Update department int.
     *
     * @param department the department
     * @return the int
     * @since Garnet 1.0.0
     */
    int updateDepartment(Department department);

    /**
     * Add department long.
     *
     * @param department the department
     * @return the long
     * @since Garnet 1.0.0
     */
    int addDepartment(Department department);
}