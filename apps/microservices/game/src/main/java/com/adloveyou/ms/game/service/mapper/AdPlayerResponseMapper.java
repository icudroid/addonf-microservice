package com.adloveyou.ms.game.service.mapper;

import com.adloveyou.ms.game.domain.*;
import com.adloveyou.ms.game.service.dto.AdPlayerResponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdPlayerResponse and its DTO AdPlayerResponseDTO.
 */
@Mapper(componentModel = "spring", uses = {AdScoreMapper.class, PossibilityMapper.class})
public interface AdPlayerResponseMapper extends EntityMapper<AdPlayerResponseDTO, AdPlayerResponse> {

    @Mapping(source = "adScore.id", target = "adScoreId")
    AdPlayerResponseDTO toDto(AdPlayerResponse adPlayerResponse); 

    @Mapping(source = "adScoreId", target = "adScore")
    AdPlayerResponse toEntity(AdPlayerResponseDTO adPlayerResponseDTO);

    default AdPlayerResponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdPlayerResponse adPlayerResponse = new AdPlayerResponse();
        adPlayerResponse.setId(id);
        return adPlayerResponse;
    }
}
