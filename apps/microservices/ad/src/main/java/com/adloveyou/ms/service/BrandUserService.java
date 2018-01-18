package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.BrandUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BrandUser.
 */
public interface BrandUserService {

    /**
     * Save a brandUser.
     *
     * @param brandUserDTO the entity to save
     * @return the persisted entity
     */
    BrandUserDTO save(BrandUserDTO brandUserDTO);

    /**
     * Get all the brandUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BrandUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" brandUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BrandUserDTO findOne(Long id);

    /**
     * Delete the "id" brandUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the brandUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BrandUserDTO> search(String query, Pageable pageable);
}
