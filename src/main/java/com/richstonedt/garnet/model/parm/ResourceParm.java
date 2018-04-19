package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Resource;

/**
 * The type Resource parm.
 */
public class ResourceParm extends BaseParm{

    private Resource resource;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


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
}
