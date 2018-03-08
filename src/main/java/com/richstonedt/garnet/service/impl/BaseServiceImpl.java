package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.service.BaseService;
import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T, E, PK extends Serializable> implements BaseService<T, E, PK> {

    public abstract BaseMapper<T, E, PK> getBaseMapper();

    @Override
    public long countByCriteria(E criteria) {
        return getBaseMapper().countByCriteria(criteria);
    }

    @Override
    public int deleteByCriteria(E criteria) {
        return getBaseMapper().deleteByCriteria(criteria);
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(T record) {
        return getBaseMapper().insertSelective(record);
    }

    @Override
    public int insertBatchSelective(List<T> records) {
        return getBaseMapper().insertBatchSelective(records);
    }

    @Override
    public List<T> selectByCriteria(E criteria) {
        return getBaseMapper().selectByCriteria(criteria);
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public T selectSingleByCriteria(E criteria) {
        return getBaseMapper().selectSingleByCriteria(criteria);
    }

    @Override
    public int updateByCriteriaSelective(T record, E criteria) {
        return getBaseMapper().updateByCriteriaSelective(record,criteria);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return getBaseMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateBatchByPrimaryKeySelective(List<T> records) {
        return getBaseMapper().updateBatchByPrimaryKeySelective(records);
    }
}
