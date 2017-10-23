/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.GarUserService;
import com.richstonedt.garnet.utils.GarnetRsUtil;
import com.richstonedt.garnet.utils.PageUtil;
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
     * Save user response entity.
     *
     * @param user the user
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增用户", notes = "Create user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveUser(@RequestBody GarUser user) {
        try {
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create user :" + user);
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询用户列表", notes = "Get user list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMUser.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchUsers(@ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
                                         @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
                                         @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarVMUser> list = userService.queryUserList(searchName, page, limit);
            int totalCount = userService.queryTotal();
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user list .");
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
