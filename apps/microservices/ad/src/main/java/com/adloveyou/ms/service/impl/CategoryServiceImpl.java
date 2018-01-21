package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.CategoryService;
import com.adloveyou.ms.domain.ad.Category;
import com.adloveyou.ms.service.dto.CategoryDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl extends GenericServiceWithDTOImpl<Category, CategoryDTO, Long> implements CategoryService{

    public CategoryServiceImpl(EntityMapper<CategoryDTO, Category> mapper, ElasticsearchRepository<Category, Long> elasticsearchRepository, JpaRepository<Category, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
