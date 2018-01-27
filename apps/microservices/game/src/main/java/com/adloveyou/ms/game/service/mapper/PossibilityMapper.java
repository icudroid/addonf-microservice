package com.adloveyou.ms.game.service.mapper;

import com.adloveyou.ms.game.domain.*;
import com.adloveyou.ms.game.service.dto.PossibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Possibility and its DTO PossibilityDTO.
 */
@Mapper(componentModel = "spring", uses = {AdChoiseMapper.class})
public interface PossibilityMapper extends EntityMapper<PossibilityDTO, Possibility> {

    @Mapping(source = "correct.id", target = "correctId")
    @Mapping(source = "possible.id", target = "possibleId")
    PossibilityDTO toDto(Possibility possibility); 

    @Mapping(source = "correctId", target = "correct")
    @Mapping(source = "possibleId", target = "possible")
    Possibility toEntity(PossibilityDTO possibilityDTO);

    default Possibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        Possibility possibility = new Possibility();
        possibility.setId(id);
        return possibility;
    }
}
