package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.AdRuleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdRule.
 */
public interface AdRuleService {

    /**
     * Save a adRule.
     *
     * @param adRuleDTO the entity to save
     * @return the persisted entity
     */
    AdRuleDTO save(AdRuleDTO adRuleDTO);

    /**
     * Get all the adRules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdRuleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adRule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdRuleDTO findOne(Long id);

    /**
     * Delete the "id" adRule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adRule corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdRuleDTO> search(String query, Pageable pageable);
}
