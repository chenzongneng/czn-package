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
 * <b><code>GarApplicationTenant</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/11/3 14:54
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "应用-租户关联信息")
public class GarApplicationTenant implements Serializable {

    /**
     * The App id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID")
    private Long applicationId;

    /**
     * The Tenant id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * Return the AppId
     *
     * @return property value of applicationId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * Set the AppId
     *
     * @param applicationId value to be assigned to property applicationId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Return the TenantId
     *
     * @return property value of tenantId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * Set the TenantId
     *
     * @param tenantId value to be assigned to property tenantId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * To String.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
