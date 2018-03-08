/*
 * 广州丰石科技公司有限公司拥有本软件版权2016并保留所有权利。
 * Copyright 2016, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.exception;


import com.richstonedt.garnet.model.message.GarnetErrorResponseMessage;
import com.richstonedt.garnet.model.message.GarnetMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarnetServiceExceptionUtils</code></b>
 * <p/>
 * class_comment
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:27.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-be project_version
 */
public class GarnetServiceExceptionUtils {
    /**
     * Return http status.
     *
     * @param <T> the generic type
     * @param e   the e
     * @return the response entity
     */
    public static <T> ResponseEntity<T> getHttpStatus(Throwable e, Class<T> requiredType) {
        if (e instanceof GarnetServiceException) {
            GarnetServiceException dse = (GarnetServiceException) e;

            if (GarnetServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
            } else if (GarnetServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<T>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<T>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return http status.
     *
     * @param <T> the generic type returned as the type of the list
     * @param e   the e
     * @return the response entity
     */
    public static <T> ResponseEntity<List<T>> getHttpStatusWithList(Throwable e, Class<T> requiredType) {
        if (e instanceof GarnetServiceException) {
            GarnetServiceException dse = (GarnetServiceException) e;

            if (GarnetServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<List<T>>(HttpStatus.NOT_FOUND);
            } else if (GarnetServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<List<T>>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<List<T>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Return http status.
     *
     * @param <T> the generic type returned as the type of the list
     * @param e the e
     * @return the response entity
     */
    public static <K, T> ResponseEntity<Map<K, List<T>>> getHttpStatusWithMap(
            Throwable e, Class<K> requiredType1, Class<T> requiredType2) {
        if (e instanceof GarnetServiceException) {
            GarnetServiceException dse = (GarnetServiceException) e;

            if (GarnetServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<Map<K, List<T>>>(
                        HttpStatus.NOT_FOUND);
            } else if (GarnetServiceException.CONFLICT
                    .equals(dse.getErrorCode())) {
                return new ResponseEntity<Map<K, List<T>>>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<Map<K, List<T>>>(
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Write error log and return http status.
     *
     * @param errorMessage the error message
     * @param e            the e
     * @return the response entity
     */
    public static ResponseEntity<GarnetErrorResponseMessage> getHttpStatusWithResponseMessage(String errorMessage, Throwable e) {
        if (e instanceof GarnetServiceException) {
            GarnetServiceException dse = (GarnetServiceException) e;
            if (GarnetServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                return new ResponseEntity<>(new GarnetErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.NOT_FOUND);
            } else if (GarnetServiceException.CONFLICT.equals(dse.getErrorCode())) {
                return new ResponseEntity<>(new GarnetErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(new GarnetErrorResponseMessage(errorMessage + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new GarnetErrorResponseMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Gets the http status with response message.
     *
     * @param errorMessage the error message
     * @return the http status with response message
     */
    public static ResponseEntity<GarnetErrorResponseMessage> getHttpStatusWithResponseMessage(
            String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<GarnetErrorResponseMessage>(
                new GarnetErrorResponseMessage(errorMessage), httpStatus);
    }

    /**
     * Gets the http status with response message.
     *
     * @param errorMessage the error message
     * @return the http status with response message
     */
    public static ResponseEntity<GarnetMessage<GarnetErrorResponseMessage>> getHttpStatusWithResponseGarnetMessage(
            GarnetMessage<GarnetErrorResponseMessage> errorMessage, Throwable e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof GarnetServiceException) {
            GarnetServiceException dse = (GarnetServiceException) e;
            if (GarnetServiceException.NOT_FOUND.equals(dse.getErrorCode())) {
                status = HttpStatus.NOT_FOUND;
            } else if (GarnetServiceException.CONFLICT.equals(dse.getErrorCode())) {
                status = HttpStatus.CONFLICT;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        return new ResponseEntity<GarnetMessage<GarnetErrorResponseMessage>>(
                errorMessage, status);
    }
}
