package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.AdCampaingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdCampaing.
 */
public interface AdCampaingService {

    /**
     * Save a adCampaing.
     *
     * @param adCampaingDTO the entity to save
     * @return the persisted entity
     */
    AdCampaingDTO save(AdCampaingDTO adCampaingDTO);

    /**
     * Get all the adCampaings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdCampaingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adCampaing.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdCampaingDTO findOne(Long id);

    /**
     * Delete the "id" adCampaing.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adCampaing corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdCampaingDTO> search(String query, Pageable pageable);
}
