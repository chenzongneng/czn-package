/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.view.model.GarVMApplication;
import com.richstonedt.garnet.service.GarApplicationService;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
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
 * <b><code>GarApplicationController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:45
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]应用管理接口")
public class GarApplicationController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarApplicationController.class);

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationService applicationService;

    /**
     * Save application response entity.
     *
     * @param application the application
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/application", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增应用", notes = "Create application")
    @RequiresPermissions("application:add")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveApplication(@RequestBody GarVMApplication application) {
        try {
            applicationService.saveApplication(application);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create application :" + application, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete applications response entity.
     *
     * @param appIds the app ids
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/application", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除应用", notes = "Delete applications")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("application:delete:batch")
    public ResponseEntity<?> deleteApplications(@ApiParam(value = "appIds,用‘,’隔开", required = true) @RequestParam(value = "appIds") String appIds) {
        try {
            applicationService.deleteBatch(GarnetRsUtil.parseStringToList(appIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete applications :" + appIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search application response entity.
     *
     * @param appId the id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/application/{appId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询应用", notes = "Get application by id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMApplication.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("application:info")
    public ResponseEntity<?> searchApplication(@ApiParam(value = "appId", required = true) @PathVariable(value = "appId") Long appId) {
        try {
            return new ResponseEntity<>(applicationService.getVmApplicationByAppId(appId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get application :" + appId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search applications response entity.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/applications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询应用列表", notes = "Get application list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMApplication.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("application:list")
    public ResponseEntity<?> searchApplications(@ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                                @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                                @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            Integer offset = (page - 1) * limit;
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            params.put("searchName", searchName);
            List<GarVMApplication> list = applicationService.queryVmApplications(params);
            int totalCount = applicationService.queryTotal(params);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get applications .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update applications response entity.
     *
     * @param application the application
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/application", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新应用", notes = "Update application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("application:update")
    public ResponseEntity<?> updateApplication(@ApiParam(value = "application对象") @RequestBody GarVMApplication application) {
        try {
            applicationService.updateVmApplication(application);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update applications .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
