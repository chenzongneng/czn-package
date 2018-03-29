package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ResourceView;

import java.io.IOException;

public interface ResourceService extends BaseService<Resource, ResourceCriteria, Long> {

    public Long insertResource(ResourceView resourceView);

    public void updateResource(ResourceView resourceView);

    public void deleteResource(ResourceView resourceView);

    public PageUtil<Resource> queryResourcesByParms(ResourceParm resourceParm);

    /**
     * 获取garnet_appCode下的所有resources
     * @return
     * @throws IOException
     */
    String getGarnetAppCodeResources() throws IOException;

    /**
     * 获取garnet_sysMenu下的所有resources
     * @return
     * @throws IOException
     */
    String getGarnetSysMenuResources() throws IOException;

}