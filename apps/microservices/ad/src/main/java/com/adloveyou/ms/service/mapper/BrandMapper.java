package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.brand.Brand;
import com.adloveyou.ms.service.dto.BrandDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Brand and its DTO BrandDTO.
 */
@Mapper(componentModel = "spring", uses = {SectorMapper.class})
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {

    @Mapping(source = "sector.id", target = "sectorId")
    BrandDTO toDto(Brand brand);

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "targets", ignore = true)
    @Mapping(target = "targetsMedias", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "attachements", ignore = true)
    @Mapping(source = "sectorId", target = "sector")
    Brand toEntity(BrandDTO brandDTO);

    default Brand fromId(Long id) {
        if (id == null) {
            return null;
        }
        Brand brand = new Brand();
        brand.setId(id);
        return brand;
    }
}
