package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.AgencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agency and its DTO AgencyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencyMapper extends EntityMapper<AgencyDTO, Agency> {


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "attachements", ignore = true)
    Agency toEntity(AgencyDTO agencyDTO);

    default Agency fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agency agency = new Agency();
        agency.setId(id);
        return agency;
    }
}
