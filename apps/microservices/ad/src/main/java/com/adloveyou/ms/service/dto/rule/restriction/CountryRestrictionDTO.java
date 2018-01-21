package com.adloveyou.ms.service.dto.rule.restriction;

import com.adloveyou.ms.service.dto.CountryDTO;

public class CountryRestrictionDTO extends AdRestrictionDTO {
    private CountryDTO country;

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }
}
