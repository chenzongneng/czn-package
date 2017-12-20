/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarVMAuthority;
import com.richstonedt.garnet.service.GarAuthorityService;
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
 * <b><code>GarAuthorityController</code></b>
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
public class GarAuthorityController {

    private static Logger LOG = LoggerFactory.getLogger(GarAuthorityController.class);

    @Autowired
    private GarAuthorityService authorityService;

    @RequestMapping(value = "/authorities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询权限列表", notes = "Get authority list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMAuthority.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"authority:list"})
    public ResponseEntity<?> searchAuthorities(@ApiParam(value = "token", required = true) @RequestParam(value = "token") String token,
                                         @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                         @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                         @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarVMAuthority> list = authorityService.queryAuthorityList(searchName, page, limit);
            int totalCount = authorityService.queryTotal();
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user authority .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/authority/{authorityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询权限信息", notes = "Get authority info by authorityId ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMAuthority.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"authority:info"})
    public ResponseEntity<?> searchAuthority(@ApiParam(value = "authorityId", required = true) @PathVariable(value = "authorityId") Long authorityId) {
        try {
            return new ResponseEntity<>(authorityService.searchAuthority(authorityId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get authority info :" + authorityId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
    
    @RequestMapping(value = "/authority", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增权限", notes = "Create authority")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"authority:create"})
    public ResponseEntity<?> saveAuthority(@RequestBody GarVMAuthority garVMAuthority) {
        try {
            authorityService.saveAuthority(garVMAuthority);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create authority :" + garVMAuthority, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/authority", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新权限信息", notes = "Update authority info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"authority:update"})
    public ResponseEntity<?> updateAuthority(@RequestBody GarVMAuthority garVMAuthority) {
        try {
            authorityService.updateAuthority(garVMAuthority);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update authority info.", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
    
    @RequestMapping(value = "/authority", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除权限", notes = "Delete authoritys")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"authority:delete:batch"})
    public ResponseEntity<?> deleteAuthoritys(@ApiParam(value = "authorityIds,用‘,’隔开", required = true) @RequestParam(value = "authorityIds") String authorityIds) {
        try {
            authorityService.deleteBatch(GarnetRsUtil.parseStringToList(authorityIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete authoritys :" + authorityIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/authorities/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询通过应用ID权限列表", notes = "Get authority list by application id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMAuthority.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"application:authority:list"})
    public ResponseEntity<?> searchAuthoritiesByApplicationId(
            @ApiParam(value = "authorityId", required = true) @PathVariable(value = "applicationId") Long applicationId) {
        try {
            List<GarVMAuthority> authorityList = authorityService.queryAuthorityListByApplicationId(applicationId);
            return new ResponseEntity<>(authorityList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user authority .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
