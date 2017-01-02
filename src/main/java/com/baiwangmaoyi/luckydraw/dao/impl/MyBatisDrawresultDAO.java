package com.baiwangmaoyi.luckydraw.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baiwangmaoyi.luckydraw.dao.DrawresultDAO;
import com.baiwangmaoyi.luckydraw.dao.mapper.DrawResultMapper;
import com.baiwangmaoyi.luckydraw.entity.DrawResult;

@Repository
public class MyBatisDrawresultDAO implements DrawresultDAO{

    @Autowired
    private DrawResultMapper drawResultMapper;


    @Override
    public int create(long roundId, long ruleId, int participantId) {
        DrawResult drawResult = new DrawResult();
        drawResult.setParticipantId(participantId);
        drawResult.setRoundId(roundId);
        drawResult.setRuleId(ruleId);
        return this.drawResultMapper.insert(drawResult);
    }

    @Override
    public List<DrawResult> selectExistDrawByRulesetId(long rulesetId, long roundId) {
        return this.drawResultMapper.selectExistDrawByRulesetId(rulesetId, roundId);
    }

}
