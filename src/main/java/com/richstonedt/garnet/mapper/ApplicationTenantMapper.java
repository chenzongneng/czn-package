package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.ApplicationTenant;
import com.richstonedt.garnet.model.criteria.ApplicationTenantCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface ApplicationTenantMapper extends BaseMapper<ApplicationTenant, ApplicationTenantCriteria, Long> {
    ApplicationTenant selectSingleByCriteria(ApplicationTenantCriteria criteria);

    int insertBatchSelective(List<ApplicationTenant> records);

    int updateBatchByPrimaryKeySelective(List<ApplicationTenant> records);
}
