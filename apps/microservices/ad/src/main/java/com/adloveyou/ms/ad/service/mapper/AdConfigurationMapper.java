package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.AdConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdConfiguration and its DTO AdConfigurationDTO.
 */
@Mapper(componentModel = "spring", uses = {AdMapper.class})
public interface AdConfigurationMapper extends EntityMapper<AdConfigurationDTO, AdConfiguration> {

    @Mapping(source = "ad.id", target = "adId")
    AdConfigurationDTO toDto(AdConfiguration adConfiguration);

    @Mapping(source = "adId", target = "ad")
    AdConfiguration toEntity(AdConfigurationDTO adConfigurationDTO);

    default AdConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdConfiguration adConfiguration = new AdConfiguration();
        adConfiguration.setId(id);
        return adConfiguration;
    }
}
