package com.adloveyou.ms.ad.service;

import com.adloveyou.ms.ad.service.dto.AdRestrictionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdRestriction.
 */
public interface AdRestrictionService {

    /**
     * Save a adRestriction.
     *
     * @param adRestrictionDTO the entity to save
     * @return the persisted entity
     */
    AdRestrictionDTO save(AdRestrictionDTO adRestrictionDTO);

    /**
     * Get all the adRestrictions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdRestrictionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adRestriction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdRestrictionDTO findOne(Long id);

    /**
     * Delete the "id" adRestriction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adRestriction corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdRestrictionDTO> search(String query, Pageable pageable);
}
