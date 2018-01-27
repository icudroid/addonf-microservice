package com.adloveyou.ms.game.service.impl;

import com.adloveyou.ms.game.service.AdChoiseService;
import com.adloveyou.ms.game.domain.AdChoise;
import com.adloveyou.ms.game.repository.AdChoiseRepository;
import com.adloveyou.ms.game.repository.search.AdChoiseSearchRepository;
import com.adloveyou.ms.game.service.dto.AdChoiseDTO;
import com.adloveyou.ms.game.service.mapper.AdChoiseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdChoise.
 */
@Service
@Transactional
public class AdChoiseServiceImpl implements AdChoiseService{

    private final Logger log = LoggerFactory.getLogger(AdChoiseServiceImpl.class);

    private final AdChoiseRepository adChoiseRepository;

    private final AdChoiseMapper adChoiseMapper;

    private final AdChoiseSearchRepository adChoiseSearchRepository;

    public AdChoiseServiceImpl(AdChoiseRepository adChoiseRepository, AdChoiseMapper adChoiseMapper, AdChoiseSearchRepository adChoiseSearchRepository) {
        this.adChoiseRepository = adChoiseRepository;
        this.adChoiseMapper = adChoiseMapper;
        this.adChoiseSearchRepository = adChoiseSearchRepository;
    }

    /**
     * Save a adChoise.
     *
     * @param adChoiseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdChoiseDTO save(AdChoiseDTO adChoiseDTO) {
        log.debug("Request to save AdChoise : {}", adChoiseDTO);
        AdChoise adChoise = adChoiseMapper.toEntity(adChoiseDTO);
        adChoise = adChoiseRepository.save(adChoise);
        AdChoiseDTO result = adChoiseMapper.toDto(adChoise);
        adChoiseSearchRepository.save(adChoise);
        return result;
    }

    /**
     * Get all the adChoises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdChoiseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdChoises");
        return adChoiseRepository.findAll(pageable)
            .map(adChoiseMapper::toDto);
    }

    /**
     * Get one adChoise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdChoiseDTO findOne(Long id) {
        log.debug("Request to get AdChoise : {}", id);
        AdChoise adChoise = adChoiseRepository.findOne(id);
        return adChoiseMapper.toDto(adChoise);
    }

    /**
     * Delete the adChoise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdChoise : {}", id);
        adChoiseRepository.delete(id);
        adChoiseSearchRepository.delete(id);
    }

    /**
     * Search for the adChoise corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdChoiseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdChoises for query {}", query);
        Page<AdChoise> result = adChoiseSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adChoiseMapper::toDto);
    }
}
