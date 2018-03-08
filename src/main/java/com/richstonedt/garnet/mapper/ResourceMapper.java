package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.criteria.ResourceCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ResourceMapper extends BaseMapper<Resource, ResourceCriteria, Long> {
    Resource selectSingleByCriteria(ResourceCriteria criteria);

    int insertBatchSelective(List<Resource> records);

    int updateBatchByPrimaryKeySelective(List<Resource> records);
}