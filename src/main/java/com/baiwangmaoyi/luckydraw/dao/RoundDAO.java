package com.baiwangmaoyi.luckydraw.dao;

import com.baiwangmaoyi.luckydraw.entity.Round;

public interface RoundDAO {

    long createNew(long rulesetId);

    Round selectMaxByRulesetId(Long rulesetId);
}
