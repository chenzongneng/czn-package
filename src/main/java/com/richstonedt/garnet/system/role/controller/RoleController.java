/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.system.role.entity.SysRole;
import com.richstonedt.garnet.system.role.service.RoleService;
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
 * <b><code>RoleController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:13
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
public class RoleController {

    /**
     * The Role service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private RoleService roleService;

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static Logger LOG = LoggerFactory.getLogger(RoleController.class);

    /**
     * Search roles response entity.
     *
     * @param page   the page
     * @param limit  the limit
     * @param roleId the role id
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> searchRoles (
            // todo: get roleid in user
            @RequestParam(value = "page") int page,@RequestParam(value = "limit") int limit ,@RequestParam(value = "roleId") int roleId
    ){
        try{
            List<SysRole> results = roleService.getRoleLists(page,limit,roleId);
            int totalCount =results.size();
            PageUtils pageUtils = new PageUtils(results,totalCount,limit,page);
            return new ResponseEntity<>(pageUtils,HttpStatus.OK);
        }catch (Throwable t){
            LOG.error("Failed to get roles in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }
}
