package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.CategoryService;
import com.adloveyou.ms.domain.Category;
import com.adloveyou.ms.repository.CategoryRepository;
import com.adloveyou.ms.repository.search.CategorySearchRepository;
import com.adloveyou.ms.service.dto.CategoryDTO;
import com.adloveyou.ms.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final CategorySearchRepository categorySearchRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategorySearchRepository categorySearchRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categorySearchRepository = categorySearchRepository;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        categorySearchRepository.save(category);
        return result;
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }

    /**
     * Get one category by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        return categoryMapper.toDto(category);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
        categorySearchRepository.delete(id);
    }

    /**
     * Search for the category corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Categories for query {}", query);
        Page<Category> result = categorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(categoryMapper::toDto);
    }
}
