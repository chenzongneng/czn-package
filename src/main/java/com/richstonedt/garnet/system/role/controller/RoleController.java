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
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/roleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRoleLists(
            // todo: get roleId in user
            @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit, @RequestParam(value = "roleId") int roleId) {
        try {
            List<SysRole> results = roleService.getRoleLists(page, limit, roleId);
            int totalCount = results.size();
            PageUtils pageUtils = new PageUtils(results, totalCount, limit, page);
            return new ResponseEntity<>(pageUtils, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get roles in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Search role response entity.
     *
     * @param roleId   the role id
     * @param roleName the role name
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> searchRole(
            // todo: get roleId in user
            @RequestParam(value = "roleId") int roleId, @RequestParam(value = "roleName") String roleName) {
        try {
            List<SysRole> results = roleService.searchRole(roleId, roleName);
            return new ResponseEntity<Object>(results, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to search roles in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Sava role response entity.
     *
     * @param role     the role  接收一个role对象
     * @param roleId   the roleId  该角色的id
     * @param roleType the role type  0:管理员   1:租户管理员  2:其他角色
     * @param tenant   the tenant    租户id，只有当type = 2 时，才有值
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveRole(
            @RequestBody SysRole role,@RequestParam(value = "roleId") Integer roleId, @RequestParam(value = "roleType", required = false) Integer roleType,
            @RequestParam(value = "tenant", required = false) Integer tenant) {
        try{
            if(role == null || roleId == null ){
                return new ResponseEntity<Object>("Role or roleId can't be null!!!",HttpStatus.BAD_REQUEST);
            }
            roleService.saveRole(role,roleId,roleType,tenant);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }catch (Throwable t){
            LOG.error("Failed to save role in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

}
