package com.baiwangmaoyi.luckydraw.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baiwangmaoyi.luckydraw.dao.RoundDAO;
import com.baiwangmaoyi.luckydraw.dao.mapper.RoundMapper;
import com.baiwangmaoyi.luckydraw.entity.Round;

@Repository
public class MyBatisRoundDAO implements RoundDAO{

    @Autowired
    private RoundMapper roundMapper;

    @Override
    public long createNew(long rulesetId){
        Round round = new Round();
        round.setRulesetId(rulesetId);
        round.setStartTime(new Date());
        this.roundMapper.insert(round);
        return round.getId();
    }

    @Override
    public Round selectMaxByRulesetId(Long rulesetId) {
        return this.roundMapper.selectMaxByRulesetId(rulesetId);
    }


}
