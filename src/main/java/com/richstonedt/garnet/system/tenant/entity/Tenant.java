/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.tenant.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>Tenant</code></b>
 * <p/>
 * Tenant
 * <p/>
 * <b>Creation Time:</b> 2017/8/22 11:12.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
public class Tenant implements Serializable {

    /**
     * The constant serialVersionUID.
     *
     * @since Garnet 1.0.0
     */
    private static final long serialVersionUID = -5519562337167802216L;

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
     * The Delete flag.
     *
     * @since Garnet 1.0.0
     */
    private boolean deleteFlag;

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
     * Is delete flag boolean.
     *
     * @return the boolean
     * @since Garnet 1.0.0
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets delete flag.
     *
     * @param deleteFlag the delete flag
     * @since Garnet 1.0.0
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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
