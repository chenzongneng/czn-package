/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.view.model.GarApiForImport;
import com.richstonedt.garnet.model.view.model.GarVmApi;
import com.richstonedt.garnet.service.GarApiService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarApiController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/7 14:49
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]API管理接口")
public class GarApiController {

    private static Logger LOG = LoggerFactory.getLogger(GarApiController.class);

    @Autowired
    private GarApiService apiService;


    @RequestMapping(value = "/apis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询API列表", notes = "Get api list ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVmApi.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("api:list")
    public ResponseEntity<?> searchApis(
            @ApiParam(value = "page,当前页", required = true) @RequestParam(value = "page") Integer page,
            @ApiParam(value = "limit,每页数量", required = true) @RequestParam(value = "limit") Integer limit,
            @ApiParam(value = "searchName,搜索名") @RequestParam(value = "name", required = false) String name,
            @ApiParam(value = "父权限ID") @RequestParam(value = "parentId", required = false) Long parentId,
            @ApiParam(value = "应用ID") @RequestParam(value = "applicationId", required = false) Long applicationId) {
        try {
            int offset = (page - 1) * limit;
            Map<String, Object> params = new HashMap<>();
            params.put("limit", limit);
            params.put("offset", offset);
            params.put("searchName", name);
            params.put("parentId", parentId);
            params.put("applicationId", applicationId);
            List<GarVmApi> list = apiService.queryApiList(params);
            int totalCount = apiService.queryTotalApi(params);
            PageUtil result = new PageUtil(list, totalCount, limit, page);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/apis/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]通过应用ID查询API列表", notes = "Get api list by application id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVmApi.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions("api:list:application")
    public ResponseEntity<?> searchApis(
            @ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        try {
            List<GarVmApi> list = apiService.queryApiListByApplicationId(applicationId);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/api/{apiId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id查询API信息", notes = "Get api info by api Id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVmApi.class),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"api:info"})
    public ResponseEntity<?> searchApi(@ApiParam(value = "apiId", required = true) @PathVariable(value = "apiId") Long apiId) {
        try {
            return new ResponseEntity<>(apiService.getApiById(apiId), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get api info :" + apiId, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/api", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]新增API", notes = "Create api")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"api:create"})
    public ResponseEntity<?> saveApi(@RequestBody GarVmApi garVMApi) {
        try {
            apiService.save(garVMApi);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to create api :" + garVMApi, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/api", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据ID更新API信息", notes = "Update api info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"api:update"})
    public ResponseEntity<?> updateApi(@RequestBody GarVmApi garVMApi) {
        try {
            apiService.update(garVMApi);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to update api info.", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/api", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]根据id批量删除菜单", notes = "Delete resources")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"api:delete:batch"})
    public ResponseEntity<?> deleteResources(
            @ApiParam(value = "apiIds,用‘,’隔开", required = true) @RequestParam(value = "apiIds") String apiIds) {
        try {
            Map<String,String> result = apiService.deleteBatchByApiIds(GarnetRsUtil.parseStringToList(apiIds));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to delete apiIds :" + apiIds, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/exportApis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]获取可导入的API列表数据，仅供开发使用", notes = "get apis which can import to database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVmApi.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"api:export"})
    public ResponseEntity<?> getExportApis() {
        try {
            List<GarApiForImport> apiList = apiService.getImportApiFromAnnotation(this.getClass());
            return new ResponseEntity<>(apiList, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    @RequestMapping(value = "/importApis/{appCode}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]导入API列表", notes = "import apis ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query", response = GarVmApi.class, responseContainer = "list"),
            @ApiResponse(code = 500, message = "internal server error")})
    @RequiresPermissions({"api:import"})
    public ResponseEntity<?> importApis(
            @RequestBody List<GarApiForImport> apiList,
            @ApiParam(value = "applicationId", required = true) @PathVariable("appCode") String appCode) {
        try {
            apiService.importApiByAppCode(apiList, appCode);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get user role .", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
