/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.department.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>Department</code></b>
 * <p/>
 * Department
 * <p/>
 * <b>Creation Time:</b> 2017/8/21 14:51.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
public class Department implements Serializable {

    /**
     * The constant serialVersionUID.
     *
     * @since Garnet 1.0.0
     */
    private static final long serialVersionUID = -4730787988637151882L;


    /**
     * The Id.
     *
     * @since Garnet 1.0.0
     */
    private Long id;

    /**
     * The Name.
     *
     * @since Garnet 1.0.0
     */
    private String name;

    /**
     * The Parent id.
     *
     * @since Garnet 1.0.0
     */
    private Long parentId;

    /**
     * The Parent name.
     *
     * @since Garnet 1.0.0
     */
    private String parentName;

    /**
     * The Tenant id.
     *
     * @since Garnet 1.0.0
     */
    private Long tenantId;

    /**
     * The Order num.
     *
     * @since Garnet 1.0.0
     */
    private Integer orderNum;

    /**
     * The Delete flag.
     *
     * @since Garnet 1.0.0
     */
    private Boolean deleteFlag;

    /**
     * Gets id.
     *
     * @return the id
     * @since Garnet 1.0.0
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @since Garnet 1.0.0
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     * @since Garnet 1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @since Garnet 1.0.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets parent id.
     *
     * @return the parent id
     * @since Garnet 1.0.0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     * @since Garnet 1.0.0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets order num.
     *
     * @return the order num
     * @since Garnet 1.0.0
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * Sets order num.
     *
     * @param orderNum the order num
     * @since Garnet 1.0.0
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * Gets delete flag.
     *
     * @return the delete flag
     * @since Garnet 1.0.0
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets delete flag.
     *
     * @param deleteFlag the delete flag
     * @since Garnet 1.0.0
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * Gets parent name.
     *
     * @return the parent name
     * @since Garnet 1.0.0
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets parent name.
     *
     * @param parentName the parent name
     * @since Garnet 1.0.0
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Gets tenant id.
     *
     * @return the tenant id
     * @since Garnet 1.0.0
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * Sets tenant id.
     *
     * @param tenantId the tenant id
     * @since Garnet 1.0.0
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * To string string.
     *
     * @return the string
     * @since Garnet 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
