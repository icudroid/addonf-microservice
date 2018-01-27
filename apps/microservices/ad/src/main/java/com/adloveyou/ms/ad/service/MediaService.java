package com.adloveyou.ms.ad.service;

import com.adloveyou.ms.ad.service.dto.MediaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Media.
 */
public interface MediaService {

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    MediaDTO save(MediaDTO mediaDTO);

    /**
     * Get all the media.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" media.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MediaDTO findOne(Long id);

    /**
     * Delete the "id" media.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the media corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MediaDTO> search(String query, Pageable pageable);
}
