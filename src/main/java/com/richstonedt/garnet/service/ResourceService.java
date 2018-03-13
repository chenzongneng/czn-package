package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ResourceView;

public interface ResourceService extends BaseService<Resource, ResourceCriteria, Long> {

    public Long insertResource(ResourceView resourceView);

    public void updateResource(ResourceView resourceView);

    public void deleteResource(ResourceView resourceView);

    public PageUtil<Resource> queryResourcesByParms(ResourceParm resourceParm);

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param resource
     */
    public void updateStatusById(Resource resource);
}