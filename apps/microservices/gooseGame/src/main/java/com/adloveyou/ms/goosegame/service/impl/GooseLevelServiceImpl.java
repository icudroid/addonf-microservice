package com.adloveyou.ms.goosegame.service.impl;

import com.adloveyou.ms.goosegame.service.GooseLevelService;
import com.adloveyou.ms.goosegame.domain.GooseLevel;
import com.adloveyou.ms.goosegame.repository.GooseLevelRepository;
import com.adloveyou.ms.goosegame.repository.search.GooseLevelSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseLevelDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GooseLevel.
 */
@Service
@Transactional
public class GooseLevelServiceImpl implements GooseLevelService{

    private final Logger log = LoggerFactory.getLogger(GooseLevelServiceImpl.class);

    private final GooseLevelRepository gooseLevelRepository;

    private final GooseLevelMapper gooseLevelMapper;

    private final GooseLevelSearchRepository gooseLevelSearchRepository;

    public GooseLevelServiceImpl(GooseLevelRepository gooseLevelRepository, GooseLevelMapper gooseLevelMapper, GooseLevelSearchRepository gooseLevelSearchRepository) {
        this.gooseLevelRepository = gooseLevelRepository;
        this.gooseLevelMapper = gooseLevelMapper;
        this.gooseLevelSearchRepository = gooseLevelSearchRepository;
    }

    /**
     * Save a gooseLevel.
     *
     * @param gooseLevelDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GooseLevelDTO save(GooseLevelDTO gooseLevelDTO) {
        log.debug("Request to save GooseLevel : {}", gooseLevelDTO);
        GooseLevel gooseLevel = gooseLevelMapper.toEntity(gooseLevelDTO);
        gooseLevel = gooseLevelRepository.save(gooseLevel);
        GooseLevelDTO result = gooseLevelMapper.toDto(gooseLevel);
        gooseLevelSearchRepository.save(gooseLevel);
        return result;
    }

    /**
     * Get all the gooseLevels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GooseLevels");
        return gooseLevelRepository.findAll(pageable)
            .map(gooseLevelMapper::toDto);
    }

    /**
     * Get one gooseLevel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GooseLevelDTO findOne(Long id) {
        log.debug("Request to get GooseLevel : {}", id);
        GooseLevel gooseLevel = gooseLevelRepository.findOne(id);
        return gooseLevelMapper.toDto(gooseLevel);
    }

    /**
     * Delete the gooseLevel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GooseLevel : {}", id);
        gooseLevelRepository.delete(id);
        gooseLevelSearchRepository.delete(id);
    }

    /**
     * Search for the gooseLevel corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseLevelDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GooseLevels for query {}", query);
        Page<GooseLevel> result = gooseLevelSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(gooseLevelMapper::toDto);
    }
}
