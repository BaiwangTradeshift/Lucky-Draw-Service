package com.baiwangmaoyi.luckydraw.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baiwangmaoyi.luckydraw.dao.RuleDAO;
import com.baiwangmaoyi.luckydraw.dao.mapper.RuleMapper;
import com.baiwangmaoyi.luckydraw.entity.Rule;

@Repository
public class MyBatisRuleDAO implements RuleDAO {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public Rule selectByName(String name, long rulesetId) {
        return this.ruleMapper.selectByName(name, rulesetId);
    }

    @Override
    public Rule selectById(long id) {
        return this.ruleMapper.selectByPrimaryKey(id);
    }

}
