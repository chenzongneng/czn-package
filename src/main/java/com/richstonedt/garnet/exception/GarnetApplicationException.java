/*
 * 广州丰石科技公司有限公司拥有本软件版权2016并保留所有权利。
 * Copyright 2016, GuangZhou Rich Stone Data Technologies Company Limited, 
 * All rights reserved.
 */
package com.richstonedt.garnet.exception;

/**
 * <b><code>GarnetApplicationException</code></b>
 * <p/>
 * GarnetApplicationException
 * <p/>
 * <b>Creation Time:</b> 2016/10/31 20:29.
 *
 * @author xiedongmei
 * @version $ {Revision} 2016/10/31
 * @since torinosrc-commons 0.0.1
 */
public class GarnetApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1960405186815260702L;
    /**
     * Serialization ID
     *
     * @since diamond-service-commons 1.0
     */

    private String errorCode;

    /**
     * Instantiates a new Diamond application exception.
     */
    public GarnetApplicationException() {
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param message the message
     */
    public GarnetApplicationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public GarnetApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Diamond application exception.
     *
     * @param cause the cause
     */
    public GarnetApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * Returns the errorCode
     *
     * @return the errorCode
     * @since diamond -service-commons project_version
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the errorCode
     *
     * @param errorCode the errorCode to set
     * @since diamond -service-commons project_version
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
