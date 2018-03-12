package com.richstonedt.garnet.mapper;

import com.richstonedt.garnet.model.Token;
import com.richstonedt.garnet.model.criteria.TokenCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Mapper
public interface TokenMapper extends BaseMapper<Token, TokenCriteria, Long> {
    Token selectSingleByCriteria(TokenCriteria criteria);

    int insertBatchSelective(List<Token> records);

    int updateBatchByPrimaryKeySelective(List<Token> records);
}
