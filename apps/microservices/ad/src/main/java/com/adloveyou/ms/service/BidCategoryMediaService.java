package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.BidCategoryMediaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing BidCategoryMedia.
 */
public interface BidCategoryMediaService {

    /**
     * Save a bidCategoryMedia.
     *
     * @param bidCategoryMediaDTO the entity to save
     * @return the persisted entity
     */
    BidCategoryMediaDTO save(BidCategoryMediaDTO bidCategoryMediaDTO);

    /**
     * Get all the bidCategoryMedias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BidCategoryMediaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bidCategoryMedia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BidCategoryMediaDTO findOne(Long id);

    /**
     * Delete the "id" bidCategoryMedia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the bidCategoryMedia corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BidCategoryMediaDTO> search(String query, Pageable pageable);
}
