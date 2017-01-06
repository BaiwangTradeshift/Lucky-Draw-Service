package com.baiwangmaoyi.luckydraw.dao;

import java.util.List;

import com.baiwangmaoyi.luckydraw.entity.Participant;

public interface ParticipantDAO {

    List<Participant> getParticipantsByRulesetId(long rulesetId);
}
