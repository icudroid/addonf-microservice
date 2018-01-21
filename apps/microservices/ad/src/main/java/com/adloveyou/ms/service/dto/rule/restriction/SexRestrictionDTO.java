package com.adloveyou.ms.service.dto.rule.restriction;

import com.adloveyou.ms.domain.enumeration.Sex;

public class SexRestrictionDTO extends AdRestrictionDTO {
    private Sex sex;

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

}
