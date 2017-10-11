/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>LogEntity</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/29 14:55
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public class LogEntity implements Serializable {
    /**
     * The serialVersionUID.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private static final long serialVersionUID = -7553275716211815931L;

    /**
     * The Id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private Integer id;

    /**
     * The User name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String userName;

    /**
     * The Operation.
     *
     * @since garnet-core-be-fe 0.1.0
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String operation;

    /**
     * The Method.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String method;

    /**
     * The Url.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String url;

    /**
     * The Ip.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String ip;

    /**
     * The Sql.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private String sql;

    /**
     * The Created time.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private Date createdTime;

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
     * Return the UserName
     *
     * @return property value of userName
     * @since gempile-model 0.1.0
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the UserName
     *
     * @param userName value to be assigned to property userName
     * @since gempile-model 0.1.0
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * Return the Ip
     *
     * @return property value of ip
     * @since garnet-core-be-fe 0.1.0
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the Ip
     *
     * @param ip value to be assigned to property ip
     * @since garnet-core-be-fe 0.1.0
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Return the Sql
     *
     * @return property value of sql
     * @since garnet-core-be-fe 0.1.0
     */
    public String getSql() {
        return sql;
    }

    /**
     * Set the Sql
     *
     * @param sql value to be assigned to property sql
     * @since garnet-core-be-fe 0.1.0
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * Return the CreatedTime
     *
     * @return property value of createdTime
     * @since garnet-core-be-fe 0.1.0
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * Set the CreatedTime
     *
     * @param createdTime value to be assigned to property createdTime
     * @since garnet-core-be-fe 0.1.0
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
