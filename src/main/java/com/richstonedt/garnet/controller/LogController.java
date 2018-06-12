package com.richstonedt.garnet.controller;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.exception.GarnetServiceExceptionUtils;
import com.richstonedt.garnet.interceptory.LoginRequired;
import com.richstonedt.garnet.model.Log;
import com.richstonedt.garnet.model.message.*;
import com.richstonedt.garnet.model.parm.LogParm;
import com.richstonedt.garnet.model.view.LogView;
import com.richstonedt.garnet.service.LogService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * <b><code>LogController</code></b>
 * <p/>
 * Log的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu May 31 14:42:54 CST 2018.
 *
 * @author maxuepeng
 * @version 1.0.0
 * @since torinosrc-rs 1.0.0
 */
@Api(value = "[Garnet]系统日志接口")
@LoginRequired
@RestController
@RequestMapping(value = "/api/v1.0")
public class LogController {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(LogController.class);

    /** The service. */
    @Autowired
    private LogService logService;

    @ApiOperation(value = "[Garnet]删除系统日志", notes = "通过id删除系统日志")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/logs/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteLog(
            @ApiParam(value = "系统日志id", required = true) @PathVariable(value = "id") Long id) {
        try {
            logService.deleteByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<LogView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entity! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]删除系统日志", notes = "批量删除系统日志")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/logs", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deleteLogs(
            @ApiParam(value = "系统日志ids，样例 - 1,2,3", required = true) @RequestBody String ids) {
        try {
            for (String id : ids.split(",")) {
                logService.deleteByPrimaryKey(Long.parseLong(id));
            }
            // 封装返回信息
            GarnetMessage<LogView> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_DELETE_SUCCESS, null);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to delete entities! " + MessageDescription.OPERATION_DELETE_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取单个系统日志", notes = "通过id获取系统日志")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/logs/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getLog(
            @ApiParam(value = "系统日志id", required = true) @PathVariable(value = "id") Long id) {
        try {
            final Log log = logService.selectByPrimaryKey(id);
            // 封装返回信息
            GarnetMessage<Log> torinoSrcMessage = MessageUtils.setMessage(MessageCode.SUCCESS, MessageStatus.SUCCESS, MessageDescription.OPERATION_QUERY_SUCCESS, log);
            return new ResponseEntity<>(torinoSrcMessage, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entity!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

    @ApiOperation(value = "[Garnet]获取系统日志列表", notes = "通过查询条件获取系统日志列表")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful request"),
            @ApiResponse(code = 500, message = "internal server error") })
    @RequestMapping(value = "/logs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getLogs(
            @ApiParam(value = "查询条件", defaultValue = "", required = false) @RequestParam(value = "searchName", defaultValue = "", required = false) String searchName,
            @ApiParam(value = "页数", defaultValue = "0", required = false) @RequestParam(value = "page", defaultValue = "0", required = false) Integer pageNumber,
            @ApiParam(value = "每页加载量", defaultValue = "10", required = false) @RequestParam(value = "limit", defaultValue = "10", required = false) Integer pageSize) {
        try {
            LogParm logParm = new LogParm();
            logParm.setPageSize(pageSize);
            logParm.setPageNumber(pageNumber);
            logParm.setSearchName(searchName);
            PageUtil pageInfo = logService.queryLogsByParms(logParm);
            // 封装返回信息
            return new ResponseEntity<>(pageInfo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to get entities!" + MessageDescription.OPERATION_QUERY_FAILURE;
            LOG.error(error, t);
            GarnetMessage<GarnetErrorResponseMessage> torinoSrcMessage = MessageUtils.setMessage(MessageCode.FAILURE, MessageStatus.ERROR, error, new GarnetErrorResponseMessage(t.toString()));
            return GarnetServiceExceptionUtils.getHttpStatusWithResponseGarnetMessage(torinoSrcMessage, t);
        }
    }

}
