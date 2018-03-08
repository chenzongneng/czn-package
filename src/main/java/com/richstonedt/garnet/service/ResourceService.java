package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ResourceView;

public interface ResourceService extends BaseService<Resource, ResourceCriteria, Long> {

    public Long insertResource(ResourceView resourceView);

    public void updateResource(ResourceView resourceView);

    public void deleteResource(ResourceView resourceView);

    public PageInfo<Resource> queryResourcesByParms(ResourceParm resourceParm);
}