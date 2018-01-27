package com.adloveyou.ms.game.service;

import com.adloveyou.ms.game.service.dto.PossibilityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Possibility.
 */
public interface PossibilityService {

    /**
     * Save a possibility.
     *
     * @param possibilityDTO the entity to save
     * @return the persisted entity
     */
    PossibilityDTO save(PossibilityDTO possibilityDTO);

    /**
     * Get all the possibilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PossibilityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" possibility.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PossibilityDTO findOne(Long id);

    /**
     * Delete the "id" possibility.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the possibility corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PossibilityDTO> search(String query, Pageable pageable);
}
