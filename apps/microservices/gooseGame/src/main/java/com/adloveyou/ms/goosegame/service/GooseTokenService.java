package com.adloveyou.ms.goosegame.service;

import com.adloveyou.ms.goosegame.service.dto.GooseTokenDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GooseToken.
 */
public interface GooseTokenService {

    /**
     * Save a gooseToken.
     *
     * @param gooseTokenDTO the entity to save
     * @return the persisted entity
     */
    GooseTokenDTO save(GooseTokenDTO gooseTokenDTO);

    /**
     * Get all the gooseTokens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseTokenDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gooseToken.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GooseTokenDTO findOne(Long id);

    /**
     * Delete the "id" gooseToken.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gooseToken corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseTokenDTO> search(String query, Pageable pageable);
}
