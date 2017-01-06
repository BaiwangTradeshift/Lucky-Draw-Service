package com.baiwangmaoyi.luckydraw.dao;

import com.baiwangmaoyi.luckydraw.entity.Ruleset;

public interface RulesetDAO {

    Ruleset selectByName(String name);

}
