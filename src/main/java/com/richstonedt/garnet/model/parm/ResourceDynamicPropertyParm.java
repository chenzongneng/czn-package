package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;

/**
 * The type Resource parm.
 */
public class ResourceDynamicPropertyParm extends BaseParm{

    private ResourceDynamicProperty resourceDynamicProperty;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResourceDynamicProperty getResourceDynamicProperty() {
        return resourceDynamicProperty;
    }

    public void setResourceDynamicProperty(ResourceDynamicProperty resourceDynamicProperty) {
        this.resourceDynamicProperty = resourceDynamicProperty;
    }
}
