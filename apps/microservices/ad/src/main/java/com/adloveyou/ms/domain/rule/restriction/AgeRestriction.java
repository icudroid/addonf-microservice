package com.adloveyou.ms.domain.rule.restriction;

import com.adloveyou.ms.domain.IMetaData;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.AGE_RULE)
public class AgeRestriction extends AdRestriction {

    @Column(name = IMetaData.ColumnMetadata.AdRule.AGE_MIN)
    private Integer ageMin;
    @Column(name = IMetaData.ColumnMetadata.AdRule.AGE_MAX)
    private Integer ageMax;

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgeRestriction)) return false;
        if (!super.equals(o)) return false;

        AgeRestriction ageRestriction = (AgeRestriction) o;

        if (ageMax != null ? !ageMax.equals(ageRestriction.ageMax) : ageRestriction.ageMax != null) return false;
        if (ageMin != null ? !ageMin.equals(ageRestriction.ageMin) : ageRestriction.ageMin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ageMin != null ? ageMin.hashCode() : 0);
        result = 31 * result + (ageMax != null ? ageMax.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AgeRule{" +
            "ageMin=" + ageMin +
            ", ageMax=" + ageMax +
            '}';
    }
}
