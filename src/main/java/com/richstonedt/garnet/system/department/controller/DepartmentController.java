/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.system.department.entity.Department;
import com.richstonedt.garnet.system.department.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b><code>DepartmentController</code></b>
 * <p/>
 * DepartmentController
 * <p/>
 * <b>Creation Time:</b> 2017/8/21 10:18.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
public class DepartmentController {

    /**
     * The constant LOG.
     *
     * @since Garnet 1.0.0
     */
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    /**
     * The Department service.
     *
     * @since Garnet 1.0.0
     */
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Gets department list.
     *
     * @param userId      the user id
     * @param containThis the contain this
     * @return the department list
     * @since Garnet 1.0.0
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getDepartmentList(
            @RequestParam(value = "userId") long userId,
            @RequestParam(value = "containThis", defaultValue = "false") boolean containThis
    ) {
        try {
            Long tenantId = null;
            // TODO: get tenant id of user
//            tenantId = 1L;

            List<Department> departments = departmentService.getDepartmentList(tenantId, containThis);
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get department list!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     * @since Garnet 1.0.0
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getDepartmentById(
            @PathVariable(value = "id") Long id) {
        try {
            Department department = departmentService.getDepartmentById(id);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get department with id[" + id + "]!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Delete department by id response entity.
     *
     * @param id the id
     * @return the response entity
     * @since Garnet 1.0.0
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteDepartmentById(
            @PathVariable(value = "id") Long id) {
        try {
            departmentService.deleteDepartmentById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete department with id[" + id + "]!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Add department response entity.
     *
     * @param department the department
     * @return the response entity
     * @since Garnet 1.0.0
     */
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public ResponseEntity<?> addDepartment(
            @RequestBody Department department) {
        try {
            departmentService.addDepartment(department);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Throwable t) {
            LOG.error("Failed to add department, department[" + department.toString() + "]");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Update department response entity.
     *
     * @param id         the id
     * @param department the department
     * @return the response entity
     * @since Garnet 1.0.0
     */
    @RequestMapping(value = "/department/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDepartment(
            @PathVariable(value = "id") Long id,
            @RequestBody Department department) {
        try {
            if (department.getId() == null) {
                String error = "Illegal argument: id in department is null!";
                LOG.error(error);
                throw new IllegalArgumentException(error);
            }
            if (!department.getId().equals(id)) {
                String error = "Illegal argument: department is not match with parameter id!";
                LOG.error(error);
                throw new IllegalArgumentException(error);
            }
            departmentService.updateDepartment(department);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update department, department[" + department.toString() + "]");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * get Parent Department List
     *
     * @return the department list
     * @since Garnet 1.0.0
     */
    @RequestMapping(value = "/parentDepartments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getParentDepartmentList() {
        try {
            List<Department> departments = departmentService.getParentDept();
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get department list!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

}
