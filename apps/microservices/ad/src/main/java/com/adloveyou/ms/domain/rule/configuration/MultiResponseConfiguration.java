package com.adloveyou.ms.domain.rule.configuration;

import com.adloveyou.ms.domain.IMetaData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.MULTI_RESPONSE_RULE)
public class MultiResponseConfiguration extends AdConfiguration {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.MULTI_RESPONSE_JOIN)
    private List<AdResponseConfiguration> responses;
    private String addonText;
    private String BtnValidText;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.RESPONSE_CORRECT_JOIN)
    private List<AdResponseConfiguration> corrects;

    public List<AdResponseConfiguration> getResponses() {
        return responses;
    }

    public void setResponses(List<AdResponseConfiguration> responses) {
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

    public List<AdResponseConfiguration> getCorrects() {
        return corrects;
    }

    public void setCorrects(List<AdResponseConfiguration> corrects) {
        this.corrects = corrects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiResponseConfiguration)) return false;
        if (!super.equals(o)) return false;

        MultiResponseConfiguration openRule = (MultiResponseConfiguration) o;

        if (responses != null ? !responses.equals(openRule.responses) : openRule.responses != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (responses != null ? responses.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpenRule{" +
            "responses=" + responses +
            '}';
    }

    public void addResponse(AdResponseConfiguration adResponseConfiguration) {
        if (responses == null) {
            responses = new ArrayList<AdResponseConfiguration>();
        }
        responses.add(adResponseConfiguration);
    }
}
