package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_permissions
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-04-16 11:10
 */
public class Permission implements Serializable {
    private Long id;

    private String resourcePathWildcard;

    private String name;

    private String description;

    private Long createdTime;

    private Long modifiedTime;

    private Long applicationId;

    private Long tenantId;

    private String action;

    private Integer status;

    /**
     * 更新的人
     * gar_permissions.updated_by_user_name
     */
    private String updatedByUserName;

    /**
     * TABLE： gar_permissions
     *
     * @mbg.generated
     *
     * DATE: 2018-04-16 11:10
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourcePathWildcard() {
        return resourcePathWildcard;
    }

    public void setResourcePathWildcard(String resourcePathWildcard) {
        this.resourcePathWildcard = resourcePathWildcard == null ? null : resourcePathWildcard.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
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
     * column：gar_permissions.updated_by_user_name<br>
     * @return updated_by_user_name
     *
     * @mbg.generated
     *
     * DATE: 2018-04-16 11:10
     */
    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    /**
     * 更新的人<br>
     *
     * column：gar_permissions.updated_by_user_name<br>
     * @param updatedByUserName
     *
     * @mbg.generated
     *
     * DATE: 2018-04-16 11:10
     */
    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName == null ? null : updatedByUserName.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-04-16 11:10
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
        Permission other = (Permission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getResourcePathWildcard() == null ? other.getResourcePathWildcard() == null : this.getResourcePathWildcard().equals(other.getResourcePathWildcard()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
                && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
                && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getUpdatedByUserName() == null ? other.getUpdatedByUserName() == null : this.getUpdatedByUserName().equals(other.getUpdatedByUserName()));
    }

    /**
     * <br>
     *
     * TABLE： gar_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-04-16 11:10
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getResourcePathWildcard() == null) ? 0 : getResourcePathWildcard().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getUpdatedByUserName() == null) ? 0 : getUpdatedByUserName().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-04-16 11:10
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resourcePathWildcard=").append(resourcePathWildcard);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", action=").append(action);
        sb.append(", status=").append(status);
        sb.append(", updatedByUserName=").append(updatedByUserName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}