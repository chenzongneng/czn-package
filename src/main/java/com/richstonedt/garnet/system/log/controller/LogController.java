/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.system.log.entity.LogEntity;
import com.richstonedt.garnet.system.log.service.LogService;
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
public class LogController {

    /**
     * The Log service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private LogService logService;

    /**
     * Gets user roles.
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the user roles
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAllLogs(
            @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit,
            @RequestParam(value = "searchName", required = false) String searchName) {
        try {
            List<LogEntity> results = logService.getLogs(page, limit,searchName);
            PageUtils pageUtils = new PageUtils(results, logService.getLogsCount(), limit, page);
            return new ResponseEntity<>(pageUtils, HttpStatus.OK);
        } catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }

    /**
     * Gets one log.
     *
     * @param id the id
     * @return the one log
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getOneLog(@PathVariable(value = "id") Integer id){
        try{
            LogEntity log = logService.getLogById(id);
            return new ResponseEntity<>(log, HttpStatus.OK);
        }catch (Throwable t) {
            return GarnetUtils.newResponseEntity(t);
        }
    }
}
