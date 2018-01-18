package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.*;
import com.adloveyou.ms.service.dto.AdRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdRule and its DTO AdRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {AdMapper.class})
public interface AdRuleMapper extends EntityMapper<AdRuleDTO, AdRule> {

    @Mapping(source = "ad.id", target = "adId")
    AdRuleDTO toDto(AdRule adRule); 

    @Mapping(source = "adId", target = "ad")
    AdRule toEntity(AdRuleDTO adRuleDTO);

    default AdRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdRule adRule = new AdRule();
        adRule.setId(id);
        return adRule;
    }
}
