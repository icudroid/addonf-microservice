package com.adloveyou.ms.ad.service.mapper;

import com.adloveyou.ms.ad.domain.*;
import com.adloveyou.ms.ad.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "main.id", target = "mainId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "mainId", target = "main")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
