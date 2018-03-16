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

    void deleteResourceDynamicPropertyWithType(ResourceDynamicPropertyView resourceDynamicPropertyView);

    ResourceDynamicPropertyView selectResourceDynamicPropertyViewById(Long id);

}