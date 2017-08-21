/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.service.impl;

import com.richstonedt.garnet.system.department.dao.DepartmentDao;
import com.richstonedt.garnet.system.department.entity.Department;
import com.richstonedt.garnet.system.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>DepartmentServiceImpl</code></b>
 * <p/>
 * DepartmentServiceImpl
 * <p/>
 * <b>Creation Time:</b> 2017/8/21 10:25.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    /**
     * The Department dao.
     */
    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    /**
     * Gets department list.
     *
     * @param departmentId the department id
     * @return the department list
     */
    @Override
    public List<Department> getDepartmentList(Long departmentId) {
        return departmentDao.getDepartmentList(departmentId);
    }
}