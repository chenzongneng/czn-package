package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TenantMapper extends BaseMapper<Tenant, TenantCriteria, Long> {
    Tenant selectSingleByCriteria(TenantCriteria criteria);

    int insertBatchSelective(List<Tenant> records);

    int updateBatchByPrimaryKeySelective(List<Tenant> records);
}