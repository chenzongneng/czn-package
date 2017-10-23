/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.GarDept;
import com.richstonedt.garnet.model.GarUserDept;
import com.richstonedt.garnet.service.GarDeptService;
import com.richstonedt.garnet.utils.GarnetRsUtil;
import io.swagger.annotations.*;
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
     * Gets dept list.
     *
     * @return the dept list
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/depts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询部门列表", notes = "Get dept list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getDeptList() {
        try {
            return new ResponseEntity<>(deptService.queryObjects(null, null, null), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to Get dept list ");
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Get dept list to add response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/depts/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询部门列表用于增加部门", notes = "Get dept list to add")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getDeptListToAdd() {
        try {
            List<GarDept> deptList = deptService.queryObjects(null, null, null);
            //todo 添加一级部门(租户名称)
            GarDept root = new GarDept();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentDeptId(-1L);
            //root.setOpen(true);
            deptList.add(root);
            return new ResponseEntity<>(deptList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get dept list to add");
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Gets user dept.
     *
     * @param token the token
     * @return the user dept
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept/user/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询用户所属部门", notes = "Get user's dept info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarDept.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getUserDept(@ApiParam(value = "token", required = true) @PathVariable("token") String token) {
        try {
            //todo 根据用户token获取该用户的部门信息
            GarUserDept userDept = new GarUserDept();
            userDept.setDeptId(0L);
            return new ResponseEntity<>(userDept, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user's dept :" + token);
            LOG.error(t.getMessage());
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
    @ApiOperation(value = "[Garnet]根据id查询部门", notes = "Get dept by id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarDept.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchDept(@ApiParam(value = "deptId", required = true) @PathVariable("deptId") Long deptId) {
        try {
            return new ResponseEntity<>(deptService.queryObject(deptId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get dept :" + deptId);
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Save dept response entity.
     *
     * @param garDept the gar dept
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增部门", notes = "Create dept")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveDept(@RequestBody GarDept garDept) {
        try {
            deptService.save(garDept);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create dept :" + garDept);
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update dept response entity.
     *
     * @param garDept the gar dept
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新部门", notes = "Update dept")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> updateDept(@RequestBody GarDept garDept) {
        try {
            deptService.update(garDept);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update dept .");
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete dept response entity.
     *
     * @param deptId the dept id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/dept/{deptId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id删除部门", notes = "Delete dept")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> deleteDept(@ApiParam(value = "deptId", required = true) @PathVariable(value = "deptId") Long deptId) {
        try {
            List<Long> deptList = deptService.queryDetpIdList(deptId);
            if (!CollectionUtils.isEmpty(deptList)) {
                Map<String, String> map = new HashMap<>(4);
                map.put("message", "请先删除子部门");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            deptService.deleteById(deptId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete applications :" + deptId);
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}