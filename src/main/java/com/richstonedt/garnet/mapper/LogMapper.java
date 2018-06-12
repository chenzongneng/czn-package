package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Log;
import com.richstonedt.garnet.model.criteria.LogCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LogMapper extends BaseMapper<Log, LogCriteria, Long> {
    Log selectSingleByCriteria(LogCriteria criteria);

    int insertBatchSelective(List<Log> records);

    int updateBatchByPrimaryKeySelective(List<Log> records);
}