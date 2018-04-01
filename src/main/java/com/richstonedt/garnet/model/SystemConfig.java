package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_system_config
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-30 13:28
 */
public class SystemConfig implements Serializable {
    private Long id;

    private String parameter;

    private String value;

    /**
     * TABLE： gar_system_config
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:28
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_system_config<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:28
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
        SystemConfig other = (SystemConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParameter() == null ? other.getParameter() == null : this.getParameter().equals(other.getParameter()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
    }

    /**
     * <br>
     *
     * TABLE： gar_system_config<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:28
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParameter() == null) ? 0 : getParameter().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_system_config<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:28
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parameter=").append(parameter);
        sb.append(", value=").append(value);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}