package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.SectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sector and its DTO SectorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SectorMapper extends EntityMapper<SectorDTO, Sector> {



    default Sector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sector sector = new Sector();
        sector.setId(id);
        return sector;
    }
}
