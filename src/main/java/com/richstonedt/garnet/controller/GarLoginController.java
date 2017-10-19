/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.google.code.kaptcha.Producer;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.view.model.GarLoginResult;
import com.richstonedt.garnet.model.view.model.GarUserLogin;
import com.richstonedt.garnet.service.GarUserService;
import com.richstonedt.garnet.utils.GarnetRsUtil;
import com.richstonedt.garnet.utils.TokenGeneratorUtil;
import io.swagger.annotations.*;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <b><code>GarLoginController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 16:59
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]登录相关接口")
public class GarLoginController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarLoginController.class);

    /**
     * The Producer.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private Producer producer;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

    /**
     * The Token generator.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private TokenGeneratorUtil tokenGenerator;

    /**
     * The constant kaptchaMap.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Map<String, String> kaptchaMap = new HashMap<>();

    /**
     * The constant LOGIN_FORM.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static final String LOGIN_FORM = "garnet";

    /**
     * Gets kaptcha.
     *
     * @param nowTime the now time
     * @return the kaptcha
     * @throws IOException the io exception
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/kaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation(value = "[Garnet]获取验证码", notes = "Get Kaptcha")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getKaptcha(@ApiParam(value = "nowTime,当前时间毫秒值", required = true) @RequestParam(value = "nowTime") String nowTime) throws IOException {
        try {
            //生成文字验证码
            String text = producer.createText();
            //生成图片验证码
            BufferedImage image = producer.createImage(text);
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
            LOG.error("Failed to get Kaptcha ");
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Login response entity.
     *
     * @param loginFrom the login from
     * @param userLogin the user login
     * @return the response entity
     * @throws IOException the io exception
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@ApiParam(value = "loginFrom,源登录项目名") @RequestParam(value = "loginFrom") String loginFrom,
                                   @RequestBody GarUserLogin userLogin) throws IOException {
        try {
            GarLoginResult loginResult = new GarLoginResult();
            String kaptcha = kaptchaMap.get(userLogin.getNowTime());
            if (!userLogin.getCaptcha().equalsIgnoreCase(kaptcha)) {
                loginResult.setLoginStatus("failure");
                loginResult.setMessage("验证码不正确");
                return new ResponseEntity<>(loginResult, HttpStatus.OK);
            }
            kaptchaMap.remove("kaptcha");
            GarUser user = userService.getUserByName(userLogin.getUserName());
            if (user == null) {
                loginResult.setLoginStatus("failure");
                loginResult.setMessage("账号不存在");
                return new ResponseEntity<>(loginResult, HttpStatus.OK);
            }
            if (!BCrypt.checkpw(userLogin.getPassword(), user.getPassword())) {
                loginResult.setLoginStatus("failure");
                loginResult.setMessage("账号或密码不正确");
                return new ResponseEntity<>(loginResult, HttpStatus.OK);
            }
            if (user.getStatus() == 0) {
                loginResult.setLoginStatus("failure");
                loginResult.setMessage("账号已被锁定,请联系管理员");
                return new ResponseEntity<>(loginResult, HttpStatus.OK);
            }
            if (LOGIN_FORM.equals(loginFrom.toLowerCase())) {
                //todo 鉴权判断，只有租户管理员和超级管理员才可登录
            }
            //生成token
            loginResult.setGarnetToken(tokenGenerator.createGarnetToken(user.getUserId()));
            loginResult.setUserToken(tokenGenerator.createUserToken(user.getUserId()));
            loginResult.setLoginStatus("success");
            loginResult.setMessage("登录成功");
            return new ResponseEntity<>(loginResult, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to login");
            LOG.error(t.getMessage());
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
