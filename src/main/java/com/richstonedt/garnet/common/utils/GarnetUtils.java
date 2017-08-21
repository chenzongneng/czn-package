/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import com.richstonedt.garnet.common.exception.GarnetServiceErrorCodes;
import com.richstonedt.garnet.common.exception.GarnetServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * <b><code>GarnetUtils</code></b>
 * <p/>
 * Garnet Restful Service Utils
 * <p/>
 * <b>Creation Time:</b> 2016年5月30日 上午10:56:08
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
public class GarnetUtils {

    /**
     * 返回带异常信息的ResponseEntity
     *
     * @param t the t
     * @return the response entity
     * @since Garnet 1.0.0
     */
    public static ResponseEntity<Map<String, Object>> newResponseEntity(Throwable t) {
        Map<String, Object> errorMap = new HashMap<>();
        if (t instanceof GarnetServiceException) {
            GarnetServiceException e = (GarnetServiceException) t;
            if (!StringUtils.isEmpty(e.getErrorCode())) {
                errorMap.put("errorCode", e.getErrorCode());
            } else {
                errorMap.put("errorCode", GarnetServiceErrorCodes.OTHER);
            }
        } else if (t instanceof IllegalArgumentException) {
            errorMap.put("errorCode", GarnetServiceErrorCodes.ILLEGAL_ARGUMENT);
        } else {
            errorMap.put("errorCode", GarnetServiceErrorCodes.OTHER);
        }
        errorMap.put("errorMessage", t.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
