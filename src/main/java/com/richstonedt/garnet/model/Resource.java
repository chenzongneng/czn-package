package com.richstonedt.garnet.model;

import com.richstonedt.garnet.common.utils.ExcelVOAttribute;

import java.io.Serializable;

/**
 *
 * TABLE:gar_resources
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-06-19 10:11
 */
public class Resource implements Serializable {
    private Long id;

    private Long applicationId;

    @ExcelVOAttribute(name = "", column = "B")
    private String path;

    private String actions;

    @ExcelVOAttribute(name = "", column = "A")
    private String name;

    private Long createdTime;

    private Long modifiedTime;

    @ExcelVOAttribute(name = "", column = "D")
    private String type;

    private Long tenantId;

    @ExcelVOAttribute(name = "", column = "F")
    private String varchar00;

    @ExcelVOAttribute(name = "", column = "G")
    private String varchar01;

    @ExcelVOAttribute(name = "", column = "H")
    private String varchar02;

    @ExcelVOAttribute(name = "", column = "I")
    private String varchar03;

    @ExcelVOAttribute(name = "", column = "J")
    private String varchar04;

    @ExcelVOAttribute(name = "", column = "K")
    private String varchar05;

    @ExcelVOAttribute(name = "", column = "L")
    private String varchar06;

    @ExcelVOAttribute(name = "", column = "M")
    private String varchar07;

    @ExcelVOAttribute(name = "", column = "N")
    private String varchar08;

    @ExcelVOAttribute(name = "", column = "O")
    private String varchar09;

    @ExcelVOAttribute(name = "", column = "P")
    private String varchar10;

    @ExcelVOAttribute(name = "", column = "Q")
    private String varchar11;

    @ExcelVOAttribute(name = "", column = "R")
    private String varchar12;

    @ExcelVOAttribute(name = "", column = "S")
    private String varchar13;

    @ExcelVOAttribute(name = "", column = "T")
    private String varchar14;

    @ExcelVOAttribute(name = "", column = "U")
    private String varchar15;

    @ExcelVOAttribute(name = "", column = "V")
    private String varchar16;

    @ExcelVOAttribute(name = "", column = "W")
    private String varchar17;

    @ExcelVOAttribute(name = "", column = "X")
    private String varchar18;

    @ExcelVOAttribute(name = "", column = "Y")
    private String varchar19;

    @ExcelVOAttribute(name = "", column = "Z")
    private Integer int01;

    @ExcelVOAttribute(name = "", column = "AA")
    private Integer int02;

    @ExcelVOAttribute(name = "", column = "AB")
    private Integer int03;

    @ExcelVOAttribute(name = "", column = "AC")
    private Integer int04;

    @ExcelVOAttribute(name = "", column = "AD")
    private Integer int05;

    @ExcelVOAttribute(name = "", column = "AE")
    private Boolean boolean01;

    @ExcelVOAttribute(name = "", column = "AF")
    private Boolean boolean02;

    @ExcelVOAttribute(name = "", column = "AG")
    private Boolean boolean03;

    @ExcelVOAttribute(name = "", column = "AH")
    private Boolean boolean04;

    private String updatedByUserName;

    @ExcelVOAttribute(name = "", column = "C")
    private String remark;

    /**
     * TABLE： gar_resources
     *
     * @mbg.generated
     *
     * DATE: 2018-06-19 10:11
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions == null ? null : actions.trim();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getVarchar00() {
        return varchar00;
    }

    public void setVarchar00(String varchar00) {
        this.varchar00 = varchar00 == null ? null : varchar00.trim();
    }

    public String getVarchar01() {
        return varchar01;
    }

    public void setVarchar01(String varchar01) {
        this.varchar01 = varchar01 == null ? null : varchar01.trim();
    }

    public String getVarchar02() {
        return varchar02;
    }

    public void setVarchar02(String varchar02) {
        this.varchar02 = varchar02 == null ? null : varchar02.trim();
    }

    public String getVarchar03() {
        return varchar03;
    }

    public void setVarchar03(String varchar03) {
        this.varchar03 = varchar03 == null ? null : varchar03.trim();
    }

    public String getVarchar04() {
        return varchar04;
    }

    public void setVarchar04(String varchar04) {
        this.varchar04 = varchar04 == null ? null : varchar04.trim();
    }

    public String getVarchar05() {
        return varchar05;
    }

    public void setVarchar05(String varchar05) {
        this.varchar05 = varchar05 == null ? null : varchar05.trim();
    }

    public String getVarchar06() {
        return varchar06;
    }

    public void setVarchar06(String varchar06) {
        this.varchar06 = varchar06 == null ? null : varchar06.trim();
    }

    public String getVarchar07() {
        return varchar07;
    }

    public void setVarchar07(String varchar07) {
        this.varchar07 = varchar07 == null ? null : varchar07.trim();
    }

    public String getVarchar08() {
        return varchar08;
    }

    public void setVarchar08(String varchar08) {
        this.varchar08 = varchar08 == null ? null : varchar08.trim();
    }

    public String getVarchar09() {
        return varchar09;
    }

    public void setVarchar09(String varchar09) {
        this.varchar09 = varchar09 == null ? null : varchar09.trim();
    }

    public String getVarchar10() {
        return varchar10;
    }

    public void setVarchar10(String varchar10) {
        this.varchar10 = varchar10 == null ? null : varchar10.trim();
    }

    public String getVarchar11() {
        return varchar11;
    }

    public void setVarchar11(String varchar11) {
        this.varchar11 = varchar11 == null ? null : varchar11.trim();
    }

    public String getVarchar12() {
        return varchar12;
    }

    public void setVarchar12(String varchar12) {
        this.varchar12 = varchar12 == null ? null : varchar12.trim();
    }

    public String getVarchar13() {
        return varchar13;
    }

    public void setVarchar13(String varchar13) {
        this.varchar13 = varchar13 == null ? null : varchar13.trim();
    }

    public String getVarchar14() {
        return varchar14;
    }

    public void setVarchar14(String varchar14) {
        this.varchar14 = varchar14 == null ? null : varchar14.trim();
    }

    public String getVarchar15() {
        return varchar15;
    }

    public void setVarchar15(String varchar15) {
        this.varchar15 = varchar15 == null ? null : varchar15.trim();
    }

    public String getVarchar16() {
        return varchar16;
    }

    public void setVarchar16(String varchar16) {
        this.varchar16 = varchar16 == null ? null : varchar16.trim();
    }

    public String getVarchar17() {
        return varchar17;
    }

    public void setVarchar17(String varchar17) {
        this.varchar17 = varchar17 == null ? null : varchar17.trim();
    }

    public String getVarchar18() {
        return varchar18;
    }

    public void setVarchar18(String varchar18) {
        this.varchar18 = varchar18 == null ? null : varchar18.trim();
    }

    public String getVarchar19() {
        return varchar19;
    }

    public void setVarchar19(String varchar19) {
        this.varchar19 = varchar19 == null ? null : varchar19.trim();
    }

    public Integer getInt01() {
        return int01;
    }

    public void setInt01(Integer int01) {
        this.int01 = int01;
    }

    public Integer getInt02() {
        return int02;
    }

    public void setInt02(Integer int02) {
        this.int02 = int02;
    }

    public Integer getInt03() {
        return int03;
    }

    public void setInt03(Integer int03) {
        this.int03 = int03;
    }

    public Integer getInt04() {
        return int04;
    }

    public void setInt04(Integer int04) {
        this.int04 = int04;
    }

    public Integer getInt05() {
        return int05;
    }

    public void setInt05(Integer int05) {
        this.int05 = int05;
    }

    public Boolean getBoolean01() {
        return boolean01;
    }

    public void setBoolean01(Boolean boolean01) {
        this.boolean01 = boolean01;
    }

    public Boolean getBoolean02() {
        return boolean02;
    }

    public void setBoolean02(Boolean boolean02) {
        this.boolean02 = boolean02;
    }

    public Boolean getBoolean03() {
        return boolean03;
    }

    public void setBoolean03(Boolean boolean03) {
        this.boolean03 = boolean03;
    }

    public Boolean getBoolean04() {
        return boolean04;
    }

    public void setBoolean04(Boolean boolean04) {
        this.boolean04 = boolean04;
    }

    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName == null ? null : updatedByUserName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-19 10:11
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
        Resource other = (Resource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
                && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
                && (this.getActions() == null ? other.getActions() == null : this.getActions().equals(other.getActions()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
                && (this.getVarchar00() == null ? other.getVarchar00() == null : this.getVarchar00().equals(other.getVarchar00()))
                && (this.getVarchar01() == null ? other.getVarchar01() == null : this.getVarchar01().equals(other.getVarchar01()))
                && (this.getVarchar02() == null ? other.getVarchar02() == null : this.getVarchar02().equals(other.getVarchar02()))
                && (this.getVarchar03() == null ? other.getVarchar03() == null : this.getVarchar03().equals(other.getVarchar03()))
                && (this.getVarchar04() == null ? other.getVarchar04() == null : this.getVarchar04().equals(other.getVarchar04()))
                && (this.getVarchar05() == null ? other.getVarchar05() == null : this.getVarchar05().equals(other.getVarchar05()))
                && (this.getVarchar06() == null ? other.getVarchar06() == null : this.getVarchar06().equals(other.getVarchar06()))
                && (this.getVarchar07() == null ? other.getVarchar07() == null : this.getVarchar07().equals(other.getVarchar07()))
                && (this.getVarchar08() == null ? other.getVarchar08() == null : this.getVarchar08().equals(other.getVarchar08()))
                && (this.getVarchar09() == null ? other.getVarchar09() == null : this.getVarchar09().equals(other.getVarchar09()))
                && (this.getVarchar10() == null ? other.getVarchar10() == null : this.getVarchar10().equals(other.getVarchar10()))
                && (this.getVarchar11() == null ? other.getVarchar11() == null : this.getVarchar11().equals(other.getVarchar11()))
                && (this.getVarchar12() == null ? other.getVarchar12() == null : this.getVarchar12().equals(other.getVarchar12()))
                && (this.getVarchar13() == null ? other.getVarchar13() == null : this.getVarchar13().equals(other.getVarchar13()))
                && (this.getVarchar14() == null ? other.getVarchar14() == null : this.getVarchar14().equals(other.getVarchar14()))
                && (this.getVarchar15() == null ? other.getVarchar15() == null : this.getVarchar15().equals(other.getVarchar15()))
                && (this.getVarchar16() == null ? other.getVarchar16() == null : this.getVarchar16().equals(other.getVarchar16()))
                && (this.getVarchar17() == null ? other.getVarchar17() == null : this.getVarchar17().equals(other.getVarchar17()))
                && (this.getVarchar18() == null ? other.getVarchar18() == null : this.getVarchar18().equals(other.getVarchar18()))
                && (this.getVarchar19() == null ? other.getVarchar19() == null : this.getVarchar19().equals(other.getVarchar19()))
                && (this.getInt01() == null ? other.getInt01() == null : this.getInt01().equals(other.getInt01()))
                && (this.getInt02() == null ? other.getInt02() == null : this.getInt02().equals(other.getInt02()))
                && (this.getInt03() == null ? other.getInt03() == null : this.getInt03().equals(other.getInt03()))
                && (this.getInt04() == null ? other.getInt04() == null : this.getInt04().equals(other.getInt04()))
                && (this.getInt05() == null ? other.getInt05() == null : this.getInt05().equals(other.getInt05()))
                && (this.getBoolean01() == null ? other.getBoolean01() == null : this.getBoolean01().equals(other.getBoolean01()))
                && (this.getBoolean02() == null ? other.getBoolean02() == null : this.getBoolean02().equals(other.getBoolean02()))
                && (this.getBoolean03() == null ? other.getBoolean03() == null : this.getBoolean03().equals(other.getBoolean03()))
                && (this.getBoolean04() == null ? other.getBoolean04() == null : this.getBoolean04().equals(other.getBoolean04()))
                && (this.getUpdatedByUserName() == null ? other.getUpdatedByUserName() == null : this.getUpdatedByUserName().equals(other.getUpdatedByUserName()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-19 10:11
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getActions() == null) ? 0 : getActions().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getVarchar00() == null) ? 0 : getVarchar00().hashCode());
        result = prime * result + ((getVarchar01() == null) ? 0 : getVarchar01().hashCode());
        result = prime * result + ((getVarchar02() == null) ? 0 : getVarchar02().hashCode());
        result = prime * result + ((getVarchar03() == null) ? 0 : getVarchar03().hashCode());
        result = prime * result + ((getVarchar04() == null) ? 0 : getVarchar04().hashCode());
        result = prime * result + ((getVarchar05() == null) ? 0 : getVarchar05().hashCode());
        result = prime * result + ((getVarchar06() == null) ? 0 : getVarchar06().hashCode());
        result = prime * result + ((getVarchar07() == null) ? 0 : getVarchar07().hashCode());
        result = prime * result + ((getVarchar08() == null) ? 0 : getVarchar08().hashCode());
        result = prime * result + ((getVarchar09() == null) ? 0 : getVarchar09().hashCode());
        result = prime * result + ((getVarchar10() == null) ? 0 : getVarchar10().hashCode());
        result = prime * result + ((getVarchar11() == null) ? 0 : getVarchar11().hashCode());
        result = prime * result + ((getVarchar12() == null) ? 0 : getVarchar12().hashCode());
        result = prime * result + ((getVarchar13() == null) ? 0 : getVarchar13().hashCode());
        result = prime * result + ((getVarchar14() == null) ? 0 : getVarchar14().hashCode());
        result = prime * result + ((getVarchar15() == null) ? 0 : getVarchar15().hashCode());
        result = prime * result + ((getVarchar16() == null) ? 0 : getVarchar16().hashCode());
        result = prime * result + ((getVarchar17() == null) ? 0 : getVarchar17().hashCode());
        result = prime * result + ((getVarchar18() == null) ? 0 : getVarchar18().hashCode());
        result = prime * result + ((getVarchar19() == null) ? 0 : getVarchar19().hashCode());
        result = prime * result + ((getInt01() == null) ? 0 : getInt01().hashCode());
        result = prime * result + ((getInt02() == null) ? 0 : getInt02().hashCode());
        result = prime * result + ((getInt03() == null) ? 0 : getInt03().hashCode());
        result = prime * result + ((getInt04() == null) ? 0 : getInt04().hashCode());
        result = prime * result + ((getInt05() == null) ? 0 : getInt05().hashCode());
        result = prime * result + ((getBoolean01() == null) ? 0 : getBoolean01().hashCode());
        result = prime * result + ((getBoolean02() == null) ? 0 : getBoolean02().hashCode());
        result = prime * result + ((getBoolean03() == null) ? 0 : getBoolean03().hashCode());
        result = prime * result + ((getBoolean04() == null) ? 0 : getBoolean04().hashCode());
        result = prime * result + ((getUpdatedByUserName() == null) ? 0 : getUpdatedByUserName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_resources<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-19 10:11
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", path=").append(path);
        sb.append(", actions=").append(actions);
        sb.append(", name=").append(name);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", type=").append(type);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", varchar00=").append(varchar00);
        sb.append(", varchar01=").append(varchar01);
        sb.append(", varchar02=").append(varchar02);
        sb.append(", varchar03=").append(varchar03);
        sb.append(", varchar04=").append(varchar04);
        sb.append(", varchar05=").append(varchar05);
        sb.append(", varchar06=").append(varchar06);
        sb.append(", varchar07=").append(varchar07);
        sb.append(", varchar08=").append(varchar08);
        sb.append(", varchar09=").append(varchar09);
        sb.append(", varchar10=").append(varchar10);
        sb.append(", varchar11=").append(varchar11);
        sb.append(", varchar12=").append(varchar12);
        sb.append(", varchar13=").append(varchar13);
        sb.append(", varchar14=").append(varchar14);
        sb.append(", varchar15=").append(varchar15);
        sb.append(", varchar16=").append(varchar16);
        sb.append(", varchar17=").append(varchar17);
        sb.append(", varchar18=").append(varchar18);
        sb.append(", varchar19=").append(varchar19);
        sb.append(", int01=").append(int01);
        sb.append(", int02=").append(int02);
        sb.append(", int03=").append(int03);
        sb.append(", int04=").append(int04);
        sb.append(", int05=").append(int05);
        sb.append(", boolean01=").append(boolean01);
        sb.append(", boolean02=").append(boolean02);
        sb.append(", boolean03=").append(boolean03);
        sb.append(", boolean04=").append(boolean04);
        sb.append(", updatedByUserName=").append(updatedByUserName);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}