package com.adloveyou.ms.domain.rule.configuration;

import com.adloveyou.ms.domain.IMetaData;
import com.adloveyou.ms.domain.rule.AdRule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;


@Entity
public abstract class AdConfiguration extends AdRule {
    @Temporal(TemporalType.DATE)
    protected Date startDate;
    @Temporal(TemporalType.DATE)
    protected Date endDate;
    protected Integer maxDisplayByUser;
    @Column(name = IMetaData.ColumnMetadata.AdRule.QUESTION)
    protected String question;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdConfiguration adRule = (AdConfiguration) o;
        if (adRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
