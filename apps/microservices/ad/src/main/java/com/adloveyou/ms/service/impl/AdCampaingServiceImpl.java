package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AdCampaingService;
import com.adloveyou.ms.domain.AdCampaing;
import com.adloveyou.ms.repository.AdCampaingRepository;
import com.adloveyou.ms.repository.search.AdCampaingSearchRepository;
import com.adloveyou.ms.service.dto.AdCampaingDTO;
import com.adloveyou.ms.service.mapper.AdCampaingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdCampaing.
 */
@Service
@Transactional
public class AdCampaingServiceImpl implements AdCampaingService{

    private final Logger log = LoggerFactory.getLogger(AdCampaingServiceImpl.class);

    private final AdCampaingRepository adCampaingRepository;

    private final AdCampaingMapper adCampaingMapper;

    private final AdCampaingSearchRepository adCampaingSearchRepository;

    public AdCampaingServiceImpl(AdCampaingRepository adCampaingRepository, AdCampaingMapper adCampaingMapper, AdCampaingSearchRepository adCampaingSearchRepository) {
        this.adCampaingRepository = adCampaingRepository;
        this.adCampaingMapper = adCampaingMapper;
        this.adCampaingSearchRepository = adCampaingSearchRepository;
    }

    /**
     * Save a adCampaing.
     *
     * @param adCampaingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdCampaingDTO save(AdCampaingDTO adCampaingDTO) {
        log.debug("Request to save AdCampaing : {}", adCampaingDTO);
        AdCampaing adCampaing = adCampaingMapper.toEntity(adCampaingDTO);
        adCampaing = adCampaingRepository.save(adCampaing);
        AdCampaingDTO result = adCampaingMapper.toDto(adCampaing);
        adCampaingSearchRepository.save(adCampaing);
        return result;
    }

    /**
     * Get all the adCampaings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdCampaingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdCampaings");
        return adCampaingRepository.findAll(pageable)
            .map(adCampaingMapper::toDto);
    }

    /**
     * Get one adCampaing by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdCampaingDTO findOne(Long id) {
        log.debug("Request to get AdCampaing : {}", id);
        AdCampaing adCampaing = adCampaingRepository.findOne(id);
        return adCampaingMapper.toDto(adCampaing);
    }

    /**
     * Delete the adCampaing by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdCampaing : {}", id);
        adCampaingRepository.delete(id);
        adCampaingSearchRepository.delete(id);
    }

    /**
     * Search for the adCampaing corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdCampaingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdCampaings for query {}", query);
        Page<AdCampaing> result = adCampaingSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adCampaingMapper::toDto);
    }
}
