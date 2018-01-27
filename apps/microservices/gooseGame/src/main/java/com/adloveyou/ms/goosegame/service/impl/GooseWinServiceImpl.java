package com.adloveyou.ms.goosegame.service.impl;

import com.adloveyou.ms.goosegame.service.GooseWinService;
import com.adloveyou.ms.goosegame.domain.GooseWin;
import com.adloveyou.ms.goosegame.repository.GooseWinRepository;
import com.adloveyou.ms.goosegame.repository.search.GooseWinSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseWinDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseWinMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GooseWin.
 */
@Service
@Transactional
public class GooseWinServiceImpl implements GooseWinService{

    private final Logger log = LoggerFactory.getLogger(GooseWinServiceImpl.class);

    private final GooseWinRepository gooseWinRepository;

    private final GooseWinMapper gooseWinMapper;

    private final GooseWinSearchRepository gooseWinSearchRepository;

    public GooseWinServiceImpl(GooseWinRepository gooseWinRepository, GooseWinMapper gooseWinMapper, GooseWinSearchRepository gooseWinSearchRepository) {
        this.gooseWinRepository = gooseWinRepository;
        this.gooseWinMapper = gooseWinMapper;
        this.gooseWinSearchRepository = gooseWinSearchRepository;
    }

    /**
     * Save a gooseWin.
     *
     * @param gooseWinDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GooseWinDTO save(GooseWinDTO gooseWinDTO) {
        log.debug("Request to save GooseWin : {}", gooseWinDTO);
        GooseWin gooseWin = gooseWinMapper.toEntity(gooseWinDTO);
        gooseWin = gooseWinRepository.save(gooseWin);
        GooseWinDTO result = gooseWinMapper.toDto(gooseWin);
        gooseWinSearchRepository.save(gooseWin);
        return result;
    }

    /**
     * Get all the gooseWins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseWinDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GooseWins");
        return gooseWinRepository.findAll(pageable)
            .map(gooseWinMapper::toDto);
    }

    /**
     * Get one gooseWin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GooseWinDTO findOne(Long id) {
        log.debug("Request to get GooseWin : {}", id);
        GooseWin gooseWin = gooseWinRepository.findOne(id);
        return gooseWinMapper.toDto(gooseWin);
    }

    /**
     * Delete the gooseWin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GooseWin : {}", id);
        gooseWinRepository.delete(id);
        gooseWinSearchRepository.delete(id);
    }

    /**
     * Search for the gooseWin corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseWinDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GooseWins for query {}", query);
        Page<GooseWin> result = gooseWinSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(gooseWinMapper::toDto);
    }
}
