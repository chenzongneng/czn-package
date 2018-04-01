package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_user_tenant_applications
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-30 13:23
 */
public class UserTenantApplication implements Serializable {
    private Long id;

    private Long userId;

    private Long tenantId;

    private Long applicationId;

    private Integer status;

    /**
     * TABLE： gar_user_tenant_applications
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * <br>
     *
     * TABLE： gar_user_tenant_applications<br>
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
        UserTenantApplication other = (UserTenantApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    /**
     * <br>
     *
     * TABLE： gar_user_tenant_applications<br>
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
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_user_tenant_applications<br>
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
        sb.append(", userId=").append(userId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}