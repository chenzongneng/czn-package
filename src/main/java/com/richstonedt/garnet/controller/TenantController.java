package com.richstonedt.garnet.controller;


import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.TenantView;
import com.richstonedt.garnet.service.TenantService;
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
 * <b><code>TenantController</code></b>
 * <p/>
 * Tenant的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Mar 01 14:02:27 CST 2018.
 *
 * @author ming
 * @version 1.0.0
 * @since torinosrc-rs 1.0.0
 */
@Api(value = "[Torino Source]租户接口")
@RestController
@LoginRequired
@RequestMapping(value = "/api/v1.0")
public class TenantController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(TenantController.class);

    /** The service. */
    @Autowired
    private TenantService tenantService;

    @ApiOperation(value = "[Torino Source]创建租户", notes = "创建一个租户")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/tenants", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createTenant(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "租户", required = true) @RequestBody TenantView tenantView,
            UriComponentsBuilder ucBuilder) {
        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
        try {
            // 保存实体
            Long id = tenantService.insertTenant(tenantView);
            // 获取刚刚保存的实体
            Tenant tenant = tenantService.selectByPrimaryKey(id);

            TenantView tenantView1 = new TenantView();

            tenantView1.setTenant(tenant);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/tenants/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            GarnetMessage<TenantView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, tenantView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除租户", notes = "通过id删除租户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/tenants/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteTenant(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "租户id", required = true) @PathVariable(value = "id") long id) {
        try {
            tenantService.deleteTenant(id);
            // 封装返回信息
            GarnetMessage<TenantView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]删除租户", notes = "批量删除租户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/tenants", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteTenants(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {

            for (String id:
                    ids.split(",")) {
                Tenant tenant = new Tenant();
                tenant.setId(Long.parseLong(id));
                tenantService.updateStatusById(tenant);
//                tenantService.deleteTenant(Long.parseLong(id));
            }
            // 封装返回信息
            GarnetMessage<TenantView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]更新租户", notes = "更新租户信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/tenants", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateTenants(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "租户信息", required = true) @RequestBody TenantView tenantView) {
        try {

            tenantService.updateTenant(tenantView);
            // 封装返回信息
            GarnetMessage<TenantView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, tenantView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取单个租户", notes = "通过id获取租户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/tenants/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTenant(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "租户id", required = true) @PathVariable(value = "id") long id) {
        try {
            TenantView tenantView = tenantService.getTenantWithApplication(id);

            // 封装返回信息
            GarnetMessage<TenantView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, tenantView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取租户列表", notes = "通过查询条件获取租户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/tenants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTenants(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "用户名Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "应用Id", defaultValue = "", required = false) @RequestParam(value = "applicaionId", defaultValue = "", required = false) Long applicaionId,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @ApiParam(value = "modeId", defaultValue = "0", required = false) @RequestParam(value = "modeId", defaultValue = "-1", required = false) int modeId,
            @ApiParam(value = "mode", defaultValue = "0", required = false) @RequestParam(value = "mode", defaultValue = "", required = false) String mode,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) int limit) {
        try {
            TenantParm tenantParm = new TenantParm();
            tenantParm.setUserId(userId);
            tenantParm.setSearchName(searchName);
            tenantParm.setApplicationId(applicaionId);
            tenantParm.setPageSize(limit);
            tenantParm.setPageNumber(page);
//            tenantParm.setModeId(modeId);
            tenantParm.setMode(mode);
            PageUtil result = tenantService.queryTenantssByParms(tenantParm);
            // 封装返回信息
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Torino Source]获取已绑定租户的应用Id列表", notes = "获取已绑定租户的应用Id列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/tenants/appidwithtenant", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getApplicationIs(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token) {
//            @ApiParam(value = "租户id", required = true) @PathVariable(value = "id") long id) {
        try {
            List<Long> applicationIs = tenantService.getApplicationIs();

            // 封装返回信息
            GarnetMessage<List<Long>> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, applicationIs);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
