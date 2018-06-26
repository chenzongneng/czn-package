package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>ResourceDynamicPropertyController</code></b>
 * <p/>
 * ResourceDynamicProperty的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Mar 16 14:07:29 CST 2018.
 *
 * @author maxuepeng
 * @version 1.0.0
 *
 */
@Api(value = "[Garnet]资源动态属性接口")
@RestController
@LoginRequired
@RequestMapping(value = "/api/v1.0")
public class ResourceDynamicPropertyController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ResourceDynamicPropertyController.class);

    /** The service. */
    @Autowired
    private ResourceDynamicPropertyService resourceDynamicPropertyService;

    @ApiOperation(value = "[Garnet]创建资源动态属性", notes = "创建一个资源动态属性")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createResourceDynamicProperty(
            @ApiParam(value = "资源动态属性", required = true) @RequestBody ResourceDynamicPropertyView resourceDynamicPropertyView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            resourceDynamicPropertyService.insertResourceDynamicProperty(resourceDynamicPropertyView);

            // 封装返回信息
            GarnetMessage<ResourceDynamicPropertyView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, resourceDynamicPropertyView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除资源动态属性", notes = "通过id删除资源动态属性")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteResourceDynamicProperty(
            @ApiParam(value = "资源动态属性id", required = true) @PathVariable(value = "id") Long id) {
        try {
            resourceDynamicPropertyService.deleteByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<ResourceDynamicPropertyView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除资源动态属性", notes = "批量删除资源动态属性")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteResourceDynamicPropertys(
            @ApiParam(value = "资源动态属性ids，样例 - 1,2,3", required = true) @RequestParam(value = "ids") String ids) {
        try {
            ResourceDynamicPropertyView resourceDynamicPropertyView = new ResourceDynamicPropertyView();
            for (String id : ids.split(",")) {
                ResourceDynamicProperty resourceDynamicProperty = resourceDynamicPropertyService.selectByPrimaryKey(Long.parseLong(id));
                resourceDynamicPropertyView.setResourceDynamicProperty(resourceDynamicProperty);
                resourceDynamicPropertyService.deleteResourceDynamicPropertyWithType(resourceDynamicPropertyView);
            }

            // 封装返回信息
            GarnetMessage<ResourceDynamicPropertyView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]更新资源动态属性", notes = "更新资源动态属性信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "resourcedynamicpropertys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateResourceDynamicPropertys(
            @ApiParam(value = "资源动态属性信息", required = true) @RequestBody ResourceDynamicPropertyView resourceDynamicPropertyView) {
        try {
            resourceDynamicPropertyService.updateResourceDynamicProperty(resourceDynamicPropertyView);
            // 封装返回信息
            GarnetMessage<ResourceDynamicPropertyView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, resourceDynamicPropertyView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个资源动态属性", notes = "通过id获取资源动态属性")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResourceDynamicProperty(
            @ApiParam(value = "资源动态属性id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final ResourceDynamicPropertyView resourceDynamicPropertyView = resourceDynamicPropertyService.selectResourceDynamicPropertyViewById(id);
            // 封装返回信息
            GarnetMessage<ResourceDynamicPropertyView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, resourceDynamicPropertyView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取资源动态属性列表", notes = "通过查询条件获取资源动态属性列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResourceDynamicPropertys(
            @ApiParam(value = "用户id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "租户id", defaultValue = "0", required = false) @RequestParam(value = "tenantId", defaultValue = "0", required = false) Long tenantId,
            @ApiParam(value = "应用id", defaultValue = "0", required = false) @RequestParam(value = "applicationId", defaultValue = "0", required = false) Long applicationId,
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "资源类型", defaultValue = "", required = false) @RequestParam(value = "type", defaultValue = "", required = false) String type,
            @ApiParam(value = "状态", defaultValue = "", required = false) @RequestParam(value = "enabled", defaultValue = "", required = false) Integer enabled,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) Integer pageSize) {
        try {
            ResourceDynamicPropertyParm resourceDynamicPropertyParm = new ResourceDynamicPropertyParm();
            resourceDynamicPropertyParm.setUserId(userId);
            resourceDynamicPropertyParm.setTenantId(tenantId);
            resourceDynamicPropertyParm.setApplicationId(applicationId);
            resourceDynamicPropertyParm.setPageSize(pageSize);
            resourceDynamicPropertyParm.setPageNumber(pageNumber);
            resourceDynamicPropertyParm.setSearchName(searchName);
            resourceDynamicPropertyParm.setType(type);
            PageUtil pageInfo = resourceDynamicPropertyService.getResourceDynamicPropertiesByParams(resourceDynamicPropertyParm);
            // 封装返回信息
//            GarnetMessage<Page<ResourceDynamicPropertyView>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, resourceDynamicPropertyViews);
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个资源动态属性", notes = "通过type获取资源动态属性")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys/type/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResourceDynamicPropertyByType(
            @ApiParam(value = "资源动态属性type", required = true) @PathVariable(value = "type") String type) {
        try {
            final ResourceDynamicPropertyView resourceDynamicPropertyView = resourceDynamicPropertyService.selectResourceDynamicPropertyViewByType(type);
            // 封装返回信息
            GarnetMessage<ResourceDynamicPropertyView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, resourceDynamicPropertyView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]检查资源配置类型代号是否已被使用", notes = "检查资源配置类型代号是否已被使用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys/checktype", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> checkType(
            @ApiParam(value = "资源动态属性ID", required = true) @RequestParam(value = "id", defaultValue = "0", required = true) Long id,
            @ApiParam(value = "资源动态属性type", required = true) @RequestParam(value = "type", defaultValue = "", required = true) String type) {
        try {
            boolean result = resourceDynamicPropertyService.isTypeUsed(id, type);
            // 封装返回信息
            GarnetMessage<Boolean> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, result);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]检查资源配置类型名称是否已被使用", notes = "检查资源配置类型名称是否已被使用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys/checkname", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> checkRemark(
            @ApiParam(value = "资源动态属性ID", required = true) @RequestParam(value = "id", defaultValue = "0", required = true) Long id,
            @ApiParam(value = "资源动态属性名称", required = true) @RequestParam(value = "name", defaultValue = "", required = true) String name) {
        try {
            boolean result = resourceDynamicPropertyService.isResourceDyPropNameUsed(id, name);
            // 封装返回信息
            GarnetMessage<Boolean> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, result);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据tenantId和applicationId获取资源动态属性列表", notes = "根据tenantId和applicationId获取资源动态属性列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/resourcedynamicpropertys/byparams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getResourceDynamicPropertysByTIdAndAId(
            @ApiParam(value = "用户id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "租户id", defaultValue = "0", required = false) @RequestParam(value = "tenantId", defaultValue = "0", required = false) Long tenantId,
            @ApiParam(value = "应用id", defaultValue = "0", required = false) @RequestParam(value = "applicationId", defaultValue = "0", required = false) Long applicationId) {
        try {
            ResourceDynamicPropertyParm resourceDynamicPropertyParm = new ResourceDynamicPropertyParm();
            resourceDynamicPropertyParm.setUserId(userId);
            resourceDynamicPropertyParm.setTenantId(tenantId);
            resourceDynamicPropertyParm.setApplicationId(applicationId);
            List<ResourceDynamicProperty> resourceDynamicPropertyList = resourceDynamicPropertyService.getResourceDynamicPropertyByTIdAndAId(resourceDynamicPropertyParm);
            // 封装返回信息
            return new ResponseEntity<>(resourceDynamicPropertyList, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
