package com.richstonedt.garnet.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginMessage;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.view.ApplicationView;
import com.richstonedt.garnet.model.view.LoginView;
import com.richstonedt.garnet.model.view.RedirectView;
import com.richstonedt.garnet.service.ApplicationService;
import com.richstonedt.garnet.service.UserService;
import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>ApplicationController</code></b>
 * <p/>
 * Application的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Mar 01 14:32:30 CST 2018.
 *
 * @author ming
 * @version 1.0.0
 *
 */
@Api(value = "[Garnet]应用接口")
@RestController
@RequestMapping(value = "/api/v1.0")
public class ApplicationController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(ApplicationController.class);

    /** The service. */
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @LoginRequired
    @ApiOperation(value = "[Garnet]创建应用", notes = "创建一个应用")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = GarnetMessage.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createApplication(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "应用", required = true) @RequestBody ApplicationView applicationView,
            UriComponentsBuilder ucBuilder) {
        try {
            // 保存实体
            Long id = applicationService.insertApplication(applicationView);
            // 获取刚刚保存的实体
            Application application = applicationService.selectByPrimaryKey(id);
            ApplicationView applicationView1 = new ApplicationView();
            applicationView1.setApplication(application);
            // 设置http的headers
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/v1/applications/{id}")
                    .buildAndExpand(id).toUri());
            // 封装返回信息
            GarnetMessage<ApplicationView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_INSERT_SUCCESS, applicationView1);
            return new ResponseEntity<>(torinoSrcMessage, headers, HttpStatus.CREATED);
        } catch (Throwable t) {
            String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]删除应用", notes = "通过id删除应用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteApplication(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "应用id", required = true) @PathVariable(value = "id") long id) {
        try {
            applicationService.deleteByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<ApplicationView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]删除应用", notes = "批量删除应用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteApplications(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
        try {

            for (String id : ids.split(",")) {
                Application application = new Application();
                application.setId(Long.parseLong(id));
                applicationService.updateStatusById(application);
            }

            // 封装返回信息
            GarnetMessage<ApplicationView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {

            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]更新应用", notes = "更新应用信息")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/applications", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateApplications(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "应用信息", required = true) @RequestBody ApplicationView applicationView) {
        try {

            applicationService.updateApplication(applicationView);
            // 封装返回信息
            GarnetMessage<ApplicationView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, applicationView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]获取单个应用", notes = "通过id获取应用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getApplication(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "应用id", required = true) @PathVariable(value = "id") long id) {
        try {

            ApplicationView applicationView = applicationService.getApplicationWithTenant(id);
            // 封装返回信息
            GarnetMessage<ApplicationView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, applicationView);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]获取应用列表", notes = "通过查询条件获取应用列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getApplications(
            @ApiParam(value = "用户名Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "租户Id", defaultValue = "", required = false) @RequestParam(value = "tenantId", defaultValue = "", required = false) Long tenantId,
            @ApiParam(value = "searchName", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "queryOrTree", defaultValue = "", required = false) @RequestParam(value = "queryOrTree", defaultValue = "", required = false) String queryOrTree,
            @ApiParam(value = "modeId", defaultValue = "", required = false) @RequestParam(value = "modeId", defaultValue = "-1", required = false) Integer modeId,
            @ApiParam(value = "mode", defaultValue = "", required = false) @RequestParam(value = "mode", defaultValue = "", required = false) String  mode,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) int pageSize) {
        try {
            ApplicationParm applicationParm = new ApplicationParm();
            applicationParm.setUserId(userId);
            applicationParm.setTenantId(tenantId);
            applicationParm.setPageNumber(pageNumber);
            applicationParm.setPageSize(pageSize);
            applicationParm.setSearchName(searchName);
            applicationParm.setMode(mode);
            applicationParm.setQueryOrTree(queryOrTree);
            PageUtil applications = applicationService.getApplicationsByParams(applicationParm);

            // 封装返回信息
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]验证应用是否有关联", notes = "验证应用是否有关联")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/relate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> hasRelated(
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token,
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {
            boolean b = applicationService.hasRelated(ids);
            // 封装返回信息
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @LoginRequired
    @ApiOperation(value = "[Garnet]获取未被应用组选中的应用列表", notes = "获取未被应用组选中的应用列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/withoutroutergroup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getApplicationsWithoutRouterGroup(
            @ApiParam(value = "用户名Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "应用组id", defaultValue = "", required = false) @RequestParam(value = "routerGroupId", defaultValue = "", required = false) Long routerGroupId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token) {
        try {
            ApplicationParm applicationParm = new ApplicationParm();
            applicationParm.setUserId(userId);
            applicationParm.setSearchName(searchName);
            applicationParm.setRouterGroupId(routerGroupId);
            List<Application> applications = applicationService.getApplicatinsWithoutRouterGroup(applicationParm);
            // 封装返回信息
            return new ResponseEntity<>(applications, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取默认应用首页URL列表", notes = "通过用户名获取默认应用首页URL列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/getdefaulturl", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getApplicationsByUserName(
            @ApiParam(value = "用户名", defaultValue = "", required = false) @RequestParam(value = "userName", defaultValue = "", required = true) String userName,
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token) {
        try {
            List<Application> defaultIndexUrlBys = applicationService.getApplicationsByUserName(userName);
            // 封装返回信息
            return new ResponseEntity<>(defaultIndexUrlBys, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据选择的appCode跳转应用", notes = "根据选择的appCode跳转应用")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/redirect/{appCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> redirectByAppCode(
            @ApiParam(value = "appCode", required = true) @PathVariable(value = "appCode") String appCode,
            @ApiParam(value = "用户名", required = false) @RequestParam(value = "userName", defaultValue = "", required = false) String userName,
            @ApiParam(value = "密码", required = false) @RequestParam(value = "password", defaultValue = "", required = false) String password,
            HttpServletRequest request, HttpServletResponse response) {
        try {

            LoginView loginView = new LoginView();
            loginView.setUserName(userName);
            loginView.setPassword(password);
            loginView.setAppCode(appCode);

            LoginMessage loginMessage = userService.userLogin(loginView);
            Application application = applicationService.getApplicationByAppCode(appCode);

            RedirectView redirectView = new RedirectView();
            redirectView.setLoginStatus(loginMessage.getLoginStatus());
            redirectView.setMessage(loginMessage.getMessage());

            if ("success".equals(loginMessage.getLoginStatus())) {
                //如果登录成功，跳转
                String accessToken = loginMessage.getAccessToken();
                String refreshToken = loginMessage.getRefreshToken();
                Long userId = loginMessage.getUser().getId();
                String tenantNameAndId = JSONUtils.toJSONString(loginMessage.getUserTenantNameAndIdMap());
                String url = application.getDefaultIndexUrl() + "?accessToken=" + accessToken + "&refreshToken=" + refreshToken +
                        "&userId=" + userId + "&userName=" + userName + "&appCode=" + appCode + "&tenantNameAndId=" + tenantNameAndId;
                redirectView.setAccessToken(accessToken);
                redirectView.setRefreshToken(refreshToken);
                redirectView.setRedirectUrl(url);
                redirectView.setUser(loginMessage.getUser());

                response.setHeader("accessToken", loginMessage.getAccessToken());
                response.setHeader("refreshToken", loginMessage.getRefreshToken());

            }

            // 封装返回信息
            return new ResponseEntity<>(redirectView, HttpStatus.OK);

        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]根据userId和tenantId获取应用列表", notes = "通过用户名获取默认应用首页URL列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/applications/byuseridandtenantid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getApplicationsByUserIdAndTenantId(
            @ApiParam(value = "用户ID", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = true) Long userId,
            @ApiParam(value = "租户ID", defaultValue = "", required = false) @RequestParam(value = "tenantId", defaultValue = "", required = false) Long tenantId,
            @ApiParam(value = "资源路径", defaultValue = "", required = false) @RequestParam(value = "path", defaultValue = "", required = false) String path,
            @ApiParam(value = "access token", required = false) @RequestParam(value = "token", defaultValue = "", required = false) String token) {
        try {
            ApplicationParm applicationParm = new ApplicationParm();
            applicationParm.setUserId(userId);
            applicationParm.setTenantId(tenantId);
            applicationParm.setPath(path);
            List<Application> applicationList = applicationService.getApplicationsByUserIdAndTenantId(applicationParm);
            // 封装返回信息
            return new ResponseEntity<>(applicationList, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
