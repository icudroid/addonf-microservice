package com.adloveyou.ms.goosegame.service.mapper;

import com.adloveyou.ms.goosegame.domain.*;
import com.adloveyou.ms.goosegame.service.dto.GooseLevelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GooseLevel and its DTO GooseLevelDTO.
 */
@Mapper(componentModel = "spring", uses = {GooseGameMapper.class, GooseCaseMapper.class})
public interface GooseLevelMapper extends EntityMapper<GooseLevelDTO, GooseLevel> {

    @Mapping(source = "gooseGame.id", target = "gooseGameId")
    @Mapping(source = "start.id", target = "startId")
    @Mapping(source = "end.id", target = "endId")
    GooseLevelDTO toDto(GooseLevel gooseLevel); 

    @Mapping(source = "gooseGameId", target = "gooseGame")
    @Mapping(source = "startId", target = "start")
    @Mapping(source = "endId", target = "end")
    @Mapping(target = "cases", ignore = true)
    GooseLevel toEntity(GooseLevelDTO gooseLevelDTO);

    default GooseLevel fromId(Long id) {
        if (id == null) {
            return null;
        }
        GooseLevel gooseLevel = new GooseLevel();
        gooseLevel.setId(id);
        return gooseLevel;
    }
}
