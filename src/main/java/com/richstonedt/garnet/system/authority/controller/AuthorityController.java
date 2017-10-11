/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.system.authority.entity.viewModel.UserRoles;
import com.richstonedt.garnet.system.authority.service.AuthorityService;
import com.richstonedt.garnet.system.user.entity.User;
import com.richstonedt.garnet.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>AuthorityController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/25 17:49
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
public class AuthorityController {

    /**
     * The Authority service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private AuthorityService authorityService;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private UserService userService;

    /**
     * Get user roles response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserRoles(
            @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit,
            @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<UserRoles> results = authorityService.getUserRolesList(page, limit,searchName);
            PageUtils pageUtils = new PageUtils(results, authorityService.getUserCount(), limit, page);
            return new ResponseEntity<>(pageUtils, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Get user List.
     *
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserList() {
        try {
            List<User> results = userService.getAllUsers();
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Save authority response entity.
     *
     * @param userId          the user id
     * @param selectedRoleIds the selected role ids
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveAuthority(
            @RequestParam(value = "userId") Integer userId, @RequestParam(value = "selectedRoleIds") String selectedRoleIds) {
        try {
            List<Integer> roleIds = getRoleList(selectedRoleIds);
            authorityService.saveAuthority(userId, roleIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Save authority response entity.
     *
     * @param userId          the user id
     * @param selectedRoleIds the selected role ids
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userRole", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateAuthority(
            @RequestParam(value = "userId") Integer userId, @RequestParam(value = "selectedRoleIds") String selectedRoleIds) {
        try {
            List<Integer> roleIds = getRoleList(selectedRoleIds);
            authorityService.updateAuthority(userId, roleIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Save authority response entity.
     *
     * @param userIds the user ids
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userRole", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteAuthority(
            @RequestParam(value = "userIds") String userIds) {
        try {
            List<Integer> roleIds = getRoleList(userIds);
            authorityService.deleteAuthority(roleIds);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Get No Role Users
     *
     * @return the Response Entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/distinctUserList",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getNoRoleUsers() {
        try {
            List<User> results = authorityService.getNoRoleUsers();
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Gets role ids by user id.
     *
     * @param userId the user id
     * @return the role ids by user id
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/roleIds/{userId}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRoleIdsByUserId(
            @PathVariable(value = "userId") Integer userId) {
        try {
            List<Integer> roleIds = authorityService.getRoleIdsByUserId(userId);
            return new ResponseEntity<>(roleIds, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Get role list list.
     *
     * @param selectedRoleIds the selected role ids
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    public List<Integer> getRoleList(String selectedRoleIds) {
        List<Integer> roleIds = new ArrayList<>();
        if (selectedRoleIds.contains(",")) {
            String[] tmpIds = selectedRoleIds.split(",");
            for (String id : tmpIds) {
                roleIds.add(Integer.parseInt(id));
            }
        } else {
            roleIds.add(Integer.parseInt(selectedRoleIds));
        }
        return roleIds;
    }
}
