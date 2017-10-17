/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.GarTenant;
import com.richstonedt.garnet.service.GarTenantService;
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
 * <b><code>GarTenantController</code></b>
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
@Api(tags = "[Garnet]租户管理接口")
public class GarTenantController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarTenantController.class);

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantService tenantService;

    /**
     * Save tenant response entity.
     *
     * @param tenant the tenant
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/tenant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增租户", notes = "Create tenant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveTenant(@RequestBody GarTenant tenant) {
        try {
            tenantService.save(tenant);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create tenant :" + tenant);
            return GarnetRsUtils.newResponseEntity(t);
        }
    }

    /**
     * Delete tenants response entity.
     *
     * @param tenantIds the tenant ids
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/tenant", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除租户", notes = "Delete tenants")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> deleteTenants(@ApiParam(value = "tenantIds,用‘,’隔开", required = true) @RequestParam(value = "tenantIds") String tenantIds) {
        try {
            tenantService.deleteBatch(GarnetRsUtils.parseStringToList(tenantIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete tenants :" + tenantIds);
            return GarnetRsUtils.newResponseEntity(t);
        }
    }

    /**
     * Search tenant response entity.
     *
     * @param tenantId the tenant id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/tenant/{tenantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询租户信息", notes = "Get tenant by id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarTenant.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchTenant(@ApiParam(value = "tenantId", required = true) @PathVariable(value = "tenantId") Integer tenantId) {
        try {
            return new ResponseEntity<>(tenantService.queryObject(tenantId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get tenant :" + tenantId);
            return GarnetRsUtils.newResponseEntity(t);
        }
    }

    /**
     * Search tenants response entity.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/tenants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询租户列表", notes = "Get tenant list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarTenant.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchTenants(@ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                           @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                           @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarTenant> list = tenantService.queryObjects(searchName, page, limit);
            int totalCount = tenantService.queryTotal();
            PageUtils result = new PageUtils(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get tenants.");
            return GarnetRsUtils.newResponseEntity(t);
        }
    }

    /**
     * Update tenant response entity.
     *
     * @param tenant the tenant
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/tenant", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新租户", notes = "Update tenant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> updateTenant(@ApiParam(value = "tenant对象") @RequestBody GarTenant tenant) {
        try {
            tenantService.update(tenant);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update tenant .");
            return GarnetRsUtils.newResponseEntity(t);
        }
    }
}
