package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.FileAttachementService;
import com.adloveyou.ms.domain.FileAttachement;
import com.adloveyou.ms.repository.FileAttachementRepository;
import com.adloveyou.ms.repository.search.FileAttachementSearchRepository;
import com.adloveyou.ms.service.dto.FileAttachementDTO;
import com.adloveyou.ms.service.mapper.FileAttachementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FileAttachement.
 */
@Service
@Transactional
public class FileAttachementServiceImpl implements FileAttachementService{

    private final Logger log = LoggerFactory.getLogger(FileAttachementServiceImpl.class);

    private final FileAttachementRepository fileAttachementRepository;

    private final FileAttachementMapper fileAttachementMapper;

    private final FileAttachementSearchRepository fileAttachementSearchRepository;

    public FileAttachementServiceImpl(FileAttachementRepository fileAttachementRepository, FileAttachementMapper fileAttachementMapper, FileAttachementSearchRepository fileAttachementSearchRepository) {
        this.fileAttachementRepository = fileAttachementRepository;
        this.fileAttachementMapper = fileAttachementMapper;
        this.fileAttachementSearchRepository = fileAttachementSearchRepository;
    }

    /**
     * Save a fileAttachement.
     *
     * @param fileAttachementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FileAttachementDTO save(FileAttachementDTO fileAttachementDTO) {
        log.debug("Request to save FileAttachement : {}", fileAttachementDTO);
        FileAttachement fileAttachement = fileAttachementMapper.toEntity(fileAttachementDTO);
        fileAttachement = fileAttachementRepository.save(fileAttachement);
        FileAttachementDTO result = fileAttachementMapper.toDto(fileAttachement);
        fileAttachementSearchRepository.save(fileAttachement);
        return result;
    }

    /**
     * Get all the fileAttachements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileAttachementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileAttachements");
        return fileAttachementRepository.findAll(pageable)
            .map(fileAttachementMapper::toDto);
    }

    /**
     * Get one fileAttachement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FileAttachementDTO findOne(Long id) {
        log.debug("Request to get FileAttachement : {}", id);
        FileAttachement fileAttachement = fileAttachementRepository.findOne(id);
        return fileAttachementMapper.toDto(fileAttachement);
    }

    /**
     * Delete the fileAttachement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileAttachement : {}", id);
        fileAttachementRepository.delete(id);
        fileAttachementSearchRepository.delete(id);
    }

    /**
     * Search for the fileAttachement corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileAttachementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FileAttachements for query {}", query);
        Page<FileAttachement> result = fileAttachementSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(fileAttachementMapper::toDto);
    }
}
