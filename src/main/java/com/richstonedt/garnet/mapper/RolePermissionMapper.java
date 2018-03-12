package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.RolePermission;
import com.richstonedt.garnet.model.criteria.RolePermissionCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission, RolePermissionCriteria, Long> {
    RolePermission selectSingleByCriteria(RolePermissionCriteria criteria);

    int insertBatchSelective(List<RolePermission> records);

    int updateBatchByPrimaryKeySelective(List<RolePermission> records);
}
