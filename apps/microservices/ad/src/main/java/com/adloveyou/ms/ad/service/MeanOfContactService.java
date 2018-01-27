package com.adloveyou.ms.ad.service;

import com.adloveyou.ms.ad.service.dto.MeanOfContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing MeanOfContact.
 */
public interface MeanOfContactService {

    /**
     * Save a meanOfContact.
     *
     * @param meanOfContactDTO the entity to save
     * @return the persisted entity
     */
    MeanOfContactDTO save(MeanOfContactDTO meanOfContactDTO);

    /**
     * Get all the meanOfContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MeanOfContactDTO> findAll(Pageable pageable);

    /**
     * Get the "id" meanOfContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MeanOfContactDTO findOne(Long id);

    /**
     * Delete the "id" meanOfContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the meanOfContact corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MeanOfContactDTO> search(String query, Pageable pageable);
}
