package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.ad.Ad;
import com.adloveyou.ms.service.dto.AdDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ad and its DTO AdDTO.
 */
@Mapper(componentModel = "spring", uses = {AdCampaingMapper.class})
public interface AdMapper extends EntityMapper<AdDTO, Ad> {

    @Mapping(source = "adCampaing.id", target = "adCampaingId")
    AdDTO toDto(Ad ad);

    @Mapping(source = "adCampaingId", target = "adCampaing")
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "rules", ignore = true)
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
