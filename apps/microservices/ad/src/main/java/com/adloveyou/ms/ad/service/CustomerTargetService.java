package com.adloveyou.ms.ad.service;

import com.adloveyou.ms.ad.service.dto.CustomerTargetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerTarget.
 */
public interface CustomerTargetService {

    /**
     * Save a customerTarget.
     *
     * @param customerTargetDTO the entity to save
     * @return the persisted entity
     */
    CustomerTargetDTO save(CustomerTargetDTO customerTargetDTO);

    /**
     * Get all the customerTargets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CustomerTargetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" customerTarget.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CustomerTargetDTO findOne(Long id);

    /**
     * Delete the "id" customerTarget.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the customerTarget corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CustomerTargetDTO> search(String query, Pageable pageable);
}
