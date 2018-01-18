package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.*;
import com.adloveyou.ms.service.dto.FileAttachementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FileAttachement and its DTO FileAttachementDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class, AgencyMapper.class, MediaMapper.class})
public interface FileAttachementMapper extends EntityMapper<FileAttachementDTO, FileAttachement> {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "agency.id", target = "agencyId")
    @Mapping(source = "media.id", target = "mediaId")
    FileAttachementDTO toDto(FileAttachement fileAttachement); 

    @Mapping(source = "brandId", target = "brand")
    @Mapping(source = "agencyId", target = "agency")
    @Mapping(source = "mediaId", target = "media")
    FileAttachement toEntity(FileAttachementDTO fileAttachementDTO);

    default FileAttachement fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileAttachement fileAttachement = new FileAttachement();
        fileAttachement.setId(id);
        return fileAttachement;
    }
}
