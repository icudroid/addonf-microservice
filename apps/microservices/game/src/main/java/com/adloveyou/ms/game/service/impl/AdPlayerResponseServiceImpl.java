package com.adloveyou.ms.game.service.impl;

import com.adloveyou.ms.game.service.AdPlayerResponseService;
import com.adloveyou.ms.game.domain.AdPlayerResponse;
import com.adloveyou.ms.game.repository.AdPlayerResponseRepository;
import com.adloveyou.ms.game.repository.search.AdPlayerResponseSearchRepository;
import com.adloveyou.ms.game.service.dto.AdPlayerResponseDTO;
import com.adloveyou.ms.game.service.mapper.AdPlayerResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdPlayerResponse.
 */
@Service
@Transactional
public class AdPlayerResponseServiceImpl implements AdPlayerResponseService{

    private final Logger log = LoggerFactory.getLogger(AdPlayerResponseServiceImpl.class);

    private final AdPlayerResponseRepository adPlayerResponseRepository;

    private final AdPlayerResponseMapper adPlayerResponseMapper;

    private final AdPlayerResponseSearchRepository adPlayerResponseSearchRepository;

    public AdPlayerResponseServiceImpl(AdPlayerResponseRepository adPlayerResponseRepository, AdPlayerResponseMapper adPlayerResponseMapper, AdPlayerResponseSearchRepository adPlayerResponseSearchRepository) {
        this.adPlayerResponseRepository = adPlayerResponseRepository;
        this.adPlayerResponseMapper = adPlayerResponseMapper;
        this.adPlayerResponseSearchRepository = adPlayerResponseSearchRepository;
    }

    /**
     * Save a adPlayerResponse.
     *
     * @param adPlayerResponseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdPlayerResponseDTO save(AdPlayerResponseDTO adPlayerResponseDTO) {
        log.debug("Request to save AdPlayerResponse : {}", adPlayerResponseDTO);
        AdPlayerResponse adPlayerResponse = adPlayerResponseMapper.toEntity(adPlayerResponseDTO);
        adPlayerResponse = adPlayerResponseRepository.save(adPlayerResponse);
        AdPlayerResponseDTO result = adPlayerResponseMapper.toDto(adPlayerResponse);
        adPlayerResponseSearchRepository.save(adPlayerResponse);
        return result;
    }

    /**
     * Get all the adPlayerResponses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdPlayerResponseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdPlayerResponses");
        return adPlayerResponseRepository.findAll(pageable)
            .map(adPlayerResponseMapper::toDto);
    }

    /**
     * Get one adPlayerResponse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdPlayerResponseDTO findOne(Long id) {
        log.debug("Request to get AdPlayerResponse : {}", id);
        AdPlayerResponse adPlayerResponse = adPlayerResponseRepository.findOneWithEagerRelationships(id);
        return adPlayerResponseMapper.toDto(adPlayerResponse);
    }

    /**
     * Delete the adPlayerResponse by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdPlayerResponse : {}", id);
        adPlayerResponseRepository.delete(id);
        adPlayerResponseSearchRepository.delete(id);
    }

    /**
     * Search for the adPlayerResponse corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdPlayerResponseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdPlayerResponses for query {}", query);
        Page<AdPlayerResponse> result = adPlayerResponseSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adPlayerResponseMapper::toDto);
    }
}
