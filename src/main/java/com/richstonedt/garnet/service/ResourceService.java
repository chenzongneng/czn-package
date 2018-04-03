package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ResourceExcelView;
import com.richstonedt.garnet.model.view.ResourceView;

import java.io.IOException;
import java.util.List;

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
    String getGarnetAppCodeResources(ResourceParm resourceParm) throws IOException;

    /**
     * 获取garnet_sysMenu下的所有resources
     * @return
     * @throws IOException
     */
    String getGarnetSysMenuResources(ResourceParm resourceParm) throws IOException;

    String getAllResourceByAppAndType(ResourceParm resourceParm);

    void saveResourcesExcel(List<ResourceExcelView> resourceExcelViews);


}