package com.adloveyou.ms.domain.rule.restriction;

import com.adloveyou.ms.domain.IMetaData;
import com.adloveyou.ms.domain.country.Country;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.COUNTRY_RULE)
public class CountryRestriction extends AdRestriction {
    @ManyToOne()
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.JOIN_COUNTRY)
    private Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryRestriction)) return false;
        if (!super.equals(o)) return false;

        CountryRestriction that = (CountryRestriction) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "CountryRule{" +
            "country=" + country +
            '}';
    }
}
