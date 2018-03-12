package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface GroupRoleMapper extends BaseMapper<GroupRole, GroupRoleCriteria, Long> {
    GroupRole selectSingleByCriteria(GroupRoleCriteria criteria);

    int insertBatchSelective(List<GroupRole> records);

    int updateBatchByPrimaryKeySelective(List<GroupRole> records);
}
