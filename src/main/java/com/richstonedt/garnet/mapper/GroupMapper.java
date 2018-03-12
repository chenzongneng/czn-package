package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface GroupMapper extends BaseMapper<Group, GroupCriteria, Long> {
    Group selectSingleByCriteria(GroupCriteria criteria);

    int insertBatchSelective(List<Group> records);

    int updateBatchByPrimaryKeySelective(List<Group> records);
}
