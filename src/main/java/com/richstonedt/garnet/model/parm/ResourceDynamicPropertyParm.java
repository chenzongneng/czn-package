package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;

/**
 * The type Resource parm.
 */
public class ResourceDynamicPropertyParm extends BaseParm{

    private ResourceDynamicProperty resourceDynamicProperty;

    private String searchName;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public ResourceDynamicProperty getResourceDynamicProperty() {
        return resourceDynamicProperty;
    }

    public void setResourceDynamicProperty(ResourceDynamicProperty resourceDynamicProperty) {
        this.resourceDynamicProperty = resourceDynamicProperty;
    }
}
