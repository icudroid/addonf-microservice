package com.adloveyou.ms.goosegame.service;

import com.adloveyou.ms.goosegame.service.dto.GooseLevelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GooseLevel.
 */
public interface GooseLevelService {

    /**
     * Save a gooseLevel.
     *
     * @param gooseLevelDTO the entity to save
     * @return the persisted entity
     */
    GooseLevelDTO save(GooseLevelDTO gooseLevelDTO);

    /**
     * Get all the gooseLevels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseLevelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gooseLevel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GooseLevelDTO findOne(Long id);

    /**
     * Delete the "id" gooseLevel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gooseLevel corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseLevelDTO> search(String query, Pageable pageable);
}
