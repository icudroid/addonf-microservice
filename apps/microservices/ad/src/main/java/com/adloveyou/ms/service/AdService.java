package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.AdDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ad.
 */
public interface AdService {

    /**
     * Save a ad.
     *
     * @param adDTO the entity to save
     * @return the persisted entity
     */
    AdDTO save(AdDTO adDTO);

    /**
     * Get all the ads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ad.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdDTO findOne(Long id);

    /**
     * Delete the "id" ad.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ad corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdDTO> search(String query, Pageable pageable);
}
