/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarVMPermission;
import com.richstonedt.garnet.service.GarPermissionService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b><code>GarPermissionController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 18:10
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]权限管理接口")
public class GarPermissionController {

    private static Logger LOG = LoggerFactory.getLogger(GarPermissionController.class);

    @Autowired
    private GarPermissionService permissionService;

    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询权限列表", notes = "Get permission list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMPermission.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    ////@RequiresPermissions({"permission:list"})
    public ResponseEntity<?> searchAuthorities(@ApiParam(value = "token", required = true) @RequestParam(value = "token") String token,
                                               @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                               @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                               @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarVMPermission> list = permissionService.queryPermissionList(searchName, page, limit);
            int totalCount = permissionService.queryTotal();
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user permission .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询权限信息", notes = "Get permission info by permissionId ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMPermission.class),
            @ApiResponse(code = 500, message = "internal server error")})
    //@RequiresPermissions({"permission:info"})
    public ResponseEntity<?> searchPermission(@ApiParam(value = "permissionId", required = true) @PathVariable(value = "permissionId") Long permissionId) {
        try {
            return new ResponseEntity<>(permissionService.searchPermission(permissionId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get permission info :" + permissionId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/permission", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增权限", notes = "Create permission")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    //@RequiresPermissions({"permission:create"})
    public ResponseEntity<?> savePermission(@RequestBody GarVMPermission garVMPermission) {
        try {
            permissionService.savePermission(garVMPermission);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create permission :" + garVMPermission, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/permission", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新权限信息", notes = "Update permission info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    //@RequiresPermissions({"permission:update"})
    public ResponseEntity<?> updatePermission(@RequestBody GarVMPermission garVMPermission) {
        try {
            permissionService.updatePermission(garVMPermission);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update permission info.", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/permission", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除权限", notes = "Delete permissions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    //@RequiresPermissions({"permission:delete:batch"})
    public ResponseEntity<?> deletePermissions(@ApiParam(value = "permissionIds,用‘,’隔开", required = true) @RequestParam(value = "permissionIds") String permissionIds) {
        try {
            permissionService.deleteBatch(GarnetRsUtil.parseStringToList(permissionIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete permissions :" + permissionIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/permissions/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询通过应用ID权限列表", notes = "Get permission list by application id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMPermission.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    //@RequiresPermissions({"application:permission:list"})
    public ResponseEntity<?> searchAuthoritiesByApplicationId(
            @ApiParam(value = "permissionId", required = true) @PathVariable(value = "applicationId") Long applicationId) {
        try {
            List<GarVMPermission> permissionList = permissionService.queryPermissionListByApplicationId(applicationId);
            return new ResponseEntity<>(permissionList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user permission .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
