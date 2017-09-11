/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.user.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.system.authority.controller.AuthorityController;
import com.richstonedt.garnet.system.user.entity.User;
import com.richstonedt.garnet.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <b><code>UserController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:20
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/v1.0")
public class UserController {

    /**
     * The Sys user service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private UserService userService;


    /**
     * 所有用户列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserList(
            @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit,
            @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<User> results = userService.getUserList(page, limit, searchName);
            int totalCount = userService.getUserCount();
            PageUtils pageUtils = new PageUtils(results, totalCount, limit, page);
            return new ResponseEntity<>(pageUtils, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * 修改登录用户密码
     *
     * @since garnet-core-be-fe 1.0.0
     */
    //@SysLog("修改密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> password(
            @RequestParam(value = "userId") Integer userId, @RequestParam(value = "password") String password, @RequestParam(value = "newPassword") String newPassword) {
        try {
            userService.changePassword(userId, password, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * 用户信息
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * 保存用户
     *
     * @since garnet-core-be-fe 1.0.0
     */
    //@SysLog("保存用户")
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * 修改用户
     *
     * @since garnet-core-be-fe 1.0.0
     */
    //@SysLog("修改用户")
    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> update(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * 删除用户
     *
     * @since garnet-core-be-fe 1.0.0
     */
    //@SysLog("删除用户")
    @RequestMapping(value = "/user", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUsers(@RequestParam(value = "userIds") String userIds) {
        try {
            List<Integer> userIdList = new AuthorityController().getRoleList(userIds);
            userService.deleteUsers(userIdList);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }
}
