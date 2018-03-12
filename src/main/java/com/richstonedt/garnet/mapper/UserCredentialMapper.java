package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.UserCredentialCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface UserCredentialMapper extends BaseMapper<UserCredential, UserCredentialCriteria, Long> {
    UserCredential selectSingleByCriteria(UserCredentialCriteria criteria);

    int insertBatchSelective(List<UserCredential> records);

    int updateBatchByPrimaryKeySelective(List<UserCredential> records);
}
