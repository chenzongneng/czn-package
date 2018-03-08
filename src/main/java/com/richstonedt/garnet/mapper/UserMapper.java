package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.criteria.UserCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User, UserCriteria, Long> {
    User selectSingleByCriteria(UserCriteria criteria);

    int insertBatchSelective(List<User> records);

    int updateBatchByPrimaryKeySelective(List<User> records);
}