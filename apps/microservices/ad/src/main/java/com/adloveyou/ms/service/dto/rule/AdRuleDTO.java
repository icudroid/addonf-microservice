package com.adloveyou.ms.service.dto.rule;

import com.adloveyou.ms.service.dto.AdDTO;

import java.io.Serializable;

public abstract class AdRuleDTO implements Serializable {
    protected Long id;
    protected AdDTO ad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdDTO getAd() {
        return ad;
    }

    public void setAd(AdDTO ad) {
        this.ad = ad;
    }
}
