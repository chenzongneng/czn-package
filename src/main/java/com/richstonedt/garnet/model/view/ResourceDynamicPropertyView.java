package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.ResourceDynamicProperty;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;

import java.util.List;

public class ResourceDynamicPropertyView {

    private ResourceDynamicProperty resourceDynamicProperty;

    private List<ResourceDynamicProperty> resourceDynamicPropertyList;

    public List<ResourceDynamicProperty> getResourceDynamicPropertyList() {
        return resourceDynamicPropertyList;
    }

    public void setResourceDynamicPropertyList(List<ResourceDynamicProperty> resourceDynamicPropertyList) {
        this.resourceDynamicPropertyList = resourceDynamicPropertyList;
    }

    public ResourceDynamicProperty getResourceDynamicProperty() {
        return resourceDynamicProperty;
    }

    public void setResourceDynamicProperty(ResourceDynamicProperty resourceDynamicProperty) {
        this.resourceDynamicProperty = resourceDynamicProperty;
    }
}
