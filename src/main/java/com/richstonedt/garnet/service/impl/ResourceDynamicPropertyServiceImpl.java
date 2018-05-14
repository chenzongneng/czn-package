package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceDynamicPropertyMapper;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.service.CommonService;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import com.richstonedt.garnet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class ResourceDynamicPropertyServiceImpl extends BaseServiceImpl<ResourceDynamicProperty, ResourceDynamicPropertyCriteria, Long> implements ResourceDynamicPropertyService {

    @Autowired
    private ResourceDynamicPropertyMapper resourceDynamicPropertyMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.resourceDynamicPropertyMapper;
    }

    @Override
    public void insertResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView) {

        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
        if (!CollectionUtils.isEmpty(resourceDynamicProperties)) {

            if (ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
                throw new RuntimeException("ResourceDynamicProperty Can Not Be Null");
            }

            insertResourceDynamicProp(resourceDynamicPropertyView);
        }
    }

    @Override
    public void updateResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView) {
        ResourceDynamicProperty resourceDynamicProperty = resourceDynamicPropertyView.getResourceDynamicProperty();
        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
        if (!CollectionUtils.isEmpty(resourceDynamicProperties)) {
            if (ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
                throw new RuntimeException("ResourceDynamicProperty Can Not Be Null");
            }
            //删除type关联的所有数据
//            String type = resourceDynamicProperties.get(0).getType();
            //数据库中的type
            Long id = resourceDynamicProperty.getId();
            ResourceDynamicProperty resourceDynamicPropertyOld = this.selectByPrimaryKey(id);
            String typeOld = resourceDynamicPropertyOld.getType();

            String type = resourceDynamicProperty.getType();
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(typeOld);

            List<ResourceDynamicProperty> resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
            resourceDynamicProperty.setCreatedTime(resourceDynamicPropertyList.get(0).getCreatedTime());
            resourceDynamicPropertyView.setResourceDynamicProperty(resourceDynamicProperty);

            this.deleteByCriteria(resourceDynamicPropertyCriteria);
            //插入修改的数据
            insertResourceDynamicProp(resourceDynamicPropertyView);
            //级联更新资源
            updateResourcesWhenTypeChange(resourceDynamicPropertyView, typeOld);

        }
    }

    /**
     * 插入资源配置
     * @param resourceDynamicPropertyView
     */
    private void insertResourceDynamicProp(ResourceDynamicPropertyView resourceDynamicPropertyView) {
        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
        ResourceDynamicProperty resourceDynamicProperty1 = resourceDynamicPropertyView.getResourceDynamicProperty();

        Long l = IdGeneratorUtil.generateId();
        Long currentTime = System.currentTimeMillis();
        for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {
            resourceDynamicProperty.setId(l);
            resourceDynamicProperty.setActions(resourceDynamicProperty1.getActions());
            resourceDynamicProperty.setType(resourceDynamicProperty1.getType());
            resourceDynamicProperty.setRemark(resourceDynamicProperty1.getRemark());
            resourceDynamicProperty.setApplicationId(resourceDynamicProperty1.getApplicationId());
            resourceDynamicProperty.setTenantId(resourceDynamicProperty1.getTenantId());
            resourceDynamicProperty.setUpdatedByUserName(resourceDynamicProperty1.getUpdatedByUserName());

            if (ObjectUtils.isEmpty(resourceDynamicProperty1.getId())) {
                resourceDynamicProperty.setCreatedTime(currentTime);
            } else {
                resourceDynamicProperty.setCreatedTime(resourceDynamicProperty1.getCreatedTime());
            }



            resourceDynamicProperty.setModifiedTime(currentTime);
            this.insertSelective(resourceDynamicProperty);
            l += 1;
        }

    }

    /**
     * 当资源配置类型名称改变时，级联更新资源
     * @param resourceDynamicPropertyView
     * @param type
     */
    private void updateResourcesWhenTypeChange(ResourceDynamicPropertyView resourceDynamicPropertyView, String type) {
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.createCriteria().andTypeEqualTo(type);
        List<Resource> resources = resourceService.selectByCriteria(resourceCriteria);
        if (!CollectionUtils.isEmpty(resources) && resources.size() > 0) {
            ResourceView resourceView = new ResourceView();
            for (Resource resource : resources) {
                resource.setType(resourceDynamicPropertyView.getResourceDynamicProperty().getType());
                resourceView.setResource(resource);
                resourceService.updateResource(resourceView);
            }
        }
    }

    @Override
    public PageUtil queryResourceDynamicPropertySByParms(ResourceDynamicPropertyParm resourceDynamicPropertyParm) {

        String type = resourceDynamicPropertyParm.getType();
        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        ResourceDynamicPropertyCriteria.Criteria criteria = resourceDynamicPropertyCriteria.createCriteria();

        if (!StringUtils.isEmpty(type)) {
            criteria.andTypeEqualTo(type);
        }

        Long applicationId = resourceDynamicPropertyParm.getApplicationId();
        Long tenantId = resourceDynamicPropertyParm.getTenantId();
        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0) {
            criteria.andApplicationIdEqualTo(applicationId);
        }
        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0) {
            criteria.andTenantIdEqualTo(tenantId);
        }

        if (!StringUtils.isEmpty(resourceDynamicPropertyParm.getSearchName())) {
            criteria.andTypeLike("%" + resourceDynamicPropertyParm.getSearchName() + "%");
        }

        List<ResourceDynamicProperty> resourceDynamicProperties = this.selectByCriteria(resourceDynamicPropertyCriteria);

        List<ResourceDynamicProperty> resourceDynamicPropertyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resourceDynamicProperties)) {
            //根据type进行去重
            Set<String> typeSet = new HashSet<>();
            for (ResourceDynamicProperty resourceDynamicProperty :resourceDynamicProperties) {
                String type1 = resourceDynamicProperty.getType();
                if (!typeSet.contains(type1)) {
                    typeSet.add(type1);
                    resourceDynamicPropertyList.add(resourceDynamicProperty);
                }
            }
        }

        List<ResourceDynamicProperty> resourceDynamicPropertyList1 = this.dealResourceDynamicPropertyIfGarnet(resourceDynamicPropertyParm.getUserId(), resourceDynamicPropertyList);

        PageUtil result = new PageUtil(resourceDynamicPropertyList1, resourceDynamicPropertyList1.size() ,resourceDynamicPropertyParm.getPageSize(),resourceDynamicPropertyParm.getPageNumber());
        return result;
    }

    @Override
    public void deleteResourceDynamicPropertyWithType(ResourceDynamicPropertyView resourceDynamicPropertyView) {

        if (!ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
            String type = resourceDynamicPropertyView.getResourceDynamicProperty().getType();
            Long id = resourceDynamicPropertyView.getResourceDynamicProperty().getId();
            if (!StringUtils.isEmpty(type)) {
                //如果有关联资源，一起删除
                if (resourceService.hasRelated(String.valueOf(id))) {
                    ResourceCriteria resourceCriteria = new ResourceCriteria();
                    resourceCriteria.createCriteria().andTypeEqualTo(type);
                    resourceService.deleteByCriteria(resourceCriteria);
                }

                ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
                resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
                this.deleteByCriteria(resourceDynamicPropertyCriteria);
            }
        }

    }

    @Override
    public ResourceDynamicPropertyView selectResourceDynamicPropertyViewById(Long id) {
        ResourceDynamicProperty resourceDynamicProperty = this.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(resourceDynamicProperty)) {
            throw new RuntimeException("id is invalid");
        }

        String type = resourceDynamicProperty.getType();
        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
        List<ResourceDynamicProperty> resourceDynamicProperties = this.selectByCriteria(resourceDynamicPropertyCriteria);
        ResourceDynamicPropertyView resourceDynamicPropertyView = new ResourceDynamicPropertyView();
        resourceDynamicPropertyView.setResourceDynamicProperty(resourceDynamicProperties.get(0));
        resourceDynamicPropertyView.setResourceDynamicPropertyList(resourceDynamicProperties);

        return resourceDynamicPropertyView;
    }

    @Override
    public ResourceDynamicPropertyView selectResourceDynamicPropertyViewByType(String type) {
        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
        List<ResourceDynamicProperty> resourceDynamicProperties = this.selectByCriteria(resourceDynamicPropertyCriteria);
        ResourceDynamicPropertyView resourceDynamicPropertyView = new ResourceDynamicPropertyView();
        resourceDynamicPropertyView.setResourceDynamicProperty(resourceDynamicProperties.get(0));
        resourceDynamicPropertyView.setResourceDynamicPropertyList(resourceDynamicProperties);

        return resourceDynamicPropertyView;
    }

    private List<ResourceDynamicProperty> dealResourceDynamicPropertyIfGarnet(Long userId, List<ResourceDynamicProperty> resourceDynamicProperties) {

        boolean isSuperAdmin = commonService.superAdminBelongGarnet(userId);

        List<ResourceDynamicProperty> resourceDynamicPropertyList = new ArrayList<>();
        //如果不是超级管理员
        if (!isSuperAdmin) {
            //去除type为 superAdmin、garnet_appCode、garnet_sysMenu 三个资源配置
            for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {

                String type = resourceDynamicProperty.getType();
                if (!GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_APPCODE.equals(type)
                        && !GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_SUPERADMIN.equals(type)
                        && !GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_SYSMENU.equals(type)) {
                    resourceDynamicPropertyList.add(resourceDynamicProperty);
                }
            }

            return resourceDynamicPropertyList;
        } else {
            return resourceDynamicProperties;
        }


    }

}