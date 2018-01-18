package com.adloveyou.ms.service;

import com.adloveyou.ms.service.dto.FileAttachementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FileAttachement.
 */
public interface FileAttachementService {

    /**
     * Save a fileAttachement.
     *
     * @param fileAttachementDTO the entity to save
     * @return the persisted entity
     */
    FileAttachementDTO save(FileAttachementDTO fileAttachementDTO);

    /**
     * Get all the fileAttachements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FileAttachementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileAttachement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FileAttachementDTO findOne(Long id);

    /**
     * Delete the "id" fileAttachement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the fileAttachement corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FileAttachementDTO> search(String query, Pageable pageable);
}
