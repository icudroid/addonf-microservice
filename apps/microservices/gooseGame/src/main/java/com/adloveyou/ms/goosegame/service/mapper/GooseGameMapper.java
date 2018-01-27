package com.adloveyou.ms.goosegame.service.mapper;

import com.adloveyou.ms.goosegame.domain.*;
import com.adloveyou.ms.goosegame.service.dto.GooseGameDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GooseGame and its DTO GooseGameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GooseGameMapper extends EntityMapper<GooseGameDTO, GooseGame> {

    

    @Mapping(target = "levels", ignore = true)
    GooseGame toEntity(GooseGameDTO gooseGameDTO);

    default GooseGame fromId(Long id) {
        if (id == null) {
            return null;
        }
        GooseGame gooseGame = new GooseGame();
        gooseGame.setId(id);
        return gooseGame;
    }
}
