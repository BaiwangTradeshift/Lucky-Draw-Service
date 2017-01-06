package com.baiwangmaoyi.luckydraw.dao.mapper;

import com.baiwangmaoyi.luckydraw.entity.Round;

public interface RoundMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Round record);

    int insertSelective(Round record);

    Round selectByPrimaryKey(Long id);

    Round selectMaxByRulesetId(Long rulesetId);

    int updateByPrimaryKeySelective(Round record);

    int updateByPrimaryKey(Round record);
}
