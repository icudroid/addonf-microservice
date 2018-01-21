package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.ad.AdCampaing;
import com.adloveyou.ms.service.dto.AdCampaingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdCampaing and its DTO AdCampaingDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class, SectorMapper.class, AgencyMapper.class})
public interface AdCampaingMapper extends EntityMapper<AdCampaingDTO, AdCampaing> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "providedBy.id", target = "providedById")
    AdCampaingDTO toDto(AdCampaing adCampaing);

    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "sectorId", target = "sector")
    @Mapping(source = "providedById", target = "providedBy")
    AdCampaing toEntity(AdCampaingDTO adCampaingDTO);

    default AdCampaing fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdCampaing adCampaing = new AdCampaing();
        adCampaing.setId(id);
        return adCampaing;
    }
}
