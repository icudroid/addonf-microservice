package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.brand.BrandUser;
import com.adloveyou.ms.service.dto.BrandUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BrandUser and its DTO BrandUserDTO.
 */
@Mapper(componentModel = "spring", uses = {BrandMapper.class})
public interface BrandUserMapper extends EntityMapper<BrandUserDTO, BrandUser> {

    @Mapping(source = "brand.id", target = "brandId")
    BrandUserDTO toDto(BrandUser brandUser);

    @Mapping(source = "brandId", target = "brand")
    BrandUser toEntity(BrandUserDTO brandUserDTO);

    default BrandUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        BrandUser brandUser = new BrandUser();
        brandUser.setId(id);
        return brandUser;
    }
}
