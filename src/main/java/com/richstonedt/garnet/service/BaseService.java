package com.richstonedt.garnet.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, E, PK extends Serializable> {
    long countByCriteria(E criteria);

    int deleteByCriteria(E criteria);

    int deleteByPrimaryKey(PK id);

    int insertSelective(T record);

    int insertBatchSelective(List<T> records);

    List<T> selectByCriteria(E criteria);

    T selectByPrimaryKey(PK id);

    T selectSingleByCriteria(E criteria);

    int updateByCriteriaSelective(T record, E criteria);

    int updateByPrimaryKeySelective(T record);

    int updateBatchByPrimaryKeySelective(List<T> records);
}
