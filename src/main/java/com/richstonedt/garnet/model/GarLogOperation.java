/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>GarLogOperation</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:46
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "LOG操作信息")
public class GarLogOperation implements Serializable {

    /**
     * The Id.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "操作ID")
    private Integer id;

    /**
     * The Url.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "url")
    private String url;

    /**
     * The Method.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

    /**
     * The Operation.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "操作")
    private String operation;

    /**
     * Return the Id
     *
     * @return property value of id
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the Id
     *
     * @param id value to be assigned to property id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Return the Url
     *
     * @return property value of url
     * @since garnet-core-be-fe 0.1.0
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the Url
     *
     * @param url value to be assigned to property url
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return the Method
     *
     * @return property value of method
     * @since garnet-core-be-fe 0.1.0
     */
    public String getMethod() {
        return method;
    }

    /**
     * Set the Method
     *
     * @param method value to be assigned to property method
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Return the Operation
     *
     * @return property value of operation
     * @since garnet-core-be-fe 0.1.0
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Set the Operation
     *
     * @param operation value to be assigned to property operation
     * @since garnet-core-be-fe 0.1.0
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * To String
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
