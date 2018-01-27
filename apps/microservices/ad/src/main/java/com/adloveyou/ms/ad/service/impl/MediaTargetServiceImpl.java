package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.MediaTargetService;
import com.adloveyou.ms.ad.domain.MediaTarget;
import com.adloveyou.ms.ad.repository.MediaTargetRepository;
import com.adloveyou.ms.ad.repository.search.MediaTargetSearchRepository;
import com.adloveyou.ms.ad.service.dto.MediaTargetDTO;
import com.adloveyou.ms.ad.service.mapper.MediaTargetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MediaTarget.
 */
@Service
@Transactional
public class MediaTargetServiceImpl implements MediaTargetService {

    private final Logger log = LoggerFactory.getLogger(MediaTargetServiceImpl.class);

    private final MediaTargetRepository mediaTargetRepository;

    private final MediaTargetMapper mediaTargetMapper;

    private final MediaTargetSearchRepository mediaTargetSearchRepository;

    public MediaTargetServiceImpl(MediaTargetRepository mediaTargetRepository, MediaTargetMapper mediaTargetMapper, MediaTargetSearchRepository mediaTargetSearchRepository) {
        this.mediaTargetRepository = mediaTargetRepository;
        this.mediaTargetMapper = mediaTargetMapper;
        this.mediaTargetSearchRepository = mediaTargetSearchRepository;
    }

    /**
     * Save a mediaTarget.
     *
     * @param mediaTargetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaTargetDTO save(MediaTargetDTO mediaTargetDTO) {
        log.debug("Request to save MediaTarget : {}", mediaTargetDTO);
        MediaTarget mediaTarget = mediaTargetMapper.toEntity(mediaTargetDTO);
        mediaTarget = mediaTargetRepository.save(mediaTarget);
        MediaTargetDTO result = mediaTargetMapper.toDto(mediaTarget);
        mediaTargetSearchRepository.save(mediaTarget);
        return result;
    }

    /**
     * Get all the mediaTargets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MediaTargetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MediaTargets");
        return mediaTargetRepository.findAll(pageable)
            .map(mediaTargetMapper::toDto);
    }

    /**
     * Get one mediaTarget by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MediaTargetDTO findOne(Long id) {
        log.debug("Request to get MediaTarget : {}", id);
        MediaTarget mediaTarget = mediaTargetRepository.findOne(id);
        return mediaTargetMapper.toDto(mediaTarget);
    }

    /**
     * Delete the mediaTarget by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MediaTarget : {}", id);
        mediaTargetRepository.delete(id);
        mediaTargetSearchRepository.delete(id);
    }

    /**
     * Search for the mediaTarget corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MediaTargetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MediaTargets for query {}", query);
        Page<MediaTarget> result = mediaTargetSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(mediaTargetMapper::toDto);
    }
}
