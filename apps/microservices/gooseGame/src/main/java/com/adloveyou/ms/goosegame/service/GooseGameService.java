package com.adloveyou.ms.goosegame.service;

import com.adloveyou.ms.goosegame.service.dto.GooseGameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GooseGame.
 */
public interface GooseGameService {

    /**
     * Save a gooseGame.
     *
     * @param gooseGameDTO the entity to save
     * @return the persisted entity
     */
    GooseGameDTO save(GooseGameDTO gooseGameDTO);

    /**
     * Get all the gooseGames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseGameDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gooseGame.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GooseGameDTO findOne(Long id);

    /**
     * Delete the "id" gooseGame.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gooseGame corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseGameDTO> search(String query, Pageable pageable);
}
