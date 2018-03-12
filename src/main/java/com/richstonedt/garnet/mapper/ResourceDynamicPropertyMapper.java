package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface ResourceDynamicPropertyMapper extends BaseMapper<ResourceDynamicProperty, ResourceDynamicPropertyCriteria, Long> {
    ResourceDynamicProperty selectSingleByCriteria(ResourceDynamicPropertyCriteria criteria);

    int insertBatchSelective(List<ResourceDynamicProperty> records);

    int updateBatchByPrimaryKeySelective(List<ResourceDynamicProperty> records);
}
