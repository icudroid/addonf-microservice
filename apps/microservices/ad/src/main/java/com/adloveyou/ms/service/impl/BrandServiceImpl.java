package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.BrandService;
import com.adloveyou.ms.domain.Brand;
import com.adloveyou.ms.repository.BrandRepository;
import com.adloveyou.ms.repository.search.BrandSearchRepository;
import com.adloveyou.ms.service.dto.BrandDTO;
import com.adloveyou.ms.service.mapper.BrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Brand.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService{

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    private final BrandSearchRepository brandSearchRepository;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper, BrandSearchRepository brandSearchRepository) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
        this.brandSearchRepository = brandSearchRepository;
    }

    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        log.debug("Request to save Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        BrandDTO result = brandMapper.toDto(brand);
        brandSearchRepository.save(brand);
        return result;
    }

    /**
     * Get all the brands.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Brands");
        return brandRepository.findAll(pageable)
            .map(brandMapper::toDto);
    }

    /**
     * Get one brand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BrandDTO findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        Brand brand = brandRepository.findOne(id);
        return brandMapper.toDto(brand);
    }

    /**
     * Delete the brand by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.delete(id);
        brandSearchRepository.delete(id);
    }

    /**
     * Search for the brand corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Brands for query {}", query);
        Page<Brand> result = brandSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(brandMapper::toDto);
    }
}
