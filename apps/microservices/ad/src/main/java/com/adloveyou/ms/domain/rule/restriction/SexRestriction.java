package com.adloveyou.ms.domain.rule.restriction;

import com.adloveyou.ms.domain.IMetaData;
import com.adloveyou.ms.domain.enumeration.Sex;

import javax.persistence.*;

@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.SEX_RULE)
public class SexRestriction extends AdRestriction {
    @Enumerated(EnumType.STRING)
    @Column(name = IMetaData.ColumnMetadata.AdRule.SEX)
    private Sex sex;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "SexRule{" +
            "sex=" + sex +
            '}';
    }
}
