/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.service.GarSysMenuService;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.service.GarUserMenuService;
import com.richstonedt.garnet.service.GarUserResourceService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b><code>GarSysMenuController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 16:12
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]系统菜单查询接口")
public class GarSysMenuController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarSysMenuController.class);

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarSysMenuService sysMenuService;

    @Autowired
    private GarUserMenuService userMenuService;

    @Autowired
    private GarUserResourceService userResourceService;

    /**
     * Search sys menu response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/sysMenu", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询系统菜单", notes = "Search system menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchSysMenu() {
        try {
            return new ResponseEntity<>(sysMenuService.getUserMenuList(), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to search system menu", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search sys menu response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/menu/userId/{userId}/appId/{appId}/appName/{appName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询系统菜单", notes = "Search system menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchSysMenu(
            @ApiParam(value = "userId", required = true) @PathVariable(value = "userId") Long userId,
            @ApiParam(value = "appId", required = true) @PathVariable(value = "appId") Long appId,
            @ApiParam(value = "appName",required = true)@PathVariable(value = "appName")String appName) {
        try {
            return new ResponseEntity<>(userMenuService.getUserMenuList(userId,appId,appName), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to search system menu", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
    /**
     * Search sys menu response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/button/userId/{userId}/appId/{appId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询系统菜单", notes = "Search system menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchButton(
            @ApiParam(value = "userId", required = true) @PathVariable(value = "userId") Long userId,
            @ApiParam(value = "appId", required = true) @PathVariable(value = "appId") Long appId) {
        try {
            return new ResponseEntity<>(userResourceService.getCodeMapListByUserId(userId,appId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to search system menu", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
