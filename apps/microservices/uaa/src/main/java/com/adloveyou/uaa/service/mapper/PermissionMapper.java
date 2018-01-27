package com.adloveyou.uaa.service.mapper;

import com.adloveyou.uaa.domain.*;
import com.adloveyou.uaa.service.dto.PermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Permission and its DTO PermissionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

    

    

    default Permission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }
}
