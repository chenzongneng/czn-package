/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.service.GarApplicationService;
import com.richstonedt.garnet.utils.GarnetRsUtils;
import com.richstonedt.garnet.utils.PageUtils;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveApplication(@RequestBody GarApplication application) {
        try {
            applicationService.save(application);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create application :" + application);
            LOG.error(t.getMessage());
            return GarnetRsUtils.newResponseEntity(t);
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
    public ResponseEntity<?> deleteApplications(@ApiParam(value = "appIds,用‘,’隔开", required = true) @RequestParam(value = "appIds") String appIds) {
        try {
            applicationService.deleteBatch(GarnetRsUtils.parseStringToList(appIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete applications :" + appIds);
            LOG.error(t.getMessage());
            return GarnetRsUtils.newResponseEntity(t);
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
            @ApiResponse(code = 200, message = "successful query", response = GarApplication.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchApplication(@ApiParam(value = "appId", required = true) @PathVariable(value = "appId") Integer appId) {
        try {
            return new ResponseEntity<>(applicationService.queryObject(appId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get application :" + appId);
            LOG.error(t.getMessage());
            return GarnetRsUtils.newResponseEntity(t);
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
            @ApiResponse(code = 200, message = "successful query", response = GarApplication.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchApplications(@ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                                @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                                @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarApplication> list = applicationService.queryObjects(searchName, page, limit);
            int totalCount = applicationService.queryTotal();
            PageUtils result = new PageUtils(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get applications .");
            LOG.error(t.getMessage());
            return GarnetRsUtils.newResponseEntity(t);
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
    public ResponseEntity<?> updateApplication(@ApiParam(value = "application对象") @RequestBody GarApplication application) {
        try {
            applicationService.update(application);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update applications .");
            LOG.error(t.getMessage());
            return GarnetRsUtils.newResponseEntity(t);
        }
    }
}
