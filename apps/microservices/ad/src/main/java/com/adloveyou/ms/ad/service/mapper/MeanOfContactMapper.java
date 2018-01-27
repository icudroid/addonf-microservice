package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.MeanOfContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MeanOfContact and its DTO MeanOfContactDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface MeanOfContactMapper extends EntityMapper<MeanOfContactDTO, MeanOfContact> {

    @Mapping(source = "contact.id", target = "contactId")
    MeanOfContactDTO toDto(MeanOfContact meanOfContact);

    @Mapping(source = "contactId", target = "contact")
    MeanOfContact toEntity(MeanOfContactDTO meanOfContactDTO);

    default MeanOfContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeanOfContact meanOfContact = new MeanOfContact();
        meanOfContact.setId(id);
        return meanOfContact;
    }
}
