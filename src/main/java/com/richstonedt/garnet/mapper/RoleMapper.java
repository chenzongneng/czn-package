package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role, RoleCriteria, Long> {
    Role selectSingleByCriteria(RoleCriteria criteria);

    int insertBatchSelective(List<Role> records);

    int updateBatchByPrimaryKeySelective(List<Role> records);
}