package com.richstonedt.garnet.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BaseMapper<T, E, PK extends Serializable> {
    long countByCriteria(E criteria);

    int deleteByCriteria(E criteria);

    int deleteByPrimaryKey(PK id);

    int insertSelective(T record);

    int insertBatchSelective(List<T> records);

    List<T> selectByCriteria(E criteria);

    T selectByPrimaryKey(PK id);

    T selectSingleByCriteria(E criteria);

    int updateByCriteriaSelective(@Param("record") T record, @Param("criteria") E criteria);

    int updateByPrimaryKeySelective(T record);

    int updateBatchByPrimaryKeySelective(List<T> records);
}
