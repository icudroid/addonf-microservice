package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.rule.AdRule;
import com.adloveyou.ms.service.dto.AdRuleDTO;

import org.mapstruct.*;

import java.util.List;

public class AdRuleMapper implements EntityMapper<AdRuleDTO, AdRule> {

    @Override
    public AdRule toEntity(AdRuleDTO dto) {
        return null;
    }

    @Override
    public AdRuleDTO toDto(AdRule entity) {
        return null;
    }

    @Override
    public List<AdRule> toEntity(List<AdRuleDTO> dtoList) {
        return null;
    }

    @Override
    public List<AdRuleDTO> toDto(List<AdRule> entityList) {
        return null;
    }

    public AdRuleDTO fromId(Long id) {
        return null;
    }
}
