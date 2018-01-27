package com.adloveyou.ms.goosegame.service.impl;

import com.adloveyou.ms.goosegame.service.GooseGameService;
import com.adloveyou.ms.goosegame.domain.GooseGame;
import com.adloveyou.ms.goosegame.repository.GooseGameRepository;
import com.adloveyou.ms.goosegame.repository.search.GooseGameSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseGameDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseGameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GooseGame.
 */
@Service
@Transactional
public class GooseGameServiceImpl implements GooseGameService{

    private final Logger log = LoggerFactory.getLogger(GooseGameServiceImpl.class);

    private final GooseGameRepository gooseGameRepository;

    private final GooseGameMapper gooseGameMapper;

    private final GooseGameSearchRepository gooseGameSearchRepository;

    public GooseGameServiceImpl(GooseGameRepository gooseGameRepository, GooseGameMapper gooseGameMapper, GooseGameSearchRepository gooseGameSearchRepository) {
        this.gooseGameRepository = gooseGameRepository;
        this.gooseGameMapper = gooseGameMapper;
        this.gooseGameSearchRepository = gooseGameSearchRepository;
    }

    /**
     * Save a gooseGame.
     *
     * @param gooseGameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GooseGameDTO save(GooseGameDTO gooseGameDTO) {
        log.debug("Request to save GooseGame : {}", gooseGameDTO);
        GooseGame gooseGame = gooseGameMapper.toEntity(gooseGameDTO);
        gooseGame = gooseGameRepository.save(gooseGame);
        GooseGameDTO result = gooseGameMapper.toDto(gooseGame);
        gooseGameSearchRepository.save(gooseGame);
        return result;
    }

    /**
     * Get all the gooseGames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseGameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GooseGames");
        return gooseGameRepository.findAll(pageable)
            .map(gooseGameMapper::toDto);
    }

    /**
     * Get one gooseGame by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GooseGameDTO findOne(Long id) {
        log.debug("Request to get GooseGame : {}", id);
        GooseGame gooseGame = gooseGameRepository.findOne(id);
        return gooseGameMapper.toDto(gooseGame);
    }

    /**
     * Delete the gooseGame by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GooseGame : {}", id);
        gooseGameRepository.delete(id);
        gooseGameSearchRepository.delete(id);
    }

    /**
     * Search for the gooseGame corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseGameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GooseGames for query {}", query);
        Page<GooseGame> result = gooseGameSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(gooseGameMapper::toDto);
    }
}
