package com.adloveyou.ms.goosegame.service.mapper;

import com.adloveyou.ms.goosegame.domain.*;
import com.adloveyou.ms.goosegame.service.dto.GooseWinDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GooseWin and its DTO GooseWinDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GooseWinMapper extends EntityMapper<GooseWinDTO, GooseWin> {

    

    

    default GooseWin fromId(Long id) {
        if (id == null) {
            return null;
        }
        GooseWin gooseWin = new GooseWin();
        gooseWin.setId(id);
        return gooseWin;
    }
}
