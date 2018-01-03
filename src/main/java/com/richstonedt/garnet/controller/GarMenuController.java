/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarVMMenu;
import com.richstonedt.garnet.service.GarMenuService;
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
 * <b><code>GarMenuController</code></b>
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
public class GarMenuController {

    private static Logger LOG = LoggerFactory.getLogger(GarMenuController.class);

    @Autowired
    private GarMenuService menuService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询菜单列表", notes = "Get menu list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMMenu.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"menu:list"})
    public ResponseEntity<?> searchMenus(
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
            List<GarVMMenu> list = menuService.queryMenuListByParams(params);
            int totalCount = menuService.queryTotalMenuByParam(params);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user menu .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/menus/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]通过应用查询应用菜单列表", notes = "Get application menu list by app id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMMenu.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"application:menu:list"})
    public ResponseEntity<?> searchMenusByAppId(
            @ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        try {
            List<GarVMMenu> list = menuService.queryMenuListByAppId(applicationId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user menu .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/menu/{menuId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询菜单信息", notes = "Get menu info by menuId ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMMenu.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"menu:info"})
    public ResponseEntity<?> searchMenu(@ApiParam(value = "menuId", required = true) @PathVariable(value = "menuId") Long menuId) {
        try {
            return new ResponseEntity<>(menuService.searchMenu(menuId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get menu info :" + menuId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增菜单", notes = "Create menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"menu:create"})
    public ResponseEntity<?> saveMenu(@RequestBody GarVMMenu garVMMenu) {
        try {
            menuService.saveMenu(garVMMenu);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create menu :" + garVMMenu, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/menu", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据更新菜单信息", notes = "Update menu info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"menu:update"})
    public ResponseEntity<?> updateMenu(@RequestBody GarVMMenu garVMMenu) {
        try {
            menuService.updateMenu(garVMMenu);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update menu info.", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/menu", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除菜单", notes = "Delete menus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"menu:delete:batch"})
    public ResponseEntity<?> deleteMenus(@ApiParam(value = "menuIds,用‘,’隔开", required = true) @RequestParam(value = "menuIds") String menuIds) {
        try {
            menuService.deleteBatch(GarnetRsUtil.parseStringToList(menuIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete menus :" + menuIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
