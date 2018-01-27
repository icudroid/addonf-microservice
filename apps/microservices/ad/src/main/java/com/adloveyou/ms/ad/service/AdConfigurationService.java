package com.adloveyou.ms.ad.service;

import com.adloveyou.ms.ad.service.dto.AdConfigurationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdConfiguration.
 */
public interface AdConfigurationService {

    /**
     * Save a adConfiguration.
     *
     * @param adConfigurationDTO the entity to save
     * @return the persisted entity
     */
    AdConfigurationDTO save(AdConfigurationDTO adConfigurationDTO);

    /**
     * Get all the adConfigurations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdConfigurationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adConfiguration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdConfigurationDTO findOne(Long id);

    /**
     * Delete the "id" adConfiguration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adConfiguration corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdConfigurationDTO> search(String query, Pageable pageable);
}
