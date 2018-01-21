package com.adloveyou.ms.service.mapper;

import com.adloveyou.ms.domain.ad.Product;
import com.adloveyou.ms.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {AdCampaingMapper.class, BrandMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "adCampaing.id", target = "adCampaingId")
    @Mapping(source = "brand.id", target = "brandId")
    ProductDTO toDto(Product product);

    @Mapping(source = "adCampaingId", target = "adCampaing")
    @Mapping(source = "brandId", target = "brand")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
