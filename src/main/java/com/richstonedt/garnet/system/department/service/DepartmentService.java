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
     * @param id the id
     * @return the department list
     * @since Garnet 1.0.0
     */
    List<Department> getDepartmentList(Long id);

}
