package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Resource;

/**
 * The type Resource parm.
 */
public class ResourceParm extends BaseParm{

    private Resource resource;

    private String searchName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
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
