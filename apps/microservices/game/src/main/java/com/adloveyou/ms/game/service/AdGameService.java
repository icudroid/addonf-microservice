package com.adloveyou.ms.game.service;

import com.adloveyou.ms.game.service.dto.AdGameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdGame.
 */
public interface AdGameService {

    /**
     * Save a adGame.
     *
     * @param adGameDTO the entity to save
     * @return the persisted entity
     */
    AdGameDTO save(AdGameDTO adGameDTO);

    /**
     * Get all the adGames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdGameDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adGame.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdGameDTO findOne(Long id);

    /**
     * Delete the "id" adGame.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adGame corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdGameDTO> search(String query, Pageable pageable);
}
