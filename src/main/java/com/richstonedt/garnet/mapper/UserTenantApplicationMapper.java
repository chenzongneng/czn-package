package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.UserTenantApplication;
import com.richstonedt.garnet.model.criteria.UserTenantApplicationCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface UserTenantApplicationMapper extends BaseMapper<UserTenantApplication, UserTenantApplicationCriteria, Long> {
    UserTenantApplication selectSingleByCriteria(UserTenantApplicationCriteria criteria);

    int insertBatchSelective(List<UserTenantApplication> records);

    int updateBatchByPrimaryKeySelective(List<UserTenantApplication> records);
}
