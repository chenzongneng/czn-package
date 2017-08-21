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
     * @param departmentId the department id
     * @return the department list
     */
    List<Department> getDepartmentList(@Param("departmentId") Long departmentId);


    Department getDepartmentById(Long id);


    int delete(Long id);

}