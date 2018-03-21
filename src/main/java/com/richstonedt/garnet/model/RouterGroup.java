package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_router_group
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-21 11:54
 */
public class RouterGroup implements Serializable {
    private Long id;

    private String groupName;

    private String appCode;

    /**
     * TABLE： gar_router_group
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_router_group<br>
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
        RouterGroup other = (RouterGroup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getAppCode() == null ? other.getAppCode() == null : this.getAppCode().equals(other.getAppCode()));
    }

    /**
     * <br>
     *
     * TABLE： gar_router_group<br>
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
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getAppCode() == null) ? 0 : getAppCode().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_router_group<br>
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
        sb.append(", groupName=").append(groupName);
        sb.append(", appCode=").append(appCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}