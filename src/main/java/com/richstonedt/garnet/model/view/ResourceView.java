package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;

import java.util.List;

/**
 * The type Resource view.
 */
public class ResourceView {

    private Resource resource;

    private List<ResourceDynamicProperty> resourceDynamicProperties;


    /**
     * Gets resource.
     *
     * @return the resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Sets resource.
     *
     * @param resource the resource
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     * Gets resource dynamic properties.
     *
     * @return the resource dynamic properties
     */
    public List<ResourceDynamicProperty> getResourceDynamicProperties() {
        return resourceDynamicProperties;
    }

    /**
     * Sets resource dynamic properties.
     *
     * @param resourceDynamicProperties the resource dynamic properties
     */
    public void setResourceDynamicProperties(List<ResourceDynamicProperty> resourceDynamicProperties) {
        this.resourceDynamicProperties = resourceDynamicProperties;
    }
}
