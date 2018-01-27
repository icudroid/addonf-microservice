package com.adloveyou.ms.ad.service;

import com.adloveyou.ms.ad.service.dto.AgencyUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AgencyUser.
 */
public interface AgencyUserService {

    /**
     * Save a agencyUser.
     *
     * @param agencyUserDTO the entity to save
     * @return the persisted entity
     */
    AgencyUserDTO save(AgencyUserDTO agencyUserDTO);

    /**
     * Get all the agencyUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgencyUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" agencyUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AgencyUserDTO findOne(Long id);

    /**
     * Delete the "id" agencyUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the agencyUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AgencyUserDTO> search(String query, Pageable pageable);
}
