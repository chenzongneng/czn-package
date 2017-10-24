/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>GarLog</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:41
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "系统LOG信息")
public class GarLog implements Serializable {

    /**
     * The Id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "LogID")
    private Long id;

    /**
     * The User name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * The Operation.
     *
     * @since garnet-core-be-fe 0.1.0
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户操作")
    private String operation;

    /**
     * The Method.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "请求方法")
    private String method;

    /**
     * The Url.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "请求URL")
    private String url;

    /**
     * The Ip.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户ip")
    private String ip;

    /**
     * The Sql.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "执行sql")
    private String sql;

    /**
     * The Created time.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * Return the Id
     *
     * @return property value of id
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the Id
     *
     * @param id value to be assigned to property id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Return the UserName
     *
     * @return property value of userName
     * @since garnet-core-be-fe 0.1.0
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the UserName
     *
     * @param userName value to be assigned to property userName
     * @since garnet-core-be-fe 0.1.0
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
     * Return the CreateTime
     *
     * @return property value of createTime
     * @since gempile-model 0.1.0
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Set the CreateTime
     *
     * @param createTime value to be assigned to property createTime
     * @since gempile-model 0.1.0
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
