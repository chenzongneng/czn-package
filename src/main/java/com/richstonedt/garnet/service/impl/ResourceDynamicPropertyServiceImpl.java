package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceDynamicPropertyMapper;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceDynamicPropertyParm;
import com.richstonedt.garnet.model.view.ResourceDynamicPropertyView;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.mapping.RedisPersistentProperty;
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

            Long l = IdGeneratorUtil.generateId();
            for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {
                resourceDynamicProperty.setId(l);
                resourceDynamicProperty.setType(resourceDynamicPropertyView.getResourceDynamicProperty().getType());
                this.insertSelective(resourceDynamicProperty);
                l += 1;
            }
        }
    }

    @Override
    public void updateResourceDynamicProperty(ResourceDynamicPropertyView resourceDynamicPropertyView) {
        List<ResourceDynamicProperty> resourceDynamicProperties = resourceDynamicPropertyView.getResourceDynamicPropertyList();
        if (!CollectionUtils.isEmpty(resourceDynamicProperties)) {
            if (ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
                throw new RuntimeException("ResourceDynamicProperty Can Not Be Null");
            }
            //删除type关联的所有数据
            String type = resourceDynamicProperties.get(0).getType();
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
            this.deleteByCriteria(resourceDynamicPropertyCriteria);
            //插入修改的数据
            Long l = IdGeneratorUtil.generateId();
            for (ResourceDynamicProperty resourceDynamicProperty : resourceDynamicProperties) {
                resourceDynamicProperty.setId(l);
                resourceDynamicProperty.setType(resourceDynamicPropertyView.getResourceDynamicProperty().getType());
                this.insertSelective(resourceDynamicProperty);
                l += 1;
            }
        }
    }

    @Override
    public PageUtil queryResourceDynamicPropertySByParms(ResourceDynamicPropertyParm resourceDynamicPropertyParm) {

        String type = resourceDynamicPropertyParm.getType();
        ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
        if (!StringUtils.isEmpty(type)) {
            resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
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

        PageUtil result = new PageUtil(resourceDynamicPropertyList, (int)this.countByCriteria(resourceDynamicPropertyCriteria) ,resourceDynamicPropertyParm.getPageSize(),resourceDynamicPropertyParm.getPageNumber());
        return result;
    }

    @Override
    public void deleteResourceDynamicPropertyWithType(ResourceDynamicPropertyView resourceDynamicPropertyView) {
        if (!ObjectUtils.isEmpty(resourceDynamicPropertyView.getResourceDynamicProperty())) {
            String type = resourceDynamicPropertyView.getResourceDynamicProperty().getType();
            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria.createCriteria().andTypeEqualTo(type);
            this.deleteByCriteria(resourceDynamicPropertyCriteria);
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
}