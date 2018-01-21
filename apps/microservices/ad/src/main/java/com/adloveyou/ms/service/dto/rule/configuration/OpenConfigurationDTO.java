package com.adloveyou.ms.service.dto.rule.configuration;

import java.util.List;

public class OpenConfigurationDTO extends AdConfigurationDTO {
    private List<AdResponseConfigurationDTO> responses;
    private AdResponseConfigurationDTO correct;

    public List<AdResponseConfigurationDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<AdResponseConfigurationDTO> responses) {
        this.responses = responses;
    }

    public AdResponseConfigurationDTO getCorrect() {
        return correct;
    }

    public void setCorrect(AdResponseConfigurationDTO correct) {
        this.correct = correct;
    }
}
