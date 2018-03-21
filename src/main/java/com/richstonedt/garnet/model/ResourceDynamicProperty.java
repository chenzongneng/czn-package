package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_resource_dynamic_properties
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-16 14:45
 */
public class ResourceDynamicProperty implements Serializable {
    private Long id;

    private String type;

    private Long applicationId;

    private String filedName;

    private String description;

    /**
     * TABLE： gar_resource_dynamic_properties
     *
     * @mbg.generated
     *
     * DATE: 2018-03-16 14:45
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName == null ? null : filedName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_resource_dynamic_properties<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-16 14:45
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
        ResourceDynamicProperty other = (ResourceDynamicProperty) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getFiledName() == null ? other.getFiledName() == null : this.getFiledName().equals(other.getFiledName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    /**
     * <br>
     *
     * TABLE： gar_resource_dynamic_properties<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-16 14:45
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getFiledName() == null) ? 0 : getFiledName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_resource_dynamic_properties<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-16 14:45
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", filedName=").append(filedName);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}