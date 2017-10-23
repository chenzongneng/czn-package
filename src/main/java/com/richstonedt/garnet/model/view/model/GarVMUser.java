/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>GarLoginResult</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 14:44
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "页面用户信息")
public class GarVMUser extends GarUser implements Serializable {

    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
