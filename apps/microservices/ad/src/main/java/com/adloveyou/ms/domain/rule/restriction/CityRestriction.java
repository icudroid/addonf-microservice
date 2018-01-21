package com.adloveyou.ms.domain.rule.restriction;

import com.adloveyou.ms.domain.IMetaData;
import com.adloveyou.ms.domain.country.City;

import javax.persistence.*;

@Entity
@DiscriminatorValue(IMetaData.ColumnMetadata.AdRule.Discrimator.CITY_RULE)
public class CityRestriction extends AdRestriction {
    @ManyToOne
    @JoinColumn(name = IMetaData.ColumnMetadata.AdRule.CITY_JOIN)
    private City city;
    @Column(name = IMetaData.ColumnMetadata.AdRule.AROUND)
    private Integer around;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getAround() {
        return around;
    }

    public void setAround(Integer around) {
        this.around = around;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityRestriction)) return false;
        if (!super.equals(o)) return false;

        CityRestriction cityRestriction = (CityRestriction) o;

        if (city != null ? !city.equals(cityRestriction.city) : cityRestriction.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "CityRule{" +
            "city='" + city + '\'' +
            '}';
    }
}
