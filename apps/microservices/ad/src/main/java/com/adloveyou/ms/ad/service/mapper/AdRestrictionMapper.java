package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.AdRestrictionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdRestriction and its DTO AdRestrictionDTO.
 */
@Mapper(componentModel = "spring", uses = {AdMapper.class})
public interface AdRestrictionMapper extends EntityMapper<AdRestrictionDTO, AdRestriction> {

    @Mapping(source = "ad.id", target = "adId")
    AdRestrictionDTO toDto(AdRestriction adRestriction);

    @Mapping(source = "adId", target = "ad")
    AdRestriction toEntity(AdRestrictionDTO adRestrictionDTO);

    default AdRestriction fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdRestriction adRestriction = new AdRestriction();
        adRestriction.setId(id);
        return adRestriction;
    }
}
