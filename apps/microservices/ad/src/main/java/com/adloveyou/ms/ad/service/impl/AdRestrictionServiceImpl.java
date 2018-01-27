package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.AdRestrictionService;
import com.adloveyou.ms.ad.domain.AdRestriction;
import com.adloveyou.ms.ad.repository.AdRestrictionRepository;
import com.adloveyou.ms.ad.repository.search.AdRestrictionSearchRepository;
import com.adloveyou.ms.ad.service.dto.AdRestrictionDTO;
import com.adloveyou.ms.ad.service.mapper.AdRestrictionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdRestriction.
 */
@Service
@Transactional
public class AdRestrictionServiceImpl implements AdRestrictionService {

    private final Logger log = LoggerFactory.getLogger(AdRestrictionServiceImpl.class);

    private final AdRestrictionRepository adRestrictionRepository;

    private final AdRestrictionMapper adRestrictionMapper;

    private final AdRestrictionSearchRepository adRestrictionSearchRepository;

    public AdRestrictionServiceImpl(AdRestrictionRepository adRestrictionRepository, AdRestrictionMapper adRestrictionMapper, AdRestrictionSearchRepository adRestrictionSearchRepository) {
        this.adRestrictionRepository = adRestrictionRepository;
        this.adRestrictionMapper = adRestrictionMapper;
        this.adRestrictionSearchRepository = adRestrictionSearchRepository;
    }

    /**
     * Save a adRestriction.
     *
     * @param adRestrictionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdRestrictionDTO save(AdRestrictionDTO adRestrictionDTO) {
        log.debug("Request to save AdRestriction : {}", adRestrictionDTO);
        AdRestriction adRestriction = adRestrictionMapper.toEntity(adRestrictionDTO);
        adRestriction = adRestrictionRepository.save(adRestriction);
        AdRestrictionDTO result = adRestrictionMapper.toDto(adRestriction);
        adRestrictionSearchRepository.save(adRestriction);
        return result;
    }

    /**
     * Get all the adRestrictions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdRestrictionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdRestrictions");
        return adRestrictionRepository.findAll(pageable)
            .map(adRestrictionMapper::toDto);
    }

    /**
     * Get one adRestriction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdRestrictionDTO findOne(Long id) {
        log.debug("Request to get AdRestriction : {}", id);
        AdRestriction adRestriction = adRestrictionRepository.findOne(id);
        return adRestrictionMapper.toDto(adRestriction);
    }

    /**
     * Delete the adRestriction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdRestriction : {}", id);
        adRestrictionRepository.delete(id);
        adRestrictionSearchRepository.delete(id);
    }

    /**
     * Search for the adRestriction corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdRestrictionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdRestrictions for query {}", query);
        Page<AdRestriction> result = adRestrictionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adRestrictionMapper::toDto);
    }
}
