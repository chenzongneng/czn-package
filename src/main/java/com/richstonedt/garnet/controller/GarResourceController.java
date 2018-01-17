/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarVMResource;
import com.richstonedt.garnet.service.GarResourceService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarResourceController</code></b>
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
@Api(tags = "[Garnet]菜单管理接口")
public class GarResourceController {

    private static Logger LOG = LoggerFactory.getLogger(GarResourceController.class);

    @Autowired
    private GarResourceService resourceService;

    @RequestMapping(value = "/resources", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询菜单列表", notes = "Get resource list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMResource.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"resource:list"})
    public ResponseEntity<?> searchResources(
            @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
            @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
            @ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "应用ID") @RequestParam(value = "applicationId", required = false) Long applicationId) {
        try {
            Integer offset = (page - 1) * limit;
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            params.put("name", name);
            params.put("applicationId", applicationId);
            List<GarVMResource> list = resourceService.queryResourceListByParams(params);
            int totalCount = resourceService.queryTotalResourceByParam(params);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user resource .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/resources/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]通过应用查询应用菜单列表", notes = "Get application resource list by app id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMResource.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"resource:list:application"})
    public ResponseEntity<?> searchResourcesByAppId(
            @ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        try {
            List<GarVMResource> list = resourceService.queryResourceListByAppId(applicationId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user resource .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/resource/{resourceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询菜单信息", notes = "Get resource info by resourceId ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMResource.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"resource:info"})
    public ResponseEntity<?> searchResource(@ApiParam(value = "resourceId", required = true) @PathVariable(value = "resourceId") Long resourceId) {
        try {
            return new ResponseEntity<>(resourceService.searchResource(resourceId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get resource info :" + resourceId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增菜单", notes = "Create resource")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"resource:create"})
    public ResponseEntity<?> saveResource(@RequestBody GarVMResource garVMResource) {
        try {
            resourceService.saveResource(garVMResource);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create resource :" + garVMResource, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/resource", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据更新菜单信息", notes = "Update resource info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"resource:update"})
    public ResponseEntity<?> updateResource(@RequestBody GarVMResource garVMResource) {
        try {
            resourceService.updateResource(garVMResource);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update resource info.", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/resource", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除菜单", notes = "Delete resources")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"resource:delete:batch"})
    public ResponseEntity<?> deleteResources(@ApiParam(value = "resourceIds,用‘,’隔开", required = true) @RequestParam(value = "resourceIds") String resourceIds) {
        try {
            Map<String, String> result = resourceService.deleteBatchByResourceIds(GarnetRsUtil.parseStringToList(resourceIds));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete resources :" + resourceIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
