/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.system.role.entity.Role;
import com.richstonedt.garnet.system.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * Gets role by id.
     *
     * @param roleId the role id
     * @return the role by id
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRoleById(
            @PathVariable(value = "roleId")Integer roleId){
        try {
            Role role = roleService.getRoleById(roleId);
            return new ResponseEntity<Object>(role,HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get role in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Search roles response entity.
     *
     * @param page   the page
     * @param limit  the limit
     * @param tenantId the tenant id 租户id
     * @param roleName the role name 如果不为空，则为前端查询接口
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/roleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRoleLists(
            @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit,
            @RequestParam(value = "tenantId")Integer tenantId , @RequestParam(value = "roleName",required = false) String roleName) {
        try {
            List<Role> results = roleService.getRoleLists(page, limit, tenantId,roleName);
            int totalCount = results.size();
            PageUtils pageUtils = new PageUtils(results, totalCount, limit, page);
            return new ResponseEntity<>(pageUtils, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get roles in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Save role response entity.
     *
     * @param role     the role
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveRole(
            @RequestBody Role role) {
        try {
            if (role == null) {
                return new ResponseEntity<Object>("Role or tenantId can't be null!!!", HttpStatus.BAD_REQUEST);
            }
            roleService.saveRole(role);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to save role in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Update role response entity.
     *
     * @param role   the role
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity updateRole(
            @RequestBody Role role) {
        try {
            if (role == null) {
                return new ResponseEntity<Object>("Role can't be null!!!", HttpStatus.BAD_REQUEST);
            }
            roleService.updateRole(role);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update role in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Delete role response entity.
     *
     * @param idList the id  List 删除多条记录，用‘，’隔开
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRole(
            @RequestParam(value = "idList") String idList) {
        try {
            List<Integer> idLists = new ArrayList<>();
            if(idList.contains(",")){
                String[] idArray = idList.split(",");
                for(String id:idArray){
                    idLists.add(Integer.parseInt(id));
                }
            }else{
                idLists.add(Integer.parseInt(idList));
            }
            roleService.deleteRole(idLists);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete role in RoleController ! !!");
            return GarnetUtils.newResponseEntity(t);
        }
    }

}
