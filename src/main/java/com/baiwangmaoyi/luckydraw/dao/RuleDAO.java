package com.baiwangmaoyi.luckydraw.dao;

import com.baiwangmaoyi.luckydraw.entity.Rule;

public interface RuleDAO {
    Rule selectByName(String name, long rulesetId);

    Rule selectById(long id);
}
