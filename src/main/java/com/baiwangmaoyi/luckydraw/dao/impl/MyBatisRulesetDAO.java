package com.baiwangmaoyi.luckydraw.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baiwangmaoyi.luckydraw.dao.RulesetDAO;
import com.baiwangmaoyi.luckydraw.dao.mapper.RulesetMapper;
import com.baiwangmaoyi.luckydraw.entity.Ruleset;

@Repository
public class MyBatisRulesetDAO implements RulesetDAO{

    @Autowired
    private RulesetMapper rulesetMapper;

    @Override
    public Ruleset selectByName(String name) {
        return this.rulesetMapper.selectByName(name);
    }

}
