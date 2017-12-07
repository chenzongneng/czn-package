/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.view.model.GarVMRole;
import com.richstonedt.garnet.service.GarRoleService;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b><code>GarRoleController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/30 14:58
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]角色管理接口")
public class GarRoleController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarRoleController.class);

    /**
     * The Role service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleService roleService;

    /**
     * Search users response entity.
     *
     * @param token      the token
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询角色列表", notes = "Get role list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMRole.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchUsers(@ApiParam(value = "token", required = true) @RequestParam(value = "token") String token,
                                         @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                         @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                         @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarVMRole> list = roleService.queryRoleList(token, searchName, page, limit);
            int totalCount = roleService.queryTotal();
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search role response entity.
     *
     * @param roleId the role id
     * @return the response entity
     */
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询角色信息", notes = "Get role info by roleId ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMRole.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchRole(@ApiParam(value = "roleId", required = true) @PathVariable(value = "roleId") Long roleId) {
        try {
            return new ResponseEntity<>(roleService.searchRole(roleId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get role info :" + roleId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Save role response entity.
     *
     * @param garVMRole the gar vm role
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增角色", notes = "Create role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveRole(@RequestBody GarVMRole garVMRole) {
        try {
            roleService.saveRole(garVMRole);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create role :" + garVMRole, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete roles response entity.
     *
     * @param roleIds the role ids
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除角色", notes = "Delete roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> deleteRoles(@ApiParam(value = "roleIds,用‘,’隔开", required = true) @RequestParam(value = "roleIds") String roleIds) {
        try {
            roleService.deleteBatch(GarnetRsUtil.parseStringToList(roleIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete roles :" + roleIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update role response entity.
     *
     * @param garVMRole the gar vm role
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/role", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新角色信息", notes = "Update role info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> updateRole(@RequestBody GarVMRole garVMRole) {
        try {
            roleService.updateRole(garVMRole);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update role info.", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
