package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_logs
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-05-31 11:08
 */
public class Log implements Serializable {
    private Long id;

    private Long createdTime;

    private Long modifiedTime;

    private String userName;

    private String message;

    private Long tenantId;

    private Long applicationId;

    private String ip;

    private String operation;

    /**
     * TABLE： gar_logs
     *
     * @mbg.generated
     *
     * DATE: 2018-05-31 11:08
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_logs<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-05-31 11:08
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
        Log other = (Log) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getOperation() == null ? other.getOperation() == null : this.getOperation().equals(other.getOperation()));
    }

    /**
     * <br>
     *
     * TABLE： gar_logs<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-05-31 11:08
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getOperation() == null) ? 0 : getOperation().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_logs<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-05-31 11:08
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", userName=").append(userName);
        sb.append(", message=").append(message);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", ip=").append(ip);
        sb.append(", operation=").append(operation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}