package com.baiwangmaoyi.luckydraw.dao.mapper;

import com.baiwangmaoyi.luckydraw.entity.Ruleset;

public interface RulesetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ruleset record);

    int insertSelective(Ruleset record);

    Ruleset selectByPrimaryKey(Long id);

    Ruleset selectByName(String name);

    int updateByPrimaryKeySelective(Ruleset record);

    int updateByPrimaryKey(Ruleset record);
}
