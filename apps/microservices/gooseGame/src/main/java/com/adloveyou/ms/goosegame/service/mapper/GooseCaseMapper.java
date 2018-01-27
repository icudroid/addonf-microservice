package com.adloveyou.ms.goosegame.service.mapper;

import com.adloveyou.ms.goosegame.domain.*;
import com.adloveyou.ms.goosegame.service.dto.GooseCaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GooseCase and its DTO GooseCaseDTO.
 */
@Mapper(componentModel = "spring", uses = {GooseLevelMapper.class})
public interface GooseCaseMapper extends EntityMapper<GooseCaseDTO, GooseCase> {

    @Mapping(source = "gooseLevel.id", target = "gooseLevelId")
    GooseCaseDTO toDto(GooseCase gooseCase); 

    @Mapping(source = "gooseLevelId", target = "gooseLevel")
    GooseCase toEntity(GooseCaseDTO gooseCaseDTO);

    default GooseCase fromId(Long id) {
        if (id == null) {
            return null;
        }
        GooseCase gooseCase = new GooseCase();
        gooseCase.setId(id);
        return gooseCase;
    }
}
