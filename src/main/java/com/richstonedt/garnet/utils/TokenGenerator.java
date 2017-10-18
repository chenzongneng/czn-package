/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.richstonedt.garnet.config.GarnetServiceException;
import com.richstonedt.garnet.model.GarToken;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.service.GarTokenService;
import com.richstonedt.garnet.service.GarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

/**
 * <b><code>TokenGenerator</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 15:41
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Component
public class TokenGenerator {

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

    /**
     * The Token service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTokenService tokenService;

    /**
     * The constant HEX_CODE.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();

    /**
     * Create token string.
     *
     * @param userId the user id
     * @return the string
     * @since garnet-core-be-fe 0.1.0
     */
    public String createUserToken(Integer userId) {
        GarUser user = userService.queryObject(userId);
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withClaim("uid", user.getUserId())
                    .withClaim("una", user.getUserName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + (long) 60 * 60 * 1000 * 3))
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new GarnetServiceException(e, "UTF-8 encoding not supported when generating token");
        }
    }

    /**
     * Create garnet token string.
     *
     * @param userId the user id
     * @return the string
     * @since garnet-core-be-fe 0.1.0
     */
    public String createGarnetToken(Integer userId) {
        //生成一个token
        String token = TokenGenerator.generateValue(UUID.randomUUID().toString());
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + 60 * 60 * 1000 * 3);
        //判断是否生成过token
        GarToken garToken = tokenService.queryObject(userId);
        if (garToken == null) {
            garToken = new GarToken();
            garToken.setUserId(userId);
            garToken.setToken(token);
            garToken.setUpdateTime(now);
            garToken.setExpireTime(expireTime);
            //保存token
            tokenService.save(garToken);
        } else {
            garToken.setToken(token);
            garToken.setUpdateTime(now);
            garToken.setExpireTime(expireTime);
            //更新token
            tokenService.update(garToken);
        }
        return token;
    }

    /**
     * Generate value string.
     *
     * @param param the param
     * @return the string
     * @since garnet-core-be-fe 0.1.0
     */
    private static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new GarnetServiceException("生成Token失败", e);
        }
    }

    /**
     * To hex string string.
     *
     * @param data the data
     * @return the string
     * @since garnet-core-be-fe 0.1.0
     */
    private static String toHexString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }
}
