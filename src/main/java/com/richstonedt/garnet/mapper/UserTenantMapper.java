package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.UserTenant;
import com.richstonedt.garnet.model.criteria.UserTenantCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserTenantMapper extends BaseMapper<UserTenant, UserTenantCriteria, Long> {
    UserTenant selectSingleByCriteria(UserTenantCriteria criteria);

    int insertBatchSelective(List<UserTenant> records);

    int updateBatchByPrimaryKeySelective(List<UserTenant> records);
}