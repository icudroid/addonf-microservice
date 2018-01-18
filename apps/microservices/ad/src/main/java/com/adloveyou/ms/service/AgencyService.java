package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.AgencyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Agency.
 */
public interface AgencyService {

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save
     * @return the persisted entity
     */
    AgencyDTO save(AgencyDTO agencyDTO);

    /**
     * Get all the agencies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgencyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" agency.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AgencyDTO findOne(Long id);

    /**
     * Delete the "id" agency.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the agency corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgencyDTO> search(String query, Pageable pageable);
}
