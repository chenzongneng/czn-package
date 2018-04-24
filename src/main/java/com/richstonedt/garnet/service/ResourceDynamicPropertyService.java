package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;

public interface ResourceDynamicPropertyService extends BaseService<ResourceDynamicProperty, ResourceDynamicPropertyCriteria, Long> {

    void insertResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView);

    void updateResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView);

    PageUtil queryResourceDynamicPropertySByParms(ResourceDynamicPropertyParm resourceDynamicPropertyParm);

    /**
     * 通过type删除资源配置
     * @param resourceDynamicPropertyView
     */
    void deleteResourceDynamicPropertyWithType(ResourceDynamicPropertyView resourceDynamicPropertyView);

    /**
     * 通过id查询资源配置
     * @param id
     * @return
     */
    ResourceDynamicPropertyView selectResourceDynamicPropertyViewById(Long id);

    /**
     * 通过type查询资源配置
     * @param type
     * @return
     */
    ResourceDynamicPropertyView selectResourceDynamicPropertyViewByType(String type);

}