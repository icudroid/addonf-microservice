package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.MediaTargetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MediaTarget.
 */
public interface MediaTargetService {

    /**
     * Save a mediaTarget.
     *
     * @param mediaTargetDTO the entity to save
     * @return the persisted entity
     */
    MediaTargetDTO save(MediaTargetDTO mediaTargetDTO);

    /**
     * Get all the mediaTargets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaTargetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mediaTarget.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MediaTargetDTO findOne(Long id);

    /**
     * Delete the "id" mediaTarget.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mediaTarget corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaTargetDTO> search(String query, Pageable pageable);
}
