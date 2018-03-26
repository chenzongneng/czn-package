package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.SystemConfig;
import com.richstonedt.garnet.model.criteria.SystemConfigCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SystemConfigMapper extends BaseMapper<SystemConfig, SystemConfigCriteria, Long> {
    SystemConfig selectSingleByCriteria(SystemConfigCriteria criteria);

    int insertBatchSelective(List<SystemConfig> records);

    int updateBatchByPrimaryKeySelective(List<SystemConfig> records);
}