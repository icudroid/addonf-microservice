package com.adloveyou.uaa.service.mapper;

import com.adloveyou.uaa.domain.*;
import com.adloveyou.uaa.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    

    

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
