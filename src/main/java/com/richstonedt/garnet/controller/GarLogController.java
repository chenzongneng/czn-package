/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.model.GarLog;
import com.richstonedt.garnet.service.GarLogService;
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
 * <b><code>LogController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/29 14:54
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]系统日志查询接口")
public class GarLogController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarLogController.class);

    /**
     * The Log service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarLogService logService;

    /**
     * Gets logs.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the user roles
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询log列表", notes = "Get log list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarLog.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getAllLogs(
            @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
            @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
            @ApiParam(value = "searchName,搜索名") @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<GarLog> list = logService.queryObjects(searchName, page, limit);
            PageUtil result = new PageUtil(list, logService.queryTotal(), limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get log list .");
            LOG.error(t.toString());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Gets detail log.
     *
     * @param id the id
     * @return the one log
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询详细log信息", notes = "Get detail log info ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarLog.class),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getDetailLog(@ApiParam(value = "id,log ID", required = true) @PathVariable(value = "id") Long id) {
        try {
            return new ResponseEntity<>(logService.queryObject(id), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get log info :" + id);
            LOG.error(t.toString());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
