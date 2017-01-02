package com.baiwangmaoyi.luckydraw.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baiwangmaoyi.luckydraw.dao.DrawresultDAO;
import com.baiwangmaoyi.luckydraw.dao.ParticipantDAO;
import com.baiwangmaoyi.luckydraw.dao.RoundDAO;
import com.baiwangmaoyi.luckydraw.dao.RuleDAO;
import com.baiwangmaoyi.luckydraw.dao.RulesetDAO;
import com.baiwangmaoyi.luckydraw.entity.DrawResult;
import com.baiwangmaoyi.luckydraw.entity.Participant;
import com.baiwangmaoyi.luckydraw.entity.Round;
import com.baiwangmaoyi.luckydraw.entity.Rule;
import com.baiwangmaoyi.luckydraw.entity.Ruleset;

@Service
@Transactional
public class NY2017Service {

    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private RoundDAO roundDAO;

    @Autowired
    private RulesetDAO rulesetDAO;

    @Autowired
    private DrawresultDAO drawresultDAO;

    @Autowired
    private RuleDAO ruleDAO;

    @Autowired
    private RandomProvider randomProvider;

    private Ruleset getCurrentRuleSet() {
        return rulesetDAO.selectByName("2017_NY");
    }

    public long startNewRound() {
        Ruleset ruleset = getCurrentRuleSet();
        return roundDAO.createNew(ruleset.getId());
    }

    public long getCurrentRound() {
        Ruleset ruleset = getCurrentRuleSet();
        Round round = this.roundDAO.selectMaxByRulesetId(ruleset.getId());
        if (round != null) {
            return round.getId();
        }
        throw new RuntimeException("No round started as 2017 NY.");
    }

    public Participant draw1stAPrize(String fpdm) {
        return draw2ndAPrize(fpdm);
    }

    public List<Participant> draw1stBPrize(int count){
        Ruleset ruleset = getCurrentRuleSet();
        long currentRoundId = getCurrentRound();
        List<DrawResult> pickedResult =
                drawresultDAO.selectExistDrawByRulesetId(ruleset.getId(), currentRoundId);
        Rule rule = ruleDAO.selectByName("1ST_PRIZE_B", ruleset.getId());
        List<Participant> list = participantDAO.getParticipantsByRulesetId(ruleset.getId());

        List<Participant> candidateList = getCandidates(list, pickedResult);

        List<Participant> pickedList = this.randomProvider.pickRandomly(candidateList, count);

        updateDrawResult(pickedList, rule, currentRoundId);

        return pickedList;
    }

    public Participant draw2ndAPrize(String fpdm) {
        Ruleset ruleset = getCurrentRuleSet();
        long currentRoundId = getCurrentRound();
        List<DrawResult> pickedResult =
                drawresultDAO.selectExistDrawByRulesetId(ruleset.getId(), currentRoundId);
        Rule rule = ruleDAO.selectByName("2ND_PRIZE_A", ruleset.getId());
        List<Participant> list = participantDAO.getParticipantsByRulesetId(ruleset.getId());
        List<Participant> candidateList = getCandidates(list, pickedResult);
        List<Participant> candicateListWFpdm = getCandidateWithFPDM(candidateList, fpdm);

        List<Participant> pickedList = this.randomProvider.pickRandomly(candicateListWFpdm, 1);

        updateDrawResult(pickedList, rule, currentRoundId);

        return pickedList.get(0);
    }

    public List<Participant> draw2ndBPrize(int count) {
        Ruleset ruleset = getCurrentRuleSet();
        long currentRoundId = getCurrentRound();
        List<DrawResult> pickedResult =
                drawresultDAO.selectExistDrawByRulesetId(ruleset.getId(), currentRoundId);
        Rule rule = ruleDAO.selectByName("2ND_PRIZE_B", ruleset.getId());
        List<Participant> list = participantDAO.getParticipantsByRulesetId(ruleset.getId());

        List<Participant> candidateList = getCandidates(list, pickedResult);

        List<Participant> pickedList = this.randomProvider.pickRandomly(candidateList, count);

        updateDrawResult(pickedList, rule, currentRoundId);

        return pickedList;
    }

    public List<Participant> draw3rdPrize(int count) {
        Ruleset ruleset = getCurrentRuleSet();
        long currentRoundId = getCurrentRound();
        List<DrawResult> pickedResult =
                drawresultDAO.selectExistDrawByRulesetId(ruleset.getId(), currentRoundId);
        Rule rule = ruleDAO.selectByName("3RD_PRIZE_20", ruleset.getId());
        List<Participant> list = participantDAO.getParticipantsByRulesetId(ruleset.getId());

        List<Participant> candidateList = getCandidates(list, pickedResult);

        List<Participant> pickedList = this.randomProvider.pickRandomly(candidateList, count);


        updateDrawResult(pickedList, rule, currentRoundId);

        return pickedList;
    }

    private void updateDrawResult(List<Participant> pickedList, Rule rule, long roundId) {
        List<DrawResult> alreadyPickedParticipants =
                drawresultDAO.selectExistDrawByRulesetId(rule.getRulesetId(), roundId);
        long duplicateCount = alreadyPickedParticipants.stream().filter(p -> {
                    for (Participant picked : pickedList) {
                        if (p.getParticipantId().equals(picked.getId())) {
                            return true;
                        }
                    }
                    return false;
                }
        ).count();
        if (duplicateCount != 0) {
            throw new RuntimeException("This program is flawed, candidates are picked more then once.");
        }
        List<DrawResult> results = alreadyPickedParticipants.stream().filter(p -> p.getRuleId().equals(rule.getId())).collect
                (Collectors.toList());
        if (results.size() + pickedList.size() <= rule.getMaxCount()) {
            for (Participant participant : pickedList) {
                this.drawresultDAO.create(roundId, rule.getId(), participant.getId());
            }
        } else {
            throw new RuntimeException("Can't draw more then " + rule.getMaxCount() + "of " + rule.getName());
        }
    }

    private List<Participant> getCandidates(List<Participant> fullList, List<DrawResult> toRemove) {
        return fullList.stream().filter(p -> {
            boolean inPicked = false;
            for (DrawResult drawResult : toRemove) {
                if (drawResult.getParticipantId().equals(p.getId())) {
                    inPicked = true;
                    break;
                }
            }
            return !inPicked;
        }).collect(Collectors.toList());
    }

    private List<Participant> getCandidateWithFPDM(List<Participant> fulList, String fpdm) {
        return fulList.stream().filter(p -> fpdm.equals(p.getFirstName())).collect(Collectors.toList());
    }

}
