package com.adloveyou.ms.service.dto.rule.configuration;

import java.util.List;

public class MultiResponseConfigurationDTO extends AdConfigurationDTO {
    private List<AdResponseConfigurationDTO> responses;
    private String addonText;
    private String BtnValidText;
    private List<AdResponseConfigurationDTO> corrects;

    public List<AdResponseConfigurationDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<AdResponseConfigurationDTO> responses) {
        this.responses = responses;
    }

    public String getAddonText() {
        return addonText;
    }

    public void setAddonText(String addonText) {
        this.addonText = addonText;
    }

    public String getBtnValidText() {
        return BtnValidText;
    }

    public void setBtnValidText(String btnValidText) {
        BtnValidText = btnValidText;
    }

    public List<AdResponseConfigurationDTO> getCorrects() {
        return corrects;
    }

    public void setCorrects(List<AdResponseConfigurationDTO> corrects) {
        this.corrects = corrects;
    }
}
