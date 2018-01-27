package com.adloveyou.ms.goosegame.service;

import com.adloveyou.ms.goosegame.service.dto.GooseCaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GooseCase.
 */
public interface GooseCaseService {

    /**
     * Save a gooseCase.
     *
     * @param gooseCaseDTO the entity to save
     * @return the persisted entity
     */
    GooseCaseDTO save(GooseCaseDTO gooseCaseDTO);

    /**
     * Get all the gooseCases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseCaseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gooseCase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GooseCaseDTO findOne(Long id);

    /**
     * Delete the "id" gooseCase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the gooseCase corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GooseCaseDTO> search(String query, Pageable pageable);
}
