/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>GemOperation</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/25 18:34
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since gempile-model 1.0.0
 */
public class OperationEntity implements Serializable{

    /**
     * serialVersionUID
     *
     * @since gempile-model 1.0.0
     */
    private static final long serialVersionUID = 5832260761801443960L;

    /**
     * The Id.
     * 
     * @since gempile-model 1.0.0
     */
    private Integer id;

    /**
     * The Url.
     * 
     * @since gempile-model 1.0.0
     */
    private String url;

    /**
     * The Method.
     * 
     * @since gempile-model 1.0.0
     */
    private String method;

    /**
     * The Operation.
     * 
     * @since gempile-model 1.0.0
     */
    private String operation;

    /**
     * Return the Id
     *
     * @return property value of id
     * @since gempile-model 1.0.0
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the Id
     *
     * @param id value to be assigned to property id
     * @since gempile-model 1.0.0
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Return the Url
     *
     * @return property value of url
     * @since gempile-model 1.0.0
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the Url
     *
     * @param url value to be assigned to property url
     * @since gempile-model 1.0.0
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return the Method
     *
     * @return property value of method
     * @since gempile-model 1.0.0
     */
    public String getMethod() {
        return method;
    }

    /**
     * Set the Method
     *
     * @param method value to be assigned to property method
     * @since gempile-model 1.0.0
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Return the Operation
     *
     * @return property value of operation
     * @since gempile-model 1.0.0
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Set the Operation
     *
     * @param operation value to be assigned to property operation
     * @since gempile-model 1.0.0
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * To String
     *
     * @since gempile-model 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
