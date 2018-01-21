package com.adloveyou.ms.domain.rule.configuration;

import com.adloveyou.ms.domain.IMetaData;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.PRODUCT_RULE)
public class ProductConfiguration extends AdConfiguration {

    @Override
    public String toString() {
        return "ProductRule";
    }

}
