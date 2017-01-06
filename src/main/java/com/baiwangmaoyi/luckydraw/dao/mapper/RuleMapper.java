package com.baiwangmaoyi.luckydraw.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.baiwangmaoyi.luckydraw.entity.Rule;

public interface RuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Rule record);

    int insertSelective(Rule record);

    Rule selectByPrimaryKey(Long id);

    Rule selectByName(@Param("name") String name, @Param("rulesetId") Long rulesetId);

    int updateByPrimaryKeySelective(Rule record);

    int updateByPrimaryKey(Rule record);
}
