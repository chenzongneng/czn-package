package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Group;
import io.swagger.annotations.ApiModelProperty;

/**
 * The type Group parm.
 */
public class GroupParm extends BaseParm{

    private Group group;

    @ApiModelProperty(value = "查询关键词")
    private String searchName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * Gets group.
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets group.
     *
     * @param group the group
     */
    public void setGroup(Group group) {
        this.group = group;
    }
}
