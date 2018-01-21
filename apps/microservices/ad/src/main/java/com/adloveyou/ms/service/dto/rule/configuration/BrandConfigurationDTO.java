package com.adloveyou.ms.service.dto.rule.configuration;

import com.adloveyou.ms.service.dto.BrandDTO;

import java.util.ArrayList;
import java.util.List;


public class BrandConfigurationDTO extends AdConfigurationDTO {
    private List<BrandDTO> noDisplayWith = new ArrayList<BrandDTO>();

    public List<BrandDTO> getNoDisplayWith() {
        return noDisplayWith;
    }

    public void setNoDisplayWith(List<BrandDTO> noDisplayWith) {
        this.noDisplayWith = noDisplayWith;
    }
}
