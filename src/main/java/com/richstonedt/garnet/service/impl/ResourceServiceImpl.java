package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceMapper;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.model.parm.ResourceParm;
import com.richstonedt.garnet.model.view.ResourceView;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import com.richstonedt.garnet.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl extends BaseServiceImpl<Resource, ResourceCriteria, Long> implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceDynamicPropertyService resourceDynamicPropertyService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.resourceMapper;
    }

    @Override
    public Long insertResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();

        resource.setId(IdGeneratorUtil.generateId());

        Long currentTime = new Date().getTime();

        resource.setCreatedTime(currentTime);

        resource.setModifiedTime(currentTime);

        this.insertSelective(resource);

        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){

            for (ResourceDynamicProperty resourceDynamicProperty:
                 resourceView.getResourceDynamicProperties()) {

                resourceDynamicProperty.setId(IdGeneratorUtil.generateId());
                resourceDynamicProperty.setResourceId(resource.getId());
                resourceDynamicPropertyService.insertSelective(resourceDynamicProperty);

            }

        }

        return resource.getId();

    }

    @Override
    public void updateResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();

        Long currentTime = new Date().getTime();

        resource.setModifiedTime(currentTime);

        this.updateByPrimaryKeySelective(resource);


        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){

            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria.createCriteria().andResourceIdEqualTo(resource.getId());
            resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);

            for (ResourceDynamicProperty resourceDynamicProperty:
                    resourceView.getResourceDynamicProperties()) {

                resourceDynamicProperty.setId(IdGeneratorUtil.generateId());
                resourceDynamicProperty.setResourceId(resource.getId());
                resourceDynamicPropertyService.insertSelective(resourceDynamicProperty);

            }

        }


    }

    @Override
    public void deleteResource(ResourceView resourceView) {

        Resource resource = resourceView.getResource();

        if(!ObjectUtils.isEmpty(resourceView.getResourceDynamicProperties())){

            ResourceDynamicPropertyCriteria resourceDynamicPropertyCriteria = new ResourceDynamicPropertyCriteria();
            resourceDynamicPropertyCriteria.createCriteria().andResourceIdEqualTo(resource.getId());
            resourceDynamicPropertyService.deleteByCriteria(resourceDynamicPropertyCriteria);

        }

        this.deleteByPrimaryKey(resource.getId());

    }

    @Override
    public PageInfo<Resource> queryResourcesByParms(ResourceParm resourceParm) {

        Resource resource = resourceParm.getResource();

        ResourceCriteria resourceCriteria = new ResourceCriteria();

        if(!ObjectUtils.isEmpty(resourceParm.getApplicationId())){

            resourceCriteria.createCriteria().andApplicationIdEqualTo(resourceParm.getApplicationId());

        }
        if(!ObjectUtils.isEmpty(resourceParm.getTenantId())){

            resourceCriteria.createCriteria().andTenantIdEqualTo(resourceParm.getTenantId());
        }

        PageHelper.startPage(resourceParm.getPageNumber(),resourceParm.getPageSize());

        List<Resource> resources = this.selectByCriteria(resourceCriteria);

        PageInfo<Resource> resourcePageInfo = new PageInfo<Resource>(resources);


        return resourcePageInfo;
    }


}