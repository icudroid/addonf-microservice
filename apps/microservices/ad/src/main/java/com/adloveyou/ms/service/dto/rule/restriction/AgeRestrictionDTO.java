package com.adloveyou.ms.service.dto.rule.restriction;

public class AgeRestrictionDTO extends AdRestrictionDTO {
    private Integer ageMin;
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
}
