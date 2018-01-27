package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.AdService;
import com.adloveyou.ms.ad.domain.Ad;
import com.adloveyou.ms.ad.repository.AdRepository;
import com.adloveyou.ms.ad.repository.search.AdSearchRepository;
import com.adloveyou.ms.ad.service.dto.AdDTO;
import com.adloveyou.ms.ad.service.mapper.AdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Ad.
 */
@Service
@Transactional
public class AdServiceImpl implements AdService {

    private final Logger log = LoggerFactory.getLogger(AdServiceImpl.class);

    private final AdRepository adRepository;

    private final AdMapper adMapper;

    private final AdSearchRepository adSearchRepository;

    public AdServiceImpl(AdRepository adRepository, AdMapper adMapper, AdSearchRepository adSearchRepository) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
        this.adSearchRepository = adSearchRepository;
    }

    /**
     * Save a ad.
     *
     * @param adDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdDTO save(AdDTO adDTO) {
        log.debug("Request to save Ad : {}", adDTO);
        Ad ad = adMapper.toEntity(adDTO);
        ad = adRepository.save(ad);
        AdDTO result = adMapper.toDto(ad);
        adSearchRepository.save(ad);
        return result;
    }

    /**
     * Get all the ads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ads");
        return adRepository.findAll(pageable)
            .map(adMapper::toDto);
    }

    /**
     * Get one ad by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdDTO findOne(Long id) {
        log.debug("Request to get Ad : {}", id);
        Ad ad = adRepository.findOne(id);
        return adMapper.toDto(ad);
    }

    /**
     * Delete the ad by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ad : {}", id);
        adRepository.delete(id);
        adSearchRepository.delete(id);
    }

    /**
     * Search for the ad corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ads for query {}", query);
        Page<Ad> result = adSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adMapper::toDto);
    }
}
