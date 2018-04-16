package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Permission;
import com.richstonedt.garnet.model.criteria.PermissionCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PermissionMapper extends BaseMapper<Permission, PermissionCriteria, Long> {
    Permission selectSingleByCriteria(PermissionCriteria criteria);

    int insertBatchSelective(List<Permission> records);

    int updateBatchByPrimaryKeySelective(List<Permission> records);
}
