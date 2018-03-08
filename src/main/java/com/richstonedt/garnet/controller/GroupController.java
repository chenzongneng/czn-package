package com.richstonedt.garnet.controller;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.GroupView;
import com.richstonedt.garnet.service.GroupService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GroupController</code></b>
 * <p/>
 * Group的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Mar 01 14:57:45 CST 2018.
 *
 * @author ming
 * @version 1.0.0
 * @since torinosrc-rs 1.0.0
 */
@Api(value = "[Garnet]用户组接口")
@RestController
@RequestMapping(value = "/api/v1.0")
public class GroupController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(GroupController.class);

    /** The service. */
    @Autowired
    private GroupService groupService;

    @ApiOperation(value = "[Garnet]创建用户组", notes = "创建一个用户组")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/groups", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createGroup(
            @ApiParam(value = "用户组", required = true) @RequestBody GroupView groupView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            Long id = groupService.insertGroup(groupView);
            // 获取刚刚保存的实体
            Group group = groupService.selectByPrimaryKey(id);
            GroupView groupView1 = new GroupView();
            groupView1.setGroup(group);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/groups/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            GarnetMessage<GroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, groupView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除用户组", notes = "通过id删除用户组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteGroup(
            @ApiParam(value = "用户组id", required = true) @PathVariable(value = "id") long id) {
        try {
            groupService.deleteByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<GroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除用户组", notes = "批量删除用户组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/groups", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> deleteGroups(
            @ApiParam(value = "用户组ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            List<Long> idslist = new ArrayList<>();
            for (String id:
                    ids.split(",")) {
                idslist.add(Long.parseLong(id));
            }
            GroupCriteria groupCriteria = new GroupCriteria();
            groupCriteria.createCriteria().andIdIn(idslist);
            groupService.deleteByCriteria(groupCriteria);
            // 封装返回信息
            GarnetMessage<GroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]更新用户组", notes = "更新用户组信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateGroups(
            @ApiParam(value = "用户组id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "用户组信息", required = true) @RequestBody GroupView groupView) {
        try {
            groupView.getGroup().setId(id);
            groupService.updateGroup(groupView);
            // 封装返回信息
            GarnetMessage<GroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, groupView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个用户组", notes = "通过id获取用户组")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGroup(
            @ApiParam(value = "用户组id", required = true) @PathVariable(value = "id") long id) {
        try {
            Group group = groupService.selectByPrimaryKey(id);

            GroupView groupView = new GroupView();

            groupView.setGroup(group);
            // 封装返回信息
            GarnetMessage<GroupView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, groupView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取用户组列表", notes = "通过查询条件获取用户组列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGroups(
            @ApiParam(value = "用户id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "租户id", defaultValue = "0", required = false) @RequestParam(value = "tenantId", defaultValue = "0", required = false) Long tenantId,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) int pageSize) {
        try {
            GroupParm groupParm = new GroupParm();
            groupParm.setUserId(userId);
            groupParm.setTenantId(tenantId);
            groupParm.setPageSize(pageSize);
            groupParm.setPageNumber(pageNumber);
            PageUtil  pageInfo = groupService.queryGroupsByParms(groupParm);
            // 封装返回信息
//            GarnetMessage<PageInfo<Group>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, pageInfo);
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
