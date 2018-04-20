package com.richstonedt.garnet.controller;

import com.google.code.kaptcha.Producer;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginMessage;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.UserParm;
import com.richstonedt.garnet.model.view.*;
import com.richstonedt.garnet.service.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


    private static final Map<String, String> kaptchaMap = new HashMap<>();

    private static final Map<String, Object> loginMap = new HashMap<>();


    /** The service. */
    @Autowired
    private UserService userService;

    @Autowired
    private Producer producer;

    @LoginRequired
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

    @LoginRequired
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

    @LoginRequired
    @ApiOperation(value = "[Garnet]删除用户", notes = "批量删除用户")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteUsers(
            @ApiParam(value = "登录用户id", required = true) @RequestParam(value = "loginUserId") Long loginUserId,
            @ApiParam(value = "ids,用‘,’隔开", required = true) @RequestParam(value = "ids") String ids) {
        try {

            for (String id : ids.split(",")) {
                User user = new User();
                user.setId(Long.parseLong(id));
                userService.updateStatusById(user, loginUserId);
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

    @LoginRequired
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

    @LoginRequired
    @ApiOperation(value = "[Garnet]更新密码", notes = "更新用户密码")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 404, message = "not found"),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal Server Error") })
    @RequestMapping(value = "/users/password", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updatePassword(
            @ApiParam(value = "用户信息", required = true) @RequestBody UserCredentialView userCredentialView) {
        try {
            userService.updateUserPassword(userCredentialView);
            // 封装返回信息
            GarnetMessage<UserCredentialView> garnetMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_UPDATE_SUCCESS, null);
            return new ResponseEntity<>(garnetMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to update entity! " + MessageDescription.OPERATION_UPDATE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @LoginRequired
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

    @LoginRequired
    @ApiOperation(value = "[Garnet]获取用户列表", notes = "通过查询条件获取用户列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getUsers(
            @ApiParam(value = "用户名Id", defaultValue = "", required = false) @RequestParam(value = "userId", defaultValue = "", required = false) Long userId,
            @ApiParam(value = "租户Id", defaultValue = "", required = false) @RequestParam(value = "tenantId", defaultValue = "", required = false) Long tenantId,
            @ApiParam(value = "搜索", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {

            UserParm userParm = new UserParm();
            userParm.setUserId(userId);
            userParm.setTenantId(tenantId);
            userParm.setPageSize(pageSize);
            userParm.setSearchName(searchName);
            userParm.setPageNumber(pageNumber);
            PageUtil result = userService.queryUsersByParms(userParm);
            // 封装返回信息
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
    @RequestMapping(value = "/users/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
//            @ApiParam(value = "token", required = false) @RequestParam(value = "token") String token,
            @ApiParam(value = "用户", required = true) @RequestBody LoginView loginView) {

        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
        try {
            LoginMessage loginMessage = userService.userLogin(loginView);
            if (StringUtils.isEmpty(loginMessage.getAccessToken()) || StringUtils.isEmpty(loginMessage.getRefreshToken())) {
                error = "登录失败，请重新登录";
            }

            response.setHeader("accessToken", loginMessage.getAccessToken());
            response.setHeader("refreshToken", loginMessage.getRefreshToken());

            return new ResponseEntity<>(loginMessage, HttpStatus.OK);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]刷新token", notes = "刷新token")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @LoginRequired
    @RequestMapping(value = "/users/refreshtoken", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "token", required = false) @RequestParam(value = "token") String token,
            @ApiParam(value = "用户", required = true) @RequestBody TokenRefreshView tokenRefreshView) {
        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
        try {
            LoginMessage loginMessage = userService.refreshToken(tokenRefreshView);
            if (StringUtils.isEmpty(loginMessage.getAccessToken()) || StringUtils.isEmpty(loginMessage.getRefreshToken())) {
                error = "刷新失败，请重新登录";
            }
            response.setHeader("accessToken", loginMessage.getAccessToken());
            response.setHeader("refreshToken", loginMessage.getRefreshToken());
            // 设置http的headers
            return new ResponseEntity<>(loginMessage, HttpStatus.OK);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]garnet刷新token", notes = "garnet刷新token")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @LoginRequired
    @RequestMapping(value = "/users/garnetrefreshtoken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> garnetRefreshToken(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "token", required = false) @RequestParam(value = "token") String token,
            @ApiParam(value = "用户", required = true) @RequestBody LoginView loginView) {
        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
        try {
            LoginMessage loginMessage = userService.garnetRefreshToken(loginView);
            if (StringUtils.isEmpty(loginMessage.getAccessToken()) || StringUtils.isEmpty(loginMessage.getRefreshToken())) {
                error = "刷新失败，请重新登录";
            }
            response.setHeader("accessToken", loginMessage.getAccessToken());
            response.setHeader("refreshToken", loginMessage.getRefreshToken());
            // 设置http的headers
            return new ResponseEntity<>(loginMessage, HttpStatus.OK);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @LoginRequired
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

    @ApiOperation(value = "[Garnet]garnet登录", notes = "garnet登录")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", responseHeaders = @ResponseHeader(name = "location", description = "URL of new created resource", response = String.class) ),
            @ApiResponse(code = 409, message = "conflict"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/users/garnetlogin", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> garnetLogin(HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "用户", required = true) @RequestBody GarLoginView garLoginView) {

        String error = "Failed to add entity! " + MessageDescription.OPERATION_INSERT_FAILURE;
        try {

            LoginMessage loginMessage = new LoginMessage();
            String nowTime = garLoginView.getNowTime();
            String kaptcha = kaptchaMap.get(nowTime);
            if (StringUtils.isEmpty(garLoginView.getKaptcha()) || !garLoginView.getKaptcha().equals(kaptcha)) {
                loginMessage.setMessage("验证码不正确");
                loginMessage.setLoginStatus("false");
                loginMessage.setCode(401);

            } else {
                loginMessage = userService.garLogin(garLoginView);
            }

            //判断是否是同一个人登录
            String userName = garLoginView.getUserName();
            Long loginTime = System.currentTimeMillis();

            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                //之前已经有人登录, 删除cookie
                for(Cookie cookie : cookies) {
                    if (cookie.getName().equals(userName)) {
                        cookie.setValue(null);
                        // 立即销毁cookie
                        cookie.setMaxAge(0);
                        break;
                    }
                }
            }

            //设置新的cookie
            Cookie cookie = new Cookie(userName, String.valueOf(loginTime));
            response.addCookie(cookie);

            response.setHeader("accessToken", loginMessage.getAccessToken());
            response.setHeader("refreshToken", loginMessage.getRefreshToken());

            return new ResponseEntity<>(loginMessage, HttpStatus.OK);
        } catch (Throwable t) {
            error = t.getMessage();
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> garnetMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(garnetMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取验证码", notes = "Get Kaptcha")
    @RequestMapping(value = "/kaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getKaptcha(
            @ApiParam(value = "nowTime,当前时间毫秒值", required = true) @RequestParam(value = "nowTime") String nowTime,
            @ApiParam(value = "oldTime,上一张验证码的毫秒值", required = false) @RequestParam(value = "oldTime",required = false) String oldTime) throws IOException {
        try {
            //生成文字验证码
            String text = producer.createText();
            //生成图片验证码
            BufferedImage image = producer.createImage(text);
            if (!StringUtils.isEmpty(oldTime)) {
                kaptchaMap.remove(oldTime);
            }
            kaptchaMap.put(nowTime, text);
            // transform to byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", stream);
            byte[] result = stream.toByteArray();

            // modify header of response
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.IMAGE_JPEG);
            header.setCacheControl("no-store, no-cache");
            return new ResponseEntity<>(result, header, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get Kaptcha ", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
