package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.*;
import com.adloveyou.ms.service.dto.MediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Media and its DTO MediaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MediaMapper extends EntityMapper<MediaDTO, Media> {

    

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "attachements", ignore = true)
    Media toEntity(MediaDTO mediaDTO);

    default Media fromId(Long id) {
        if (id == null) {
            return null;
        }
        Media media = new Media();
        media.setId(id);
        return media;
    }
}
