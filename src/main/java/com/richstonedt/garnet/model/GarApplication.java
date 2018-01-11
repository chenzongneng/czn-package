/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * <b><code>GarApplication</code></b>
 * <p>
 * class_comment sentence
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
    private Long applicationId;

    /**
     * The Name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称")
    private String name;

    /**
     * The Code.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用标识")
    private String code;

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
     * Return the ApplicationId
     *
     * @return property value of applicationId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * Set the ApplicationId
     *
     * @param applicationId value to be assigned to property applicationId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
