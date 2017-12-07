/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>GarAuthority</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:32
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "权限")
public class GarAuthority implements Serializable {

    @ApiModelProperty(value = "权限ID")
    private Long authorityId;

    @ApiModelProperty(value = "具体名称")
    private String name;

    @ApiModelProperty(value = "详细说明")
    private String description;

    @ApiModelProperty(value = "状态")
    private Integer status;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GarAuthority)) {
            return false;
        }

        GarAuthority that = (GarAuthority) o;

        return (getAuthorityId() != null ? getAuthorityId().equals(that.getAuthorityId()) : that.getAuthorityId() == null) && (getName() != null ? getName().equals(that.getName()) : that.getName() == null) && (getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null) && (getStatus() != null ? getStatus().equals(that.getStatus()) : that.getStatus() == null);
    }

    @Override
    public int hashCode() {
        int result = getAuthorityId() != null ? getAuthorityId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}
