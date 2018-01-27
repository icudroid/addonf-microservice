package com.adloveyou.ms.game.service.mapper;

import com.adloveyou.ms.game.domain.*;
import com.adloveyou.ms.game.service.dto.AdChoiseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdChoise and its DTO AdChoiseDTO.
 */
@Mapper(componentModel = "spring", uses = {AdGameMapper.class})
public interface AdChoiseMapper extends EntityMapper<AdChoiseDTO, AdChoise> {

    @Mapping(source = "adGame.id", target = "adGameId")
    AdChoiseDTO toDto(AdChoise adChoise); 

    @Mapping(source = "adGameId", target = "adGame")
    @Mapping(target = "corrects", ignore = true)
    @Mapping(target = "possiblities", ignore = true)
    AdChoise toEntity(AdChoiseDTO adChoiseDTO);

    default AdChoise fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdChoise adChoise = new AdChoise();
        adChoise.setId(id);
        return adChoise;
    }
}
