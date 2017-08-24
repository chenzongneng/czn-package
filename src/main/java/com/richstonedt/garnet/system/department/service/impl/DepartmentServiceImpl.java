/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.service.impl;

import com.richstonedt.garnet.common.exception.GarnetServiceErrorCodes;
import com.richstonedt.garnet.common.exception.GarnetServiceException;
import com.richstonedt.garnet.system.department.dao.DepartmentDao;
import com.richstonedt.garnet.system.department.entity.Department;
import com.richstonedt.garnet.system.department.service.DepartmentService;
import com.richstonedt.garnet.system.tenant.dao.TenantDao;
import com.richstonedt.garnet.system.tenant.entity.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
     * The constant LOG.
     *
     * @since Garnet 1.0.0
     */
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    /**
     * The Department dao.
     *
     * @since Garnet 1.0.0
     */
    private final DepartmentDao departmentDao;

    /**
     * The Tenant dao.
     *
     * @since Garnet 1.0.0
     */
    private final TenantDao tenantDao;

    @Autowired
    public DepartmentServiceImpl(DepartmentDao departmentDao, TenantDao tenantDao) {
        this.departmentDao = departmentDao;
        this.tenantDao = tenantDao;
    }

    /**
     * Gets department list.
     *
     * @param tenantId    the tenant id
     * @param containThis the contain this
     * @return the department list
     * @since Garnet 1.0.0
     */
    @Override
    public List<Department> getDepartmentList(Long tenantId, boolean containThis) {
        List<Department> departments = departmentDao.getDepartmentList(tenantId, containThis);
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                if (!ObjectUtils.isEmpty(department.getParentId())) {
                    Department parent = departmentDao.getDepartmentById(department.getParentId());
                    if (parent != null) {
                        department.setParentName(parent.getName());
                    }
                }
            }
        }
        return departments;
    }

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     * @since Garnet 1.0.0
     */
    @Override
    public Department getDepartmentById(Long id) {
        Department department = departmentDao.getDepartmentById(id);
        if (!ObjectUtils.isEmpty(department.getParentId())) {
            Department parent = departmentDao.getDepartmentById(department.getParentId());
            if (parent != null) {
                department.setParentName(parent.getName());
            }
        }
        return department;
    }

    /**
     * Delete department by id int.
     *
     * @param id the id
     * @since Garnet 1.0.0
     */
    @Override
    public void deleteDepartmentById(Long id) {
        //TODO: delete all the children about this department.
        //TODO: if it's the root department ,delete the tenant.
        int row = departmentDao.deleteDepartmentById(id);
        if (row == 0) {
            String error = "Failed to set delete_flag of department to true![id:" + id + "]";
            LOG.error(error);
            throw new GarnetServiceException(error, GarnetServiceErrorCodes.OBJECT_NOT_FOUND);
        }
    }

    /**
     * Update department.
     *
     * @param department the department
     * @since Garnet 1.0.0
     */
    @Override
    public void updateDepartment(Department department) {
        Long tenantId = null;
        Department origin = departmentDao.getDepartmentById(department.getId());
        if (department.getParentId() != null && origin.getParentId() != null) {
            Department parent = departmentDao.getDepartmentById(department.getParentId());
            if (department.getParentId().equals(origin.getParentId())) {
                /*
                  parent not changed:
                    1. root     if the name of department changed, change the name of tenant
                    2. others
                 */
                tenantId = origin.getTenantId();
                if (parent.getTenantId() == null) {
                    if (!department.getName().equals(origin.getName())) {
                        Tenant tenant = new Tenant();
                        tenant.setId(origin.getTenantId());
                        tenant.setName(department.getName());
                        int row = tenantDao.updateTenant(tenant);
                        if (row == 0) {
                            String error = "Failed to update tenant[id:" + tenant.getId() + ",name:" + tenant.getName() + "]!";
                            LOG.error(error);
                            throw new GarnetServiceException(error, GarnetServiceErrorCodes.OBJECT_NOT_FOUND);
                        }
                    }
                }
            } else {
                /*
                 parent changed:
                    1. not -> not  []               tenantId = parent.tenantId
                    2. root -> not [remove tenant]  tenantId = parent.tenantId
                    3. not -> root [add new tenant] tenantId = new tenantId
                 */
                Department originParent = departmentDao.getDepartmentById(origin.getParentId());
                if (originParent.getTenantId() != null && parent.getTenantId() != null) {
                    tenantId = parent.getTenantId();
                } else if (originParent.getTenantId() == null && parent.getTenantId() != null) {
                    tenantDao.deleteById(origin.getTenantId());
                    tenantId = parent.getTenantId();
                } else if (originParent.getTenantId() != null && parent.getTenantId() == null) {
                    Tenant tenant = new Tenant();
                    tenant.setName(department.getName());
                    tenantDao.addTenant(tenant);
                    tenantId = tenant.getId();
                }
            }
        }

        department.setTenantId(tenantId);
        int row = departmentDao.updateDepartment(department);
        if (row == 0) {
            String error = "Failed to set delete_flag of department to true![id:" + department.getId() + "]";
            LOG.error(error);
            throw new GarnetServiceException(error, GarnetServiceErrorCodes.OBJECT_NOT_FOUND);
        }
    }

    /**
     * Add department long.
     *
     * @param department the department
     * @return the long
     * @since Garnet 1.0.0
     */
    @Override
    public Long addDepartment(Department department) {
        Long tenantId = null;
        if (department.getParentId() != null) {
            Department parent = departmentDao.getDepartmentById(department.getParentId());
            if (parent.getTenantId() == null) {
                Tenant tenant = new Tenant();
                tenant.setName(department.getName());
                tenantDao.addTenant(tenant);
                tenantId = tenant.getId();
            } else {
                tenantId = parent.getTenantId();
            }
        }
        department.setTenantId(tenantId);
        departmentDao.addDepartment(department);
        return department.getId();
    }

    /**
     * Gets parent dept.
     *
     * @return the parent dept
     * @since Garnet 1.0.0
     */
    @Override
    public List<Department> getParentDept() {
        return departmentDao.getParentDept();
    }

}