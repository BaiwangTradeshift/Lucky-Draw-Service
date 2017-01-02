package com.baiwangmaoyi.luckydraw.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baiwangmaoyi.luckydraw.entity.DrawResult;

public interface DrawResultMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DrawResult record);

    int insertSelective(DrawResult record);

    DrawResult selectByPrimaryKey(Long id);

    List<DrawResult> selectExistDrawByRulesetId(@Param("rulesetId") Long rulesetId,@Param("roundId") Long roundId);

    int updateByPrimaryKeySelective(DrawResult record);

    int updateByPrimaryKey(DrawResult record);
}
