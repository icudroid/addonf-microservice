package com.adloveyou.ms.domain.rule.configuration;

import com.adloveyou.ms.domain.IMetaData;
import com.adloveyou.ms.domain.brand.Brand;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.BRAND_RULE)
public class BrandConfiguration extends AdConfiguration {
    @ManyToMany
    @JoinTable(
        name = IMetaData.ColumnMetadata.AdRule.BRAND_NO_DISPLAY_TABLE_JOIN,
        joinColumns = {@JoinColumn(name = IMetaData.ColumnMetadata.AdRule.BRAND_RULE_JOIN)},
        inverseJoinColumns = @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.BRAND_JOIN)
    )
    private List<Brand> noDisplayWith = new ArrayList<Brand>();

    public List<Brand> getNoDisplayWith() {
        return noDisplayWith;
    }

    public void setNoDisplayWith(List<Brand> noDisplayWith) {
        this.noDisplayWith = noDisplayWith;
    }

    @Override
    public String toString() {
        return "Brand";
    }

    public void addNoDisplayWith(Brand brand) {
        if (noDisplayWith == null) {
            noDisplayWith = new ArrayList<Brand>();
        }
        noDisplayWith.add(brand);
    }
}
