/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarVMDept;
import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.GarDeptService;
import com.richstonedt.garnet.service.GarUserService;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <b><code>GarDeptController</code></b>
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
@RequestMapping("/v1.0")
@Api(tags = "[Garnet]部门管理接口")
public class GarDeptController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarDeptController.class);

    /**
     * The Dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarDeptService deptService;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;


    @RequestMapping(value = "/depts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询该用户的部门列表", notes = "Get dept list by user id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:info")
    public ResponseEntity<?> getDeptList(
            @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
            @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
            @ApiParam(value = "名称") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            Integer offset = (page - 1) * limit;
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            params.put("searchName", searchName);
            List<GarVMDept> list = deptService.queryDeptListByParams(params);
            int totalCount = deptService.queryTotalMenuByParam(params);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to Get dept list ", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Gets dept list.
     *
     * @param userId the user id
     * @return the dept list
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/depts/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询该用户的部门列表", notes = "Get dept list by user id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:list:byUser")
    public ResponseEntity<?> getDeptListByUserId(@ApiParam(value = "userId,用户ID") @PathVariable(value = "userId") Long userId) {
        try {
            return new ResponseEntity<>(deptService.getUserDeptList(userId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to Get dept list by userId :" + userId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Gets dept list to add.
     *
     * @param userId the user id
     * @return the dept list to add
     * @since garnet-core-be-fe 0.1.0
     */
//    @RequestMapping(value = "/depts/add/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(value = "[Garnet]查询部门列表用于增加部门", notes = "Get dept list to add by user id")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "successful query"),
//            @ApiResponse(code = 500, message = "internal server error")})
//    @RequiresPermissions("dept:list:toAdd:byUser")
//    public ResponseEntity<?> getDeptListToAdd(@ApiParam(value = "userId,用户ID") @PathVariable(value = "userId") Long userId) {
//        try {
//            List<GarVMDept> deptList = deptService.getUserDeptList(userId);
//            GarVMUser vmUser = userService.searchUser(userId);
//            GarVMDept root = new GarVMDept();
//            root.setDeptId(0L);
//            root.setName(vmUser.getTenantName());
//            root.setParentDeptId(-1L);
//            deptList.add(root);
//            return new ResponseEntity<>(deptList, HttpStatus.OK);
//        } catch (Throwable t) {
//            LOG.error("Failed to get dept list to add", t);
//            return GarnetRsUtil.newResponseEntity(t);
//        }
//    }

    @RequestMapping(value = "/depts/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询部门列表用于增加部门", notes = "Get dept list to add by user id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:list:toAdd:byUser")
    public ResponseEntity<?> getDeptListToAdd() {
        try {
            List<GarVMDept> deptList = deptService.queryDeptListByParams(null);
//            deptList.add(root);
            return new ResponseEntity<>(deptList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get dept list to add", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search dept response entity.
     *
     * @param deptId the dept id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept/{deptId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询部门信息", notes = "Get dept by id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMDept.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:info")
    public ResponseEntity<?> searchDept(@ApiParam(value = "deptId", required = true) @PathVariable("deptId") Long deptId) {
        try {
            return new ResponseEntity<>(deptService.getVMDeptByDeptId(deptId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get dept :" + deptId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Save dept response entity.
     *
     * @param vmDept the gar dept
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增部门", notes = "Create dept")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:create")
    public ResponseEntity<?> saveDept(@RequestBody GarVMDept vmDept) {
        try {
            deptService.saveVMDept(vmDept);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create dept :" + vmDept, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update dept response entity.
     *
     * @param vmDept the gar dept
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]更新部门信息", notes = "Update dept")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:update")
    public ResponseEntity<?> updateDept(@RequestBody GarVMDept vmDept) {
        try {
            deptService.updateVMDept(vmDept);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update dept .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete dept response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id删除部门", notes = "Delete dept")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("dept:delete:batch")
    public ResponseEntity<?> deleteDept(
            @ApiParam(value = "deptIds,用‘,’隔开", required = true) @RequestParam(value = "deptIds") String deptIds) {
        try {
            Map<String, String> result = deptService.deleteBatchByDeptIds(GarnetRsUtil.parseStringToList(deptIds));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete applications :" + deptIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
