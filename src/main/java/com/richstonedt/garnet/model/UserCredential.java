package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_user_credentials
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-02-25 14:02
 */
public class UserCredential implements Serializable {
    private Long id;

    private Long createdTime;

    private Long modifiedTime;

    private String credential;

    private Long expiredDateTime;

    private Long userId;

    /**
     * TABLE： gar_user_credentials
     *
     * @mbg.generated
     *
     * DATE: 2018-02-25 14:02
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

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential == null ? null : credential.trim();
    }

    public Long getExpiredDateTime() {
        return expiredDateTime;
    }

    public void setExpiredDateTime(Long expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * <br>
     *
     * TABLE： gar_user_credentials<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-02-25 14:02
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
        UserCredential other = (UserCredential) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
            && (this.getCredential() == null ? other.getCredential() == null : this.getCredential().equals(other.getCredential()))
            && (this.getExpiredDateTime() == null ? other.getExpiredDateTime() == null : this.getExpiredDateTime().equals(other.getExpiredDateTime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    /**
     * <br>
     *
     * TABLE： gar_user_credentials<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-02-25 14:02
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getCredential() == null) ? 0 : getCredential().hashCode());
        result = prime * result + ((getExpiredDateTime() == null) ? 0 : getExpiredDateTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_user_credentials<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-02-25 14:02
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
        sb.append(", credential=").append(credential);
        sb.append(", expiredDateTime=").append(expiredDateTime);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}