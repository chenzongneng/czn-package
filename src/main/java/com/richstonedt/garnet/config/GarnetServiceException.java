/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.config;

/**
 * <b><code>GarnetServiceException</code></b>
 * <p/>
 * Garnet Service Exception
 * <p/>
 * <b>Creation Time:</b> 2017年10月28日 下午5:24:39
 *
 * @author chenzechao
 * @since garnet 1.0.0
 */
public class GarnetServiceException extends RuntimeException {

    /**
     * Serialization ID
     *
     * @since garnet 1.0.0
     */
    private static final long serialVersionUID = 3894491214688661572L;

    /**
     * Error code
     *
     * @since garnet 1.0.0
     */
    private String errorCode = GarnetServiceErrorCodes.OTHER;

    /**
     * Instantiates a new Garnet service_bk exception.
     *
     * @param message the message
     * @since garnet 1.0.0
     */
    public GarnetServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Garnet service_bk exception.
     *
     * @param message   the message
     * @param errorCode the error code
     * @since garnet 1.0.0
     */
    public GarnetServiceException(String message, String errorCode) {
        super(message);
        setErrorCode(errorCode);
    }

    /**
     * Instantiates a new Garnet service_bk exception.
     *
     * @param message the message
     * @param cause   the cause
     * @since garnet 1.0.0
     */
    public GarnetServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Garnet service_bk exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     * @since garnet 1.0.0
     */
    public GarnetServiceException(String message, Throwable cause,
                                  String errorCode) {
        super(message, cause);
        setErrorCode(errorCode);
    }

    /**
     * Instantiates a new Garnet service_bk exception.
     *
     * @param cause the cause
     * @since garnet 1.0.0
     */
    public GarnetServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Garnet service_bk exception.
     *
     * @param cause     the cause
     * @param errorCode the error code
     * @since garnet 1.0.0
     */
    public GarnetServiceException(Throwable cause, String errorCode) {
        super(cause);
        setErrorCode(errorCode);
    }

    /**
     * Returns the errorCode
     *
     * @return the errorCode
     * @since garnet 1.0.0
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the errorCode
     *
     * @param errorCode the error code
     * @since garnet 1.0.0
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
