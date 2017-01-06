package com.baiwangmaoyi.luckydraw.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baiwangmaoyi.luckydraw.dao.ParticipantDAO;
import com.baiwangmaoyi.luckydraw.dao.mapper.ParticipantMapper;
import com.baiwangmaoyi.luckydraw.entity.Participant;

@Repository
public class MyBatisParticipantDAO implements ParticipantDAO{

    @Autowired
    private ParticipantMapper participantMapper;

    @Override
    public List<Participant> getParticipantsByRulesetId(long rulesetId) {
        return participantMapper.selectByRulesetId(rulesetId);
    }
}
