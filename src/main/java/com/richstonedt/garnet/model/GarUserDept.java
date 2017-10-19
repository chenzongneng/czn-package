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
 * <b><code>GarUserDept</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 18:12
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户-用户组关联信息")
public class GarUserDept  implements Serializable{

    /**
     * The User id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * The Dept id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    /**
     * Return the UserId
     *
     * @return property value of userId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set the UserId
     *
     * @param userId value to be assigned to property userId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Return the DeptId
     *
     * @return property value of deptId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * Set the DeptId
     *
     * @param deptId value to be assigned to property deptId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
