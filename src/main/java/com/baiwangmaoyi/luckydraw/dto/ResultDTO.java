package com.baiwangmaoyi.luckydraw.dto;

public class ResultDTO {

    private Long drawResultId;

    private Long roundId;

    private String ruleName;

    private String participantName;

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public Long getDrawResultId() {
        return drawResultId;
    }

    public void setDrawResultId(Long drawResultId) {
        this.drawResultId = drawResultId;
    }
}
