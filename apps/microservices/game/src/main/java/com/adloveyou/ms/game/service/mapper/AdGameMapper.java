package com.adloveyou.ms.game.service.mapper;

import com.adloveyou.ms.game.domain.*;
import com.adloveyou.ms.game.service.dto.AdGameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdGame and its DTO AdGameDTO.
 */
@Mapper(componentModel = "spring", uses = {AdScoreMapper.class})
public interface AdGameMapper extends EntityMapper<AdGameDTO, AdGame> {

    @Mapping(source = "score.id", target = "scoreId")
    AdGameDTO toDto(AdGame adGame); 

    @Mapping(source = "scoreId", target = "score")
    @Mapping(target = "choises", ignore = true)
    AdGame toEntity(AdGameDTO adGameDTO);

    default AdGame fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdGame adGame = new AdGame();
        adGame.setId(id);
        return adGame;
    }
}
