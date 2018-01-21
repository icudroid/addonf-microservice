package com.adloveyou.ms.service.dto.rule.configuration;

import com.adloveyou.ms.service.dto.AdDTO;
import com.adloveyou.ms.service.dto.rule.AdRuleDTO;

import java.util.Date;


public abstract class AdConfigurationDTO extends AdRuleDTO {
    protected Date startDate;
    protected Date endDate;
    protected Integer maxDisplayByUser;
    protected String question;
    protected AdDTO ad;
    private Boolean activated = true;
    private String name;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMaxDisplayByUser() {
        return maxDisplayByUser;
    }

    public void setMaxDisplayByUser(Integer maxDisplayByUser) {
        this.maxDisplayByUser = maxDisplayByUser;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public AdDTO getAd() {
        return ad;
    }

    @Override
    public void setAd(AdDTO ad) {
        this.ad = ad;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
