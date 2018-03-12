package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.criteria.GroupUserCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface GroupUserMapper extends BaseMapper<GroupUser, GroupUserCriteria, Long> {
    GroupUser selectSingleByCriteria(GroupUserCriteria criteria);

    int insertBatchSelective(List<GroupUser> records);

    int updateBatchByPrimaryKeySelective(List<GroupUser> records);
}
