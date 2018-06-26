package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;

import javax.validation.constraints.Max;
import java.util.List;

public interface ResourceDynamicPropertyService extends BaseService<ResourceDynamicProperty, ResourceDynamicPropertyCriteria, Long> {

    /**
     * 新增资源配置类型
     * @param resourceDynamicPropertyView
     */
    void insertResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView);

    /**
     * 更新资源配置类型
     * @param resourceDynamicPropertyView
     */
    void updateResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView);

    /**
     * 查询资源配置类型
     * @param resourceDynamicPropertyParm
     * @return
     */
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

    /**
     * 检查type是否已被使用
     * @param id
     * @param type
     * @return
     */
    boolean isTypeUsed(Long id, String type);

    /**
     * 检查名称是否已被使用
     * @param id
     * @param name
     * @return
     */
    boolean isResourceDyPropNameUsed(Long id, String name);

    /**
     * 根据租户id和应用id返回资源配置列表
     * @param resourceDynamicPropertyParm
     * @return
     */
    List<ResourceDynamicProperty> getResourceDynamicPropertyByTIdAndAId(ResourceDynamicPropertyParm resourceDynamicPropertyParm);

    PageUtil getResourceDynamicPropertiesByParams(ResourceDynamicPropertyParm resourceDynamicPropertyParm);
}