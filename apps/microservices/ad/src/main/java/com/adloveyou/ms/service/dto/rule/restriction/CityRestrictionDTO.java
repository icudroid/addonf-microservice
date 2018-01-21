package com.adloveyou.ms.service.dto.rule.restriction;

import com.adloveyou.ms.service.dto.CityDTO;

public class CityRestrictionDTO extends AdRestrictionDTO {
    private CityDTO city;
    private Integer around;

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public Integer getAround() {
        return around;
    }

    public void setAround(Integer around) {
        this.around = around;
    }
}
