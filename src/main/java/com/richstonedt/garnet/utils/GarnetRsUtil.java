/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.utils;

import com.richstonedt.garnet.config.GarnetServiceErrorCodes;
import com.richstonedt.garnet.config.GarnetServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarnetRsUtil</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 11:26
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public class GarnetRsUtil {

    /**
     * New response entity response entity.
     *
     * @param t the t
     * @return the response entity
     * @since garnet-core-be-fe 0.1.0
     */
    public static ResponseEntity<Map<String, Object>> newResponseEntity(
            Throwable t) {
        Map<String, Object> errorMap = new HashMap<>(8);
        if (t instanceof GarnetServiceException) {
            GarnetServiceException e = (GarnetServiceException) t;
            if (!StringUtils.isEmpty(e.getErrorCode())) {
                errorMap.put("errorCode", e.getErrorCode());
            } else {
                errorMap.put("errorCode", GarnetServiceErrorCodes.OTHER);
            }
        } else {
            errorMap.put("errorCode", GarnetServiceErrorCodes.OTHER);
        }
        errorMap.put("errorMessage", t.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get role list list.
     *
     * @param ids the ids
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    public static List<Integer> parseStringToList(String ids) {
        List<Integer> idList = new ArrayList<>();
        try {
            String comma = ",";
            if (ids.contains(comma)) {
                String[] tmpIds = ids.split(",");
                for (String id : tmpIds) {
                    idList.add(Integer.parseInt(id));
                }
            } else {
                idList.add(Integer.parseInt(ids));
            }
        } catch (NumberFormatException e) {
            String errorMessage = "The parameter is error,Please input number! ids = " + ids;
            throw new GarnetServiceException(errorMessage);
        }
        return idList;
    }
}
