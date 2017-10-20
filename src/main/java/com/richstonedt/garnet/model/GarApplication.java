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
 * <b><code>GarApplication</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:29
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "应用信息")
public class GarApplication implements Serializable {

    /**
     * The App id.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID")
    private Long appId;

    /**
     * The Name.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称")
    private String name;

    /**
     * The Company.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "所属公司")
    private String company;

    /**
     * The Remark.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * The Create time.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * Return the AppId
     *
     * @return property value of appId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * Set the AppId
     *
     * @param appId value to be assigned to property appId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppId(Long appId) {
        this.appId = appId;
    }

    /**
     * Return the Name
     *
     * @return property value of name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name
     *
     * @param name value to be assigned to property name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the Company
     *
     * @return property value of company
     * @since garnet-core-be-fe 0.1.0
     */
    public String getCompany() {
        return company;
    }

    /**
     * Set the Company
     *
     * @param company value to be assigned to property company
     * @since garnet-core-be-fe 0.1.0
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Return the Remark
     *
     * @return property value of remark
     * @since garnet-core-be-fe 0.1.0
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Set the Remark
     *
     * @param remark value to be assigned to property remark
     * @since garnet-core-be-fe 0.1.0
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Return the CreateTime
     *
     * @return property value of createTime
     * @since garnet-core-be-fe 0.1.0
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Set the CreateTime
     *
     * @param createTime value to be assigned to property createTime
     * @since garnet-core-be-fe 0.1.0
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
