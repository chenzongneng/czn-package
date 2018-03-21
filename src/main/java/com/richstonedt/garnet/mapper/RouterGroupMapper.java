package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.RouterGroup;
import com.richstonedt.garnet.model.criteria.RouterGroupCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RouterGroupMapper extends BaseMapper<RouterGroup, RouterGroupCriteria, Long> {
    RouterGroup selectSingleByCriteria(RouterGroupCriteria criteria);

    int insertBatchSelective(List<RouterGroup> records);

    int updateBatchByPrimaryKeySelective(List<RouterGroup> records);
}