/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.common.utils.ShiroUtils;
import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.modules.sys.service.SysUserService;
import com.richstonedt.garnet.modules.sys.service.SysUserTokenService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
public class SysLoginController {

    /**
     * The Producer.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private Producer producer;

    /**
     * The Sys user service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * The Sys user token service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * Kaptcha.
     *
     * @return the kaptcha
     * @throws IOException the io exception
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/kaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getKaptcha() throws IOException {

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        // transform to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", stream);
        byte[] result = stream.toByteArray();

        // modify header of response
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        header.setCacheControl("no-store, no-cache");

        return new ResponseEntity<>(result, header, HttpStatus.OK);
    }

    /**
     * 登录
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public Map<String, Object> login(String username, String password, String captcha) throws IOException {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(kaptcha)) {
            return Result.error("验证码不正确");
        }
        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(username);
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return Result.error("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            return Result.error("账号已被锁定,请联系管理员");
        }
        //生成token，并保存到数据库
        Result result = sysUserTokenService.createToken(user.getUserId());
        result.put("roleId",user.getRoleId());
        return result;
    }

}
