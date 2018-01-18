package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.*;
import com.adloveyou.ms.service.dto.MediaTargetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MediaTarget and its DTO MediaTargetDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class})
public interface MediaTargetMapper extends EntityMapper<MediaTargetDTO, MediaTarget> {

    @Mapping(source = "brand.id", target = "brandId")
    MediaTargetDTO toDto(MediaTarget mediaTarget); 

    @Mapping(source = "brandId", target = "brand")
    MediaTarget toEntity(MediaTargetDTO mediaTargetDTO);

    default MediaTarget fromId(Long id) {
        if (id == null) {
            return null;
        }
        MediaTarget mediaTarget = new MediaTarget();
        mediaTarget.setId(id);
        return mediaTarget;
    }
}
