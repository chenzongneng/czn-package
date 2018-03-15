package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.UserParm;
import com.richstonedt.garnet.model.view.LoginView;
import com.richstonedt.garnet.model.view.UserProfile;
import com.richstonedt.garnet.model.view.UserView;
import com.richstonedt.garnet.service.UserService;
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
 * <b><code>UserController</code></b>
 * <p/>
 * User的具体实现
 * <p/>
 * <b>Creation Time:</b> Wed Feb 28 18:34:04 CST 2018.
 *
 * @author ming
 * @version 1.0.0
 * @since torinosrc-rs 1.0.0
 */
@Api(value = "[Garnet]用户接口")
@RestController
@RequestMapping(value = "/api/v1.0")
public class UserController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserController.class);

    /** The service. */
    @Autowired
    private UserService userService;

    @ApiOperation(value = "[Garnet]创建用户", notes = "创建一个用户")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUser(
            @ApiParam(value = "用户", required = true) @RequestBody UserView userView,
            UriComponentsBuilder ucBuilder) {

        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;

        try {
            // 保存实体
            Long id = userService.insertUser(userView);
            // 获取刚刚保存的实体
            User user = userService.selectByPrimaryKey(id);

            UserView userView1 = new UserView();

            userView1.setUser(user);

            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/users/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            GarnetMessage<UserView> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, userView1);
            return new ResponseEntity<>(garnetMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除用户", notes = "通过id删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUser(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") long id) {
        try {
            User user = new User();
            user.setId(id);
            UserView userView = new UserView();
            userView.setUser(user);
            userService.deleteUser(userView);
            // 封装返回信息
            GarnetMessage<UserView> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(garnetMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除用户", notes = "批量删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUsers(
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {

//            for (String userId:
//                    ids.split(",")) {
//                UserView userView = new UserView();
//                User user  = new User();
//                user.setId(Long.parseLong(userId));
//                userView.setUser(user);
//                userService.deleteUser(userView);
//            }

            for (String id : ids.split(",")) {
                User user = new User();
                user.setId(Long.parseLong(id));
                userService.updateStatusById(user);
            }

            // 封装返回信息
            GarnetMessage<UserView> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(garnetMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]更新用户", notes = "更新用户信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateUsers(
            @ApiParam(value = "用户信息", required = true) @RequestBody UserView userView) {
        try {

            userService.updateUser(userView);
            // 封装返回信息
            GarnetMessage<UserView> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, userView);
            return new ResponseEntity<>(garnetMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个用户", notes = "通过id获取用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUser(
            @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") long id) {
        try {
            UserView userView  = userService.getUserById(id);
            // 封装返回信息
            GarnetMessage<UserView> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, userView);
            return new ResponseEntity<>(garnetMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取用户列表", notes = "通过查询条件获取用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUsers(
            @ApiParam(value = "用户名Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "租户Id", defaultValue = "", required = false) @RequestParam(value = "tenantId", defaultValue = "", required = false) Long tenantId,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {

            UserParm userParm = new UserParm();
            //userParm.setUserId(userId);
            userParm.setTenantId(tenantId);
            userParm.setPageSize(pageSize);
            userParm.setPageNumber(pageNumber);
            PageUtil result = userService.queryUsersByParms(userParm);
            // 封装返回信息
//            GarnetMessage<PageUtil> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, result);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]用户登录", notes = "用户登录")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(
            @ApiParam(value = "用户", required = true) @RequestBody LoginView loginView,
            UriComponentsBuilder ucBuilder) {

        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;

        try {

            UserProfile userProfile = userService.userLogin(loginView);

            // 设置http的headers
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据租户id获取用户列表", notes = "通过查询条件获取用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users/tenantId/{tenantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGroupsByTenantId(
            @ApiParam(value = "用户id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "tenantId", required = true) @PathVariable(value = "tenantId") Long tenantId) {
        try {
            UserParm userParm = new UserParm();
            userParm.setUserId(userId);
            userParm.setTenantId(tenantId);
            List<User> users = userService.queryUserByTenantId(userParm);
            // 封装返回信息
//            GarnetMessage<PageInfo<Group>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, pageInfo);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
