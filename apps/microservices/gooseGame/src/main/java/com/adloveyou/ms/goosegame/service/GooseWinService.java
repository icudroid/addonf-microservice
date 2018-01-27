package com.adloveyou.ms.goosegame.service;

import com.adloveyou.ms.goosegame.service.dto.GooseWinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GooseWin.
 */
public interface GooseWinService {

    /**
     * Save a gooseWin.
     *
     * @param gooseWinDTO the entity to save
     * @return the persisted entity
     */
    GooseWinDTO save(GooseWinDTO gooseWinDTO);

    /**
     * Get all the gooseWins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseWinDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gooseWin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GooseWinDTO findOne(Long id);

    /**
     * Delete the "id" gooseWin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gooseWin corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseWinDTO> search(String query, Pageable pageable);
}
