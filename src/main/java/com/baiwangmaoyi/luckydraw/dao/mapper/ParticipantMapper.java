package com.baiwangmaoyi.luckydraw.dao.mapper;

import java.util.List;

import com.baiwangmaoyi.luckydraw.entity.Participant;

public interface ParticipantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Participant record);

    int insertSelective(Participant record);

    Participant selectByPrimaryKey(Integer id);

    List<Participant> selectByRulesetId(Long id);

    int updateByPrimaryKeySelective(Participant record);

    int updateByPrimaryKey(Participant record);
}
