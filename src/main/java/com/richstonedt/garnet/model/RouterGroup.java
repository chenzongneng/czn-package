package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_router_group
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-05-04 10:59
 */
public class RouterGroup implements Serializable {
    private Long id;

    private String groupName;

    private String remark;

    private String appCode;

    private Long createdTime;

    private Long modifiedTime;

    private String updatedByUserName;

    /**
     * TABLE： gar_router_group
     *
     * @mbg.generated
     *
     * DATE: 2018-05-04 10:59
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
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

    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName == null ? null : updatedByUserName.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_router_group<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-05-04 10:59
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
        RouterGroup other = (RouterGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
                && (this.getAppCode() == null ? other.getAppCode() == null : this.getAppCode().equals(other.getAppCode()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getUpdatedByUserName() == null ? other.getUpdatedByUserName() == null : this.getUpdatedByUserName().equals(other.getUpdatedByUserName()));
    }

    /**
     * <br>
     *
     * TABLE： gar_router_group<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-05-04 10:59
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getAppCode() == null) ? 0 : getAppCode().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getUpdatedByUserName() == null) ? 0 : getUpdatedByUserName().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_router_group<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-05-04 10:59
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupName=").append(groupName);
        sb.append(", remark=").append(remark);
        sb.append(", appCode=").append(appCode);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", updatedByUserName=").append(updatedByUserName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}