package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.ad.BidCategoryMedia;
import com.adloveyou.ms.service.dto.BidCategoryMediaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BidCategoryMedia and its DTO BidCategoryMediaDTO.
 */
@Mapper(componentModel = "spring", uses = {AdMapper.class, CategoryMapper.class, MediaMapper.class})
public interface BidCategoryMediaMapper extends EntityMapper<BidCategoryMediaDTO, BidCategoryMedia> {

    @Mapping(source = "ad.id", target = "adId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "media.id", target = "mediaId")
    BidCategoryMediaDTO toDto(BidCategoryMedia bidCategoryMedia);

    @Mapping(source = "adId", target = "ad")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "mediaId", target = "media")
    BidCategoryMedia toEntity(BidCategoryMediaDTO bidCategoryMediaDTO);

    default BidCategoryMedia fromId(Long id) {
        if (id == null) {
            return null;
        }
        BidCategoryMedia bidCategoryMedia = new BidCategoryMedia();
        bidCategoryMedia.setId(id);
        return bidCategoryMedia;
    }
}
