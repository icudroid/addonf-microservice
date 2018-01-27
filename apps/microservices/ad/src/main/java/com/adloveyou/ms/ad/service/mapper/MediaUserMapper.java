package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.MediaUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MediaUser and its DTO MediaUserDTO.
 */
@Mapper(componentModel = "spring", uses = {MediaMapper.class})
public interface MediaUserMapper extends EntityMapper<MediaUserDTO, MediaUser> {

    @Mapping(source = "media.id", target = "mediaId")
    MediaUserDTO toDto(MediaUser mediaUser);

    @Mapping(source = "mediaId", target = "media")
    MediaUser toEntity(MediaUserDTO mediaUserDTO);

    default MediaUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        MediaUser mediaUser = new MediaUser();
        mediaUser.setId(id);
        return mediaUser;
    }
}
