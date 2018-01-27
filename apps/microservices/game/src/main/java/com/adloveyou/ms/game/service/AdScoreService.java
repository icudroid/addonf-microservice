package com.adloveyou.ms.game.service;

import com.adloveyou.ms.game.service.dto.AdScoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdScore.
 */
public interface AdScoreService {

    /**
     * Save a adScore.
     *
     * @param adScoreDTO the entity to save
     * @return the persisted entity
     */
    AdScoreDTO save(AdScoreDTO adScoreDTO);

    /**
     * Get all the adScores.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdScoreDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adScore.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdScoreDTO findOne(Long id);

    /**
     * Delete the "id" adScore.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adScore corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdScoreDTO> search(String query, Pageable pageable);
}
