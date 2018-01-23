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
 * <b><code>GarRoleDept</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 18:15
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "角色-用户组关联信息")
public class GarRoleDept implements Serializable {

    /**
     * The Role id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * The Dept id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

    /**
     * Return the RoleId
     *
     * @return property value of roleId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * Set the RoleId
     *
     * @param roleId value to be assigned to property roleId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * Return the DeptId
     *
     * @return property value of departmentId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Set the DeptId
     *
     * @param departmentId value to be assigned to property departmentId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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
