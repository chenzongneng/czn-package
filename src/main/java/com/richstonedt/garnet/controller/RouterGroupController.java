package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.RouterGroup;
import com.richstonedt.garnet.model.criteria.RouterGroupCriteria;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.RouterGroupParm;
import com.richstonedt.garnet.model.view.RouterGroupView;
import com.richstonedt.garnet.service.RouterGroupService;
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

import java.util.List;

/**
 * <b><code>RouterGroupController</code></b>
 * <p/>
 * RouterGroup的具体实现
 * <p/>
 * <b>Creation Time:</b> Wed Mar 21 12:04:44 CST 2018.
 *
 * @author maxuepeng
 * @version 1.0.0
 * @since torinosrc-rs 1.0.0
 */
@Api(value = "[Torino Source]router白名单接口",tags = "[Torino Source]router白名单接口",description = "")
@RestController
@LoginRequired
@RequestMapping(value = "/api/v1.0")
public class RouterGroupController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(RouterGroupController.class);

    /** The service. */
    @Autowired
    private RouterGroupService routerGroupService;

    @ApiOperation(value = "[Torino Source]创建router白名单", notes = "创建一个router白名单")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/routergroups", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createRouterGroup(
            @ApiParam(value = "router白名单", required = true) @RequestBody RouterGroupView routerGroupView) {
        try {
            // 保存实体
            String groupName = routerGroupService.insertRouterGroup(routerGroupView);
            // 获取刚刚保存的实体
            RouterGroupCriteria routerGroupCriteria = new RouterGroupCriteria();
            routerGroupCriteria.createCriteria().andGroupNameEqualTo(groupName);
            List<RouterGroup> routerGroups = routerGroupService.selectByCriteria(routerGroupCriteria);
            // 封装返回信息
            GarnetMessage<List<RouterGroup>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, routerGroups);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除router白名单", notes = "通过id删除router白名单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/routergroups/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRouterGroup(
            @ApiParam(value = "router白名单id", required = true) @PathVariable(value = "id") Long id) {
        try {
            routerGroupService.deleteByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<RouterGroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除router白名单", notes = "批量删除router白名单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/routergroups", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRouterGroups(
            @ApiParam(value = "router白名单ids，样例 - 1,2,3", required = true) @RequestParam String ids) {
        try {

            for (String id : ids.split(",")) {
                RouterGroupView routerGroupView = new RouterGroupView();
                RouterGroup routerGroup = new RouterGroup();
                routerGroup.setId(Long.parseLong(id));
                routerGroupView.setRouterGroup(routerGroup);
                routerGroupService.deleteRouterGroup(routerGroupView);
            }
            // 封装返回信息
            GarnetMessage<RouterGroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新router白名单", notes = "更新router白名单信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/routergroups", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateRouterGroups(
            @ApiParam(value = "router白名单信息", required = true) @RequestBody RouterGroupView routerGroupView) {
        try {
            routerGroupService.updateRouterGroup(routerGroupView);
            // 封装返回信息
            GarnetMessage<RouterGroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, routerGroupView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个router白名单", notes = "通过id获取router白名单")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/routergroups/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRouterGroup(
            @ApiParam(value = "router白名单id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final RouterGroupView routerGroupView = routerGroupService.selectRouterByIdWithApp(id);
            // 封装返回信息
            GarnetMessage<RouterGroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, routerGroupView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取router白名单列表", notes = "通过查询条件获取router白名单列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/routergroups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRouterGroups(
            @ApiParam(value = "用户Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) Integer pageSize) {
        try {
            RouterGroupParm routerGroupParm = new RouterGroupParm();
            routerGroupParm.setUserId(userId);
            routerGroupParm.setSearchName(searchName);
            routerGroupParm.setPageNumber(pageNumber);
            routerGroupParm.setPageSize(pageSize);
            PageUtil rolePageInfo = routerGroupService.queryRouterGroupByParms(routerGroupParm);
            // 封装返回信息
            return new ResponseEntity<>(rolePageInfo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取router白名单列表", notes = "通过查询条件获取router白名单列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/routergroups/checkname", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> checkGroupName(
            @ApiParam(value = "ID", defaultValue = "", required = false) @RequestParam(value = "id", defaultValue = "0", required = true) Long id,
            @ApiParam(value = "应用组名", defaultValue = "", required = false) @RequestParam(value = "groupName", defaultValue = "", required = true) String groupName) {
        try {

            boolean result = routerGroupService.isGroupNameUsed(id, groupName);
            // 封装返回信息
            GarnetMessage<Boolean> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, result);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
