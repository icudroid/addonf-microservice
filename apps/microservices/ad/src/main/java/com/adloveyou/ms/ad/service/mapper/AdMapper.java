package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.AdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ad and its DTO AdDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class, SectorMapper.class, AgencyMapper.class})
public interface AdMapper extends EntityMapper<AdDTO, Ad> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "providedBy.id", target = "providedById")
    AdDTO toDto(Ad ad);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "configs", ignore = true)
    @Mapping(target = "restrictions", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "sectorId", target = "sector")
    @Mapping(source = "providedById", target = "providedBy")
    Ad toEntity(AdDTO adDTO);

    default Ad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ad ad = new Ad();
        ad.setId(id);
        return ad;
    }
}
