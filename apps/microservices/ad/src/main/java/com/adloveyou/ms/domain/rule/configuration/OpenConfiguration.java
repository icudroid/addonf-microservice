package com.adloveyou.ms.domain.rule.configuration;

import com.adloveyou.ms.domain.IMetaData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.OPEN_RULE)
public class OpenConfiguration extends AdConfiguration {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.RESPONSE_JOIN)
    private List<AdResponseConfiguration> responses;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.CORRECT_RESPONSE)
    private AdResponseConfiguration correct;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpenConfiguration)) return false;
        if (!super.equals(o)) return false;

        OpenConfiguration openConfiguration = (OpenConfiguration) o;

        if (correct != null ? !correct.equals(openConfiguration.correct) : openConfiguration.correct != null)
            return false;
        if (responses != null ? !responses.equals(openConfiguration.responses) : openConfiguration.responses != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (responses != null ? responses.hashCode() : 0);
        result = 31 * result + (correct != null ? correct.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OpenRule{" +
            "responses=" + responses +
            ", correct=" + correct +
            '}';
    }

    public void addResponse(AdResponseConfiguration adResponseConfiguration) {
        if (responses == null) {
            responses = new ArrayList<AdResponseConfiguration>();
        }
        responses.add(adResponseConfiguration);
    }

    public List<AdResponseConfiguration> getResponses() {
        return responses;
    }

    public void setResponses(List<AdResponseConfiguration> responses) {
        this.responses = responses;
    }

    public AdResponseConfiguration getCorrect() {
        return correct;
    }

    public void setCorrect(AdResponseConfiguration correct) {
        this.correct = correct;
    }
}
