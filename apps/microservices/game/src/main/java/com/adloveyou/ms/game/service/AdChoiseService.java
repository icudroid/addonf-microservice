package com.adloveyou.ms.game.service;

import com.adloveyou.ms.game.service.dto.AdChoiseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdChoise.
 */
public interface AdChoiseService {

    /**
     * Save a adChoise.
     *
     * @param adChoiseDTO the entity to save
     * @return the persisted entity
     */
    AdChoiseDTO save(AdChoiseDTO adChoiseDTO);

    /**
     * Get all the adChoises.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdChoiseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adChoise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdChoiseDTO findOne(Long id);

    /**
     * Delete the "id" adChoise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adChoise corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdChoiseDTO> search(String query, Pageable pageable);
}
