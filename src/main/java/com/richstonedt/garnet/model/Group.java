package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_groups
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-30 13:23
 */
public class Group implements Serializable {
    private Long id;

    private String name;

    private Long createdTime;

    private Long modifiedTime;

    private Long applicationId;

    private Long tenantId;

    private Integer status;

    /**
     * 更新的人
     * gar_groups.updated_by_user_name
     */
    private String updatedByUserName;

    /**
     * TABLE： gar_groups
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 更新的人<br>
     *
     * column：gar_groups.updated_by_user_name<br>
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
     * column：gar_groups.updated_by_user_name<br>
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
     * TABLE： gar_groups<br>
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
        Group other = (Group) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getUpdatedByUserName() == null ? other.getUpdatedByUserName() == null : this.getUpdatedByUserName().equals(other.getUpdatedByUserName()));
    }

    /**
     * <br>
     *
     * TABLE： gar_groups<br>
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
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUpdatedByUserName() == null) ? 0 : getUpdatedByUserName().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_groups<br>
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
        sb.append(", applicationId=").append(applicationId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", status=").append(status);
        sb.append(", updatedByUserName=").append(updatedByUserName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}