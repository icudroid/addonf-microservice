package com.adloveyou.ms.goosegame.service.mapper;

import com.adloveyou.ms.goosegame.domain.*;
import com.adloveyou.ms.goosegame.service.dto.GooseTokenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GooseToken and its DTO GooseTokenDTO.
 */
@Mapper(componentModel = "spring", uses = {GooseCaseMapper.class})
public interface GooseTokenMapper extends EntityMapper<GooseTokenDTO, GooseToken> {

    @Mapping(source = "where.id", target = "whereId")
    GooseTokenDTO toDto(GooseToken gooseToken); 

    @Mapping(source = "whereId", target = "where")
    GooseToken toEntity(GooseTokenDTO gooseTokenDTO);

    default GooseToken fromId(Long id) {
        if (id == null) {
            return null;
        }
        GooseToken gooseToken = new GooseToken();
        gooseToken.setId(id);
        return gooseToken;
    }
}
