package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.service.GarShiroService;
import com.richstonedt.garnet.service.GarUserResourceService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b><code>GarPublicApiController</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>12:02
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
@Api(tags = "[Garnet]公用的api")
public class GarPublicApiController {

    /**
     * The constant LOG.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static Logger LOG = LoggerFactory.getLogger(GarSysMenuController.class);

    /**
     * The User resource service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserResourceService userResourceService;

    /**
     * The Shiro service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarShiroService shiroService;

    /**
     * Search sys menu response entity.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/resource/userId/{userId}/appCode/{appCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询系统菜单", notes = "Search system menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> getResourceCodeByUserIdAndAppCode(
            @ApiParam(value = "userId", required = true) @PathVariable(value = "userId") Long userId,
            @ApiParam(value = "appCode", required = true) @PathVariable(value = "appCode") String appCode) {
        try {
            return new ResponseEntity<>(userResourceService.getResourceCodeByUserIdAndAppCode(userId,appCode), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to search system menu", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

    /**
     * Search sys menu response entity.
     *
     * @param userId  the user id
     * @param appCode the app code
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    @RequestMapping(value = "/api/userId/{userId}/appCode/{appCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "[Garnet]查询系统菜单", notes = "Search system menu")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful query"),
            @ApiResponse(code = 500, message = "internal server error")})
    public ResponseEntity<?> searchButton(
            @ApiParam(value = "userId", required = true) @PathVariable(value = "userId") Long userId,
            @ApiParam(value = "appCode", required = true) @PathVariable(value = "appCode") String appCode) {
        try {
            return new ResponseEntity<>(shiroService.getApiPermissionsByUserIdAndAppCode(userId,appCode), HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to search system menu", t);
            return GarnetRsUtil.newResponseEntity(t);
        }
    }

}
