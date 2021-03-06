package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.AgencyUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AgencyUser and its DTO AgencyUserDTO.
 */
@Mapper(componentModel = "spring", uses = {AgencyMapper.class})
public interface AgencyUserMapper extends EntityMapper<AgencyUserDTO, AgencyUser> {

    @Mapping(source = "agency.id", target = "agencyId")
    AgencyUserDTO toDto(AgencyUser agencyUser);

    @Mapping(source = "agencyId", target = "agency")
    AgencyUser toEntity(AgencyUserDTO agencyUserDTO);

    default AgencyUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AgencyUser agencyUser = new AgencyUser();
        agencyUser.setId(id);
        return agencyUser;
    }
}
