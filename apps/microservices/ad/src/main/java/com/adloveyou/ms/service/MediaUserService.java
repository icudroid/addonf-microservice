package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.MediaUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MediaUser.
 */
public interface MediaUserService {

    /**
     * Save a mediaUser.
     *
     * @param mediaUserDTO the entity to save
     * @return the persisted entity
     */
    MediaUserDTO save(MediaUserDTO mediaUserDTO);

    /**
     * Get all the mediaUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" mediaUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MediaUserDTO findOne(Long id);

    /**
     * Delete the "id" mediaUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the mediaUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaUserDTO> search(String query, Pageable pageable);
}
