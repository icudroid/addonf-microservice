package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AdRuleService;
import com.adloveyou.ms.domain.AdRule;
import com.adloveyou.ms.repository.AdRuleRepository;
import com.adloveyou.ms.repository.search.AdRuleSearchRepository;
import com.adloveyou.ms.service.dto.AdRuleDTO;
import com.adloveyou.ms.service.mapper.AdRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdRule.
 */
@Service
@Transactional
public class AdRuleServiceImpl implements AdRuleService{

    private final Logger log = LoggerFactory.getLogger(AdRuleServiceImpl.class);

    private final AdRuleRepository adRuleRepository;

    private final AdRuleMapper adRuleMapper;

    private final AdRuleSearchRepository adRuleSearchRepository;

    public AdRuleServiceImpl(AdRuleRepository adRuleRepository, AdRuleMapper adRuleMapper, AdRuleSearchRepository adRuleSearchRepository) {
        this.adRuleRepository = adRuleRepository;
        this.adRuleMapper = adRuleMapper;
        this.adRuleSearchRepository = adRuleSearchRepository;
    }

    /**
     * Save a adRule.
     *
     * @param adRuleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdRuleDTO save(AdRuleDTO adRuleDTO) {
        log.debug("Request to save AdRule : {}", adRuleDTO);
        AdRule adRule = adRuleMapper.toEntity(adRuleDTO);
        adRule = adRuleRepository.save(adRule);
        AdRuleDTO result = adRuleMapper.toDto(adRule);
        adRuleSearchRepository.save(adRule);
        return result;
    }

    /**
     * Get all the adRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdRules");
        return adRuleRepository.findAll(pageable)
            .map(adRuleMapper::toDto);
    }

    /**
     * Get one adRule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdRuleDTO findOne(Long id) {
        log.debug("Request to get AdRule : {}", id);
        AdRule adRule = adRuleRepository.findOne(id);
        return adRuleMapper.toDto(adRule);
    }

    /**
     * Delete the adRule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdRule : {}", id);
        adRuleRepository.delete(id);
        adRuleSearchRepository.delete(id);
    }

    /**
     * Search for the adRule corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdRuleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdRules for query {}", query);
        Page<AdRule> result = adRuleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adRuleMapper::toDto);
    }
}
