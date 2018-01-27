package com.adloveyou.ms.game.service;

import com.adloveyou.ms.game.service.dto.AdPlayerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AdPlayerResponse.
 */
public interface AdPlayerResponseService {

    /**
     * Save a adPlayerResponse.
     *
     * @param adPlayerResponseDTO the entity to save
     * @return the persisted entity
     */
    AdPlayerResponseDTO save(AdPlayerResponseDTO adPlayerResponseDTO);

    /**
     * Get all the adPlayerResponses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdPlayerResponseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" adPlayerResponse.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AdPlayerResponseDTO findOne(Long id);

    /**
     * Delete the "id" adPlayerResponse.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the adPlayerResponse corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AdPlayerResponseDTO> search(String query, Pageable pageable);
}
