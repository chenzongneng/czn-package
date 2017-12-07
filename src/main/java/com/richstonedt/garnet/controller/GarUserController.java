/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.GarUserService;
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
 * <b><code>GarUserController</code></b>
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
@Api(tags = "[Garnet]用户管理接口")
public class GarUserController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarUserController.class);

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

    /**
     * Search users response entity.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询用户列表", notes = "Get user list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMUser.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchUsers(@ApiParam(value = "token", required = true) @RequestParam(value = "token") String token,
                                         @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                         @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                         @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarVMUser> list = userService.queryUserList(token, searchName, page, limit);
            int totalCount = userService.queryTotal();
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user list .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Save user response entity.
     *
     * @param garVMUser the gar vm user
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增用户", notes = "Create user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveUser(@RequestBody GarVMUser garVMUser) {
        try {
            userService.saveUser(garVMUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create user :" + garVMUser, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search user response entity.
     *
     * @param userId the user id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询用户信息", notes = "Get user info by userId ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMUser.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchUser(@ApiParam(value = "userId", required = true) @PathVariable(value = "userId") Long userId) {
        try {
            return new ResponseEntity<>(userService.searchUser(userId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user info :" + userId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete users response entity.
     *
     * @param userIds the user ids
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/user", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除用户", notes = "Delete users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> deleteUsers(@ApiParam(value = "userIds,用‘,’隔开", required = true) @RequestParam(value = "userIds") String userIds) {
        try {
            userService.deleteBatch(GarnetRsUtil.parseStringToList(userIds));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete users :" + userIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update user response entity.
     *
     * @param garVMUser the gar vm user
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新用户信息", notes = "Update user info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> updateUser(@RequestBody GarVMUser garVMUser) {
        try {
            userService.updateUser(garVMUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update user info  .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
