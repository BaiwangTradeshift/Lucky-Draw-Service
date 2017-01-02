package com.baiwangmaoyi.luckydraw.dao;

import java.util.List;

import com.baiwangmaoyi.luckydraw.entity.DrawResult;

public interface DrawresultDAO {

    int create(long roundId, long ruleId, int participantId);

    List<DrawResult> selectExistDrawByRulesetId(long rulesetId, long roundId);

}
