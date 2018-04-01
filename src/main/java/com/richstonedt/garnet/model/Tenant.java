package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_tenants
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-30 13:23
 */
public class Tenant implements Serializable {
    private Long id;

    private String name;

    private Long createdTime;

    private Long modifiedTime;

    private String description;

    private Integer status;

    /**
     * 属于paas服务还是saas服务
     * gar_tenants.service_mode
     */
    private String serviceMode;

    /**
     * 更新的人
     * gar_tenants.updated_by_user_name
     */
    private String updatedByUserName;

    /**
     * TABLE： gar_tenants
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 属于paas服务还是saas服务<br>
     *
     * column：gar_tenants.service_mode<br>
     * @return service_mode
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public String getServiceMode() {
        return serviceMode;
    }

    /**
     * 属于paas服务还是saas服务<br>
     *
     * column：gar_tenants.service_mode<br>
     * @param serviceMode
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode == null ? null : serviceMode.trim();
    }

    /**
     * 更新的人<br>
     *
     * column：gar_tenants.updated_by_user_name<br>
     * @return updated_by_user_name
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    /**
     * 更新的人<br>
     *
     * column：gar_tenants.updated_by_user_name<br>
     * @param updatedByUserName
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName == null ? null : updatedByUserName.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_tenants<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Tenant other = (Tenant) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getServiceMode() == null ? other.getServiceMode() == null : this.getServiceMode().equals(other.getServiceMode()))
            && (this.getUpdatedByUserName() == null ? other.getUpdatedByUserName() == null : this.getUpdatedByUserName().equals(other.getUpdatedByUserName()));
    }

    /**
     * <br>
     *
     * TABLE： gar_tenants<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getServiceMode() == null) ? 0 : getServiceMode().hashCode());
        result = prime * result + ((getUpdatedByUserName() == null) ? 0 : getUpdatedByUserName().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_tenants<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", serviceMode=").append(serviceMode);
        sb.append(", updatedByUserName=").append(updatedByUserName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}