/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarPermissionForImport;
import com.richstonedt.garnet.model.view.model.GarVMRole;
import com.richstonedt.garnet.model.view.model.GarVmPermission;
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
 * <b>Create Time:</b>2017/12/7 14:49
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]访问权限管理接口")
public class GarPermissionController {

    private static Logger LOG = LoggerFactory.getLogger(GarAuthorityController.class);

    @Autowired
    private GarPermissionService permissionService;


    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询访问权限列表", notes = "Get permission list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMRole.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("permission:list")
    public ResponseEntity<?> searchPermissions(
            @ApiParam(value = "token", required = true) @RequestParam(value = "token") String token,
            @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
            @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
            @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName,
            @ApiParam(value = "应用ID") @RequestParam(value = "applicationId", required = false) Long applicationId) {
        try {
            List<GarVmPermission> list = permissionService.queryPermissionList(searchName,applicationId, page, limit);
            int totalCount = permissionService.queryTotalPermission(searchName,applicationId);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/permissions/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]通过应用ID查询访问权限列表", notes = "Get permission list by application id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMRole.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("application:permission:list")
    public ResponseEntity<?> searchPermissions(
            @ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        try {
            List<GarVmPermission> list = permissionService.queryPermissionListByApplicationId(applicationId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }


    @RequestMapping(value = "/importPermissions/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]获取可导入的访问权限列表数据", notes = "get permissions which can import to database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMRole.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getImportPermissions(
            @ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        try {
            List<GarPermissionForImport> permissionList = permissionService.getImportPermissionFromAnnotation(this.getClass(), applicationId);
            return new ResponseEntity<>(permissionList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/importPermissions/{applicationId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]导入访问权限列表", notes = "import permissions ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMRole.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> importPermissions(
            @RequestBody List<GarPermissionForImport> permissionList,
            @ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        try {
            permissionService.importPermissionFromAnnotation(permissionList, applicationId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
