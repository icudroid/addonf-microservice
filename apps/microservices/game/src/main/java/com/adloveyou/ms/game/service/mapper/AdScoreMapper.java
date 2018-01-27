package com.adloveyou.ms.game.service.mapper;

import com.adloveyou.ms.game.domain.*;
import com.adloveyou.ms.game.service.dto.AdScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AdScore and its DTO AdScoreDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdScoreMapper extends EntityMapper<AdScoreDTO, AdScore> {

    

    @Mapping(target = "answers", ignore = true)
    AdScore toEntity(AdScoreDTO adScoreDTO);

    default AdScore fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdScore adScore = new AdScore();
        adScore.setId(id);
        return adScore;
    }
}
