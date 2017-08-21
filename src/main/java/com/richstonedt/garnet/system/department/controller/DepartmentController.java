/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.controller;

import com.richstonedt.garnet.common.utils.Constant;
import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.system.department.entity.Department;
import com.richstonedt.garnet.system.department.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     */
    private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    /**
     * The Department service.
     */
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Gets department list.
     *
     * @param userId the user id
     * @return the department list
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getDepartmentList(
            @RequestParam(value = "userId") long userId
    ) {
        try {
            Long departmentId = null;
            if (userId != Constant.SUPER_ADMIN) {
                // TODO: get department id of user
                departmentId = 1L;
            }
            List<Department> departments = departmentService.getDepartmentList(departmentId);
            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get department list!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

}
