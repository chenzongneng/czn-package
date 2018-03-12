package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.criteria.ApplicationCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Mapper
public interface ApplicationMapper extends BaseMapper<Application, ApplicationCriteria, Long> {
    Application selectSingleByCriteria(ApplicationCriteria criteria);

    int insertBatchSelective(List<Application> records);

    int updateBatchByPrimaryKeySelective(List<Application> records);
}
