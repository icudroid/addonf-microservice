package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.AdConfigurationService;
import com.adloveyou.ms.ad.domain.AdConfiguration;
import com.adloveyou.ms.ad.repository.AdConfigurationRepository;
import com.adloveyou.ms.ad.repository.search.AdConfigurationSearchRepository;
import com.adloveyou.ms.ad.service.dto.AdConfigurationDTO;
import com.adloveyou.ms.ad.service.mapper.AdConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdConfiguration.
 */
@Service
@Transactional
public class AdConfigurationServiceImpl implements AdConfigurationService {

    private final Logger log = LoggerFactory.getLogger(AdConfigurationServiceImpl.class);

    private final AdConfigurationRepository adConfigurationRepository;

    private final AdConfigurationMapper adConfigurationMapper;

    private final AdConfigurationSearchRepository adConfigurationSearchRepository;

    public AdConfigurationServiceImpl(AdConfigurationRepository adConfigurationRepository, AdConfigurationMapper adConfigurationMapper, AdConfigurationSearchRepository adConfigurationSearchRepository) {
        this.adConfigurationRepository = adConfigurationRepository;
        this.adConfigurationMapper = adConfigurationMapper;
        this.adConfigurationSearchRepository = adConfigurationSearchRepository;
    }

    /**
     * Save a adConfiguration.
     *
     * @param adConfigurationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdConfigurationDTO save(AdConfigurationDTO adConfigurationDTO) {
        log.debug("Request to save AdConfiguration : {}", adConfigurationDTO);
        AdConfiguration adConfiguration = adConfigurationMapper.toEntity(adConfigurationDTO);
        adConfiguration = adConfigurationRepository.save(adConfiguration);
        AdConfigurationDTO result = adConfigurationMapper.toDto(adConfiguration);
        adConfigurationSearchRepository.save(adConfiguration);
        return result;
    }

    /**
     * Get all the adConfigurations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdConfigurations");
        return adConfigurationRepository.findAll(pageable)
            .map(adConfigurationMapper::toDto);
    }

    /**
     * Get one adConfiguration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdConfigurationDTO findOne(Long id) {
        log.debug("Request to get AdConfiguration : {}", id);
        AdConfiguration adConfiguration = adConfigurationRepository.findOne(id);
        return adConfigurationMapper.toDto(adConfiguration);
    }

    /**
     * Delete the adConfiguration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdConfiguration : {}", id);
        adConfigurationRepository.delete(id);
        adConfigurationSearchRepository.delete(id);
    }

    /**
     * Search for the adConfiguration corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdConfigurationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdConfigurations for query {}", query);
        Page<AdConfiguration> result = adConfigurationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adConfigurationMapper::toDto);
    }
}
