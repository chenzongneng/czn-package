package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.interceptory.LogRequired;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceDynamicPropertyMapper;
import com.richstonedt.garnet.model.Log;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.service.*;
import org.hibernate.validator.constraints.ModCheck;
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

    private static final Long TENANTID_NULL = 0L;

    @Autowired
    private ResourceDynamicPropertyMapper resourceDynamicPropertyMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @Autowired
    private TenantService tenantService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.resourceDynamicPropertyMapper;
    }

    @LogRequired(module = "资源类型管理模块", method = "新增资源类型")
    @Override
    public void insertResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView) {

        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
//        if (!CollectionUtils.isEmpty(resourceDynamicProperties)) {

            if (ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
                throw new RuntimeException("ResourceDynamicProperty Can Not Be Null");
            }

            insertResourceDynamicProp(resourceDynamicPropertyView);
//        }
    }

    @LogRequired(module = "资源类型管理模块", method = "配置资源类型")
    @Override
    public void updateResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView) {
        ResourceDynamicProperty resourceDynamicProperty = resourceDynamicPropertyView.getResourceDynamicProperty();
        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
//        if (!CollectionUtils.isEmpty(resourceDynamicProperties)) {
        if (ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
            throw new RuntimeException("ResourceDynamicProperty Can Not Be Null");
        }

        //数据库中的type
        Long id = resourceDynamicProperty.getId();
         ResourceDynamicProperty resourceDynamicPropertyOld = this.selectByPrimaryKey(id);
        String typeOld = resourceDynamicPropertyOld.getType();

        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(typeOld);

        List<ResourceDynamicProperty> resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
        resourceDynamicProperty.setCreatedTime(resourceDynamicPropertyList.get(0).getCreatedTime());
        resourceDynamicPropertyView.setResourceDynamicProperty(resourceDynamicProperty);

        //删除旧的type
        this.deleteByCriteria(resourceDynamicPropertyCriteria);
        //插入修改的数据
        insertResourceDynamicProp(resourceDynamicPropertyView);
        //级联更新资源
        updateResourcesWhenTypeChange(resourceDynamicPropertyView, typeOld);

//        }
    }

    /**
     * 插入资源配置
     * @param resourceDynamicPropertyView
     */
    private void insertResourceDynamicProp(ResourceDynamicPropertyView resourceDynamicPropertyView) {
        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
        ResourceDynamicProperty resourceDynamicProperty1 = resourceDynamicPropertyView.getResourceDynamicProperty();

        //如果resourceDynamicProperties为0，即没有添加输入框
        if (resourceDynamicProperties.size() == 0) {
            Long currentTime = System.currentTimeMillis();
            if (ObjectUtils.isEmpty(resourceDynamicProperty1.getId())) {
                resourceDynamicProperty1.setId(IdGeneratorUtil.generateId());
                resourceDynamicProperty1.setCreatedTime(currentTime);
            }
            resourceDynamicProperty1.setModifiedTime(currentTime);
            this.insertSelective(resourceDynamicProperty1);
        } else {
            Long l = IdGeneratorUtil.generateId();
            Long currentTime = System.currentTimeMillis();
            for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {
                resourceDynamicProperty.setId(l);
                resourceDynamicProperty.setName(resourceDynamicProperty1.getName());
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

        //根据登录用户是否是超级管理员进行处理
        List<ResourceDynamicProperty> resourceDynamicPropertyList1 = this.dealResourceDynamicPropertyIfGarnet(resourceDynamicPropertyParm.getUserId(), resourceDynamicPropertyList);

        PageUtil result = new PageUtil(resourceDynamicPropertyList1, resourceDynamicPropertyList1.size() ,resourceDynamicPropertyParm.getPageSize(),resourceDynamicPropertyParm.getPageNumber());
        return result;
    }

    @LogRequired(module = "资源类型管理模块", method = "删除资源类型")
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

    @Override
    public boolean isTypeUsed(Long id, String type) {

        if (ObjectUtils.isEmpty(id) || StringUtils.isEmpty(type)) {
            throw new RuntimeException("资源配置id和资源配置类型代号不能为空");
        }

        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
        List<ResourceDynamicProperty> resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
        boolean b = false;
        if (CollectionUtils.isEmpty(resourceDynamicPropertyList) || resourceDynamicPropertyList.size() <= 0) {
            b = true;
        } else {
            if (!ObjectUtils.isEmpty(id) && id.longValue() != 0L) {
                for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicPropertyList) {
                    if (resourceDynamicProperty.getId().longValue() == id.longValue()) {
                        b = true;
                    }
                }
            }
        }

        return b;
    }

    @Override
    public boolean isResourceDyPropNameUsed(Long id, String name) {
        if (ObjectUtils.isEmpty(id) || StringUtils.isEmpty(name)) {
            throw new RuntimeException("资源配置id和资源配置类型名称不能为空");
        }

        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        resourceDynamicPropertyCriteria.createCriteria().andNameEqualTo(name);
        List<ResourceDynamicProperty> resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
        boolean b = false;
        if (CollectionUtils.isEmpty(resourceDynamicPropertyList) || resourceDynamicPropertyList.size() <= 0) {
            b = true;
        } else {
            if (!ObjectUtils.isEmpty(id) && id.longValue() != 0L) {
                for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicPropertyList) {
                    if (resourceDynamicProperty.getId().longValue() == id.longValue()) {
                        b = true;
                    }
                }
            }
        }

        return b;
    }

    @Override
    public List<ResourceDynamicProperty> getResourceDynamicPropertyByTIdAndAId(ResourceDynamicPropertyParm resourceDynamicPropertyParm) {

        Long applicationId = resourceDynamicPropertyParm.getApplicationId();
        Long tenantId = resourceDynamicPropertyParm.getTenantId();

        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        List<ResourceDynamicProperty> resourceDynamicProperties = new ArrayList<>();

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0 && (ObjectUtils.isEmpty(tenantId) || tenantId.longValue() == 0)) {
            //应用级
            resourceDynamicPropertyCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L);
            resourceDynamicProperties = this.selectByCriteria(resourceDynamicPropertyCriteria);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && (ObjectUtils.isEmpty(applicationId) || applicationId.longValue() == 0)) {
            //租户级
            resourceDynamicPropertyCriteria.createCriteria().andApplicationIdEqualTo(0L).andTenantIdEqualTo(tenantId);
            resourceDynamicProperties = this.selectByCriteria(resourceDynamicPropertyCriteria);
        }

        //应用+租户级
        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && !ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0) {
            //应用级
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria1 = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria1.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L);
            //租户级
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria2 = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria2.createCriteria().andApplicationIdEqualTo(0L).andTenantIdEqualTo(tenantId);
            //应用+租户
            resourceDynamicPropertyCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(tenantId);

            List<ResourceDynamicProperty> resourceDynamicPropertyList1 = this.selectByCriteria(resourceDynamicPropertyCriteria1);
            List<ResourceDynamicProperty> resourceDynamicPropertyList2 = this.selectByCriteria(resourceDynamicPropertyCriteria2);
            List<ResourceDynamicProperty> resourceDynamicPropertyList3 = this.selectByCriteria(resourceDynamicPropertyCriteria);

            resourceDynamicProperties.addAll(resourceDynamicPropertyList1);
            resourceDynamicProperties.addAll(resourceDynamicPropertyList2);
            resourceDynamicProperties.addAll(resourceDynamicPropertyList3);
        }

        List<ResourceDynamicProperty> resourceDynamicPropertyList = new ArrayList<>();
        Set<String> resourceDynamicPropertyIdSet = new HashSet<>();
        for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {
            if (!resourceDynamicPropertyIdSet.contains(resourceDynamicProperty.getType())) {
                resourceDynamicPropertyList.add(resourceDynamicProperty);
                resourceDynamicPropertyIdSet.add(resourceDynamicProperty.getType());
            }
        }

        return resourceDynamicPropertyList;
    }

    @LogRequired(module = "资源类型管理模块", method = "查询资源类型列表")
    @Override
    public PageUtil getResourceDynamicPropertiesByParams(ResourceDynamicPropertyParm resourceDynamicPropertyParm) {
        Long userId = resourceDynamicPropertyParm.getUserId();
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

        String level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_RESOURCETYPEMANAGE_QUERY_PATH);
        List<ResourceDynamicProperty> resourceDynamicPropertyList = new ArrayList<>();
        if (Integer.valueOf(level) == 1) {
            //全部数据
            resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
        } else if (Integer.valueOf(level) == 2) {
            //非Garnet数据
            List<Long> tenantIdList = commonService.getTenantIdsNotGarnet(userId);
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria1 = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria1.createCriteria().andTenantIdIn(tenantIdList);
            List<ResourceDynamicProperty> resourceDynamicPropertyList1 = this.selectByCriteria(resourceDynamicPropertyCriteria1);

            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria2 = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria2.createCriteria().andTenantIdEqualTo(TENANTID_NULL);
            List<ResourceDynamicProperty> resourceDynamicPropertyList2 = this.selectByCriteria(resourceDynamicPropertyCriteria2);

            List<ResourceDynamicProperty> resourceDynamicPropertyList3 = new ArrayList<>();
            resourceDynamicPropertyList3.addAll(resourceDynamicPropertyList1);
            resourceDynamicPropertyList3.addAll(resourceDynamicPropertyList2);

            List<Long> resourceDynamicPropIds = new ArrayList<>();
            for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicPropertyList3) {
                resourceDynamicPropIds.add(resourceDynamicProperty.getId());
            }

            criteria.andIdIn(resourceDynamicPropIds);
            resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
        } else if (Integer.valueOf(level) == 3) {
            //本用户为租户管理员的租户关联的资源类型(不包括租户字段为空[即应用级]的类型)
            List<Tenant> tenantList = tenantService.getTenantManageListByUserId(userId);
            List<Long> tenantIdList = new ArrayList<>();
            for (Tenant tenant : tenantList) {
                tenantIdList.add(tenant.getId());
            }
            if (tenantIdList.size() == 0) {
                tenantIdList.add(GarnetContants.NON_VALUE);
            }
            criteria.andTenantIdIn(tenantIdList);
            resourceDynamicPropertyList = this.selectByCriteria(resourceDynamicPropertyCriteria);
        }

        List<ResourceDynamicProperty> resourceDynamicProperties = new ArrayList<>();
        //根据type进行去重
        Set<String> typeSet = new HashSet<>();
        for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicPropertyList) {
            String type1 = resourceDynamicProperty.getType();
            if (!typeSet.contains(type1)) {
                typeSet.add(type1);
                resourceDynamicProperties.add(resourceDynamicProperty);
            }
        }

        PageUtil result = new PageUtil(resourceDynamicProperties, resourceDynamicProperties.size() ,resourceDynamicPropertyParm.getPageSize(),resourceDynamicPropertyParm.getPageNumber());
        return result;
    }

    private List<ResourceDynamicProperty> dealResourceDynamicPropertyIfGarnet(Long userId, List<ResourceDynamicProperty> resourceDynamicProperties) {

        ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(userId);
        boolean isSuperAdmin = returnTenantIdView.isSuperAdmin();
        boolean isSuperAdminBelongGarnet = commonService.superAdminBelongGarnet(userId);

        List<ResourceDynamicProperty> resourceDynamicPropertyList = new ArrayList<>();
        //如果不是超级管理员
        if (!(isSuperAdmin && isSuperAdminBelongGarnet)) {
            //去除type为 superAdmin、garnet_appCode、garnet_sysMenu 三个资源配置
            for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {

                String type = resourceDynamicProperty.getType();
                if (!GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_BUTTON.equals(type)
                        && !GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_MODULE.equals(type)
                        && !GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_SUPERADMIN.equals(type)
                        && !GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_SYSMENU.equals(type)
                        && !GarnetContants.GARNET_RESOURCE_DYNAMICPROPERTY_BELONGTOGARNET.equals(type)) {
                    resourceDynamicPropertyList.add(resourceDynamicProperty);
                }
            }

            return resourceDynamicPropertyList;
        } else {
            //如果是超级管理员，原封返回
            return resourceDynamicProperties;
        }
    }

}