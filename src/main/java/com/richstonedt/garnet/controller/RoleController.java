package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.RolePermission;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.RoleView;
import com.richstonedt.garnet.service.RoleService;
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
 * <b><code>RoleController</code></b>
 * <p/>
 * Role的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Mar 01 15:17:10 CST 2018.
 *
 * @author ming
 * @version 1.0.0
 *
 */
@Api(value = "[Garnet]角色接口")
@RestController
@LoginRequired
@RequestMapping(value = "/api/v1.0")
public class RoleController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(RoleController.class);

    /** The service. */
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "[Garnet]创建角色", notes = "创建一个角色")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createRole(
            @ApiParam(value = "角色", required = true) @RequestBody RoleView roleView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            Long id = roleService.insertRole(roleView);
            // 获取刚刚保存的实体
            Role role = roleService.selectByPrimaryKey(id);

            RoleView roleView1 = new RoleView();

            roleView1.setRole(role);
                    // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/roles/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            GarnetMessage<RoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, roleView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除角色", notes = "通过id删除角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRole(
            @ApiParam(value = "角色id", required = true) @PathVariable(value = "id") long id) {
        try {
            roleService.selectByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<RoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除角色", notes = "批量删除角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteRoles(
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {
            for (String id : ids.split(",")) {
                Role role = new Role();
                role.setId(Long.parseLong(id));
                roleService.updateStatusById(role);
            }

            // 封装返回信息
            GarnetMessage<RoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]更新角色", notes = "更新角色信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/roles", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateRoles(
//            @ApiParam(value = "角色id", required = true) @PathVariable(value = "id") long id,
            @ApiParam(value = "角色信息", required = true) @RequestBody RoleView roleView) {
        try {
            roleService.updateRole(roleView);
            // 封装返回信息
            GarnetMessage<RoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, roleView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个角色", notes = "通过id获取角色")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRole(
            @ApiParam(value = "角色id", required = true) @PathVariable(value = "id") long id) {
        String message = "Failed to get entities!";
        try {
            final RoleView roleView = roleService.selectRoleWithGroupAndPermission(id);
            // 封装返回信息
            GarnetMessage<RoleView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, roleView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = message + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取角色列表以及部门名称列表等信息", notes = "通过查询条件获取角色列表以及部门名称列表等信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRolesView(
            @ApiParam(value = "用户id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "租户id", defaultValue = "", required = false) @RequestParam(value = "tenantId", defaultValue = "", required = false) Long tenantId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) int pageSize) {
        String message = "Failed to get entities!";
        try {
            RoleParm roleParm = new RoleParm();
            roleParm.setUserId(userId);
            roleParm.setSearchName(searchName );
            roleParm.setPageNumber(pageNumber);
            roleParm.setPageSize(pageSize);
            PageUtil rolePageInfo = roleService.queryRolesByParms(roleParm);

            // 封装返回信息
            return new ResponseEntity<>(rolePageInfo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = message + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据租户id获取角色列表", notes = "通过查询条件获取角色列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles/tenantId/{tenantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGroupsByTenantId(
            @ApiParam(value = "用户id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "tenantId", required = true) @PathVariable(value = "tenantId") Long tenantId) {
        try {
            RoleParm roleParm = new RoleParm();
            roleParm.setUserId(userId);
            roleParm.setSearchName(searchName);
            roleParm.setTenantId(tenantId);
            List<Role> roles = roleService.queryRolesByTenantId(roleParm);
            // 封装返回信息
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据应用id获取角色列表", notes = "通过查询条件获取角色列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles/applicationId/{applicationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGroupsByApplicationId(
            @ApiParam(value = "应用id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "applicationId", required = true) @PathVariable(value = "applicationId") Long applicationId) {
        try {
            RoleParm roleParm = new RoleParm();
            roleParm.setUserId(userId);
            roleParm.setSearchName(searchName);
            roleParm.setApplicationId(applicationId);
            List<Role> roles = roleService.queryRolesByApplicationId(roleParm);
            // 封装返回信息
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据应用id或租户id获取角色列表", notes = "通过查询条件获取角色列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roles/byparams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getGroupsByParams(
            @ApiParam(value = "用户id", defaultValue = "0", required = false) @RequestParam(value = "userId", defaultValue = "0", required = false) Long userId,
            @ApiParam(value = "租户id", defaultValue = "0", required = false) @RequestParam(value = "tenantId", defaultValue = "0", required = false) Long tenantId,
            @ApiParam(value = "应用id", defaultValue = "0", required = false) @RequestParam(value = "applicationId", defaultValue = "0", required = false) Long applicationId) {
        try {
            RoleParm roleParm = new RoleParm();
            roleParm.setUserId(userId);
            roleParm.setApplicationId(applicationId);
            roleParm.setTenantId(tenantId);
            roleParm.setApplicationId(applicationId);
            List<Role> roles = roleService.queryRolesByParams(roleParm);
            // 封装返回信息
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取全部角色列表", notes = "获取角色列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/roletree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getRoles() {
            String message = "Failed to get entities!";
        try {
            List<Role> roles = roleService.queryRoles();
            // 封装返回信息
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } catch (Throwable t) {
            String error =  message + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
