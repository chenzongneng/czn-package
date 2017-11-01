/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.GarLogOperation;
import com.richstonedt.garnet.model.view.model.GarVMUser;
import com.richstonedt.garnet.service.GarLogOperationService;
import com.richstonedt.garnet.utils.GarnetRsUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b><code>GarLogOperationController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/31 18:25
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]Log操作管理接口")
public class GarLogOperationController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarLogOperationController.class);

    /**
     * The Log operation dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarLogOperationService logOperationService;

    /**
     * Search operations response entity.
     *
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logOperations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询log操作列表", notes = "Get operation list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMUser.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchOperations() {
        try {
            return new ResponseEntity<>(logOperationService.getAllOperations(), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get operation list .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Save operation response entity.
     *
     * @param logOperation the log operation
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logOperation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增log操作", notes = "Create operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> saveOperation(@RequestBody GarLogOperation logOperation) {
        try {
            logOperationService.save(logOperation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create operation :" + logOperation, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete log operation response entity.
     *
     * @param id the id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logOperation/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id删除log操作", notes = "Delete log Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> deleteLogOperation(@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
        try {
            logOperationService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete log operation  :" + id, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Delete log operations response entity.
     *
     * @param ids the ids
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logOperations", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除 log Operation", notes = "Delete log Operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> deleteLogOperations(@ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {
            logOperationService.deleteBatch(GarnetRsUtil.parseStringToList(ids));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete log Operations :" + ids, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search log operation response entity.
     *
     * @param id the id
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logOperation/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询log Operation信息", notes = "Get log Operation info by id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVMUser.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchLogOperation(@ApiParam(value = "id", required = true) @PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(logOperationService.queryObject(id), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get log Operation info :" + id, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Update user response entity.
     *
     * @param logOperation the log operation
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logOperation", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新log Operation 信息", notes = "Update log Operation info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> updateUser(@RequestBody GarLogOperation logOperation) {
        try {
            logOperationService.update(logOperation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update log Operation info  .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
