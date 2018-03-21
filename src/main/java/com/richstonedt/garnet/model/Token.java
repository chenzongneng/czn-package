package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_token
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-21 11:54
 */
public class Token implements Serializable {
    private Long id;

    private String token;

    private String routerGroupName;

    private Long createdTime;

    private Long modifiedTime;

    private Long expiredTime;

    /**
     * TABLE： gar_token
     *
     * @mbg.generated
     *
     * DATE: 2018-03-21 11:54
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getRouterGroupName() {
        return routerGroupName;
    }

    public void setRouterGroupName(String routerGroupName) {
        this.routerGroupName = routerGroupName == null ? null : routerGroupName.trim();
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

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * <br>
     *
     * TABLE： gar_token<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-21 11:54
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
        Token other = (Token) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
                && (this.getRouterGroupName() == null ? other.getRouterGroupName() == null : this.getRouterGroupName().equals(other.getRouterGroupName()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getExpiredTime() == null ? other.getExpiredTime() == null : this.getExpiredTime().equals(other.getExpiredTime()));
    }

    /**
     * <br>
     *
     * TABLE： gar_token<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-21 11:54
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getRouterGroupName() == null) ? 0 : getRouterGroupName().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getExpiredTime() == null) ? 0 : getExpiredTime().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_token<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-21 11:54
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", token=").append(token);
        sb.append(", routerGroupName=").append(routerGroupName);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", expiredTime=").append(expiredTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}