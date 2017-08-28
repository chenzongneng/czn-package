/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.system.authority.entity.viewModel.UserRoles;
import com.richstonedt.garnet.system.authority.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * Get user roles response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userRoles",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserRoles(
            @RequestParam(value = "searchName",required = false) String searchName){
        try{
            List<UserRoles> results = authorityService.getUserRolesList(searchName);
            return new ResponseEntity<>(results, HttpStatus.OK);
        }catch (Throwable t){
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Get user List.
     *
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userList",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUserList(){
        try{
            List<SysUserEntity> results = authorityService.getAllUsers();
            return new ResponseEntity<>(results, HttpStatus.OK);
        }catch (Throwable t){
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
    @RequestMapping(value = "/userRole",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveAuthority(
            @RequestParam(value = "userId") Integer userId,@RequestParam(value = "selectedRoleIds") String selectedRoleIds){
        try {
            List<Integer> roleIds = getRoleList(selectedRoleIds);
            authorityService.saveAuthority(userId, roleIds);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (Throwable t){
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
    @RequestMapping(value = "/userRole",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateAuthority(
            @RequestParam(value = "userId") Integer userId,@RequestParam(value = "selectedRoleIds") String selectedRoleIds){
        try {
            List<Integer> roleIds = getRoleList(selectedRoleIds);
            authorityService.updateAuthority(userId, roleIds);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (Throwable t){
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Save authority response entity.
     *
     * @param userIds          the user ids
     * @return the response entity
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/userRole",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteAuthority(
            @RequestParam(value = "userIds") String userIds){
        try {
            List<Integer> roleIds = getRoleList(userIds);
            authorityService.deleteAuthority(roleIds);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch (Throwable t){
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
    private List<Integer> getRoleList(String selectedRoleIds){
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
