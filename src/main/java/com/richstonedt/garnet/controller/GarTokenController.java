/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.service.GarTokenService;
import com.richstonedt.garnet.utils.GarnetRsUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b><code>GarTokenController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 16:12
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]Token相关接口")
public class GarTokenController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarTokenController.class);

    /**
     * The Token service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTokenService tokenService;

    /**
     * Gets user info by token.
     *
     * @param token the token
     * @return the user info by token
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/token/userInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]通过Token查询用户信息", notes = "Get user info by token")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getUserInfoByToken(@ApiParam(value = "token,garnetToken") @RequestParam(value = "token") String token) {
        try {
            return new ResponseEntity<>(tokenService.getUserInfoByToken(token), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Get user info by token = " + token, t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }
}
