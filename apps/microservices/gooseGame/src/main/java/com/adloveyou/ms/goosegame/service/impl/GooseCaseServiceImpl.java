package com.adloveyou.ms.goosegame.service.impl;

import com.adloveyou.ms.goosegame.service.GooseCaseService;
import com.adloveyou.ms.goosegame.domain.GooseCase;
import com.adloveyou.ms.goosegame.repository.GooseCaseRepository;
import com.adloveyou.ms.goosegame.repository.search.GooseCaseSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseCaseDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseCaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GooseCase.
 */
@Service
@Transactional
public class GooseCaseServiceImpl implements GooseCaseService{

    private final Logger log = LoggerFactory.getLogger(GooseCaseServiceImpl.class);

    private final GooseCaseRepository gooseCaseRepository;

    private final GooseCaseMapper gooseCaseMapper;

    private final GooseCaseSearchRepository gooseCaseSearchRepository;

    public GooseCaseServiceImpl(GooseCaseRepository gooseCaseRepository, GooseCaseMapper gooseCaseMapper, GooseCaseSearchRepository gooseCaseSearchRepository) {
        this.gooseCaseRepository = gooseCaseRepository;
        this.gooseCaseMapper = gooseCaseMapper;
        this.gooseCaseSearchRepository = gooseCaseSearchRepository;
    }

    /**
     * Save a gooseCase.
     *
     * @param gooseCaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GooseCaseDTO save(GooseCaseDTO gooseCaseDTO) {
        log.debug("Request to save GooseCase : {}", gooseCaseDTO);
        GooseCase gooseCase = gooseCaseMapper.toEntity(gooseCaseDTO);
        gooseCase = gooseCaseRepository.save(gooseCase);
        GooseCaseDTO result = gooseCaseMapper.toDto(gooseCase);
        gooseCaseSearchRepository.save(gooseCase);
        return result;
    }

    /**
     * Get all the gooseCases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseCaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GooseCases");
        return gooseCaseRepository.findAll(pageable)
            .map(gooseCaseMapper::toDto);
    }

    /**
     * Get one gooseCase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GooseCaseDTO findOne(Long id) {
        log.debug("Request to get GooseCase : {}", id);
        GooseCase gooseCase = gooseCaseRepository.findOne(id);
        return gooseCaseMapper.toDto(gooseCase);
    }

    /**
     * Delete the gooseCase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GooseCase : {}", id);
        gooseCaseRepository.delete(id);
        gooseCaseSearchRepository.delete(id);
    }

    /**
     * Search for the gooseCase corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseCaseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GooseCases for query {}", query);
        Page<GooseCase> result = gooseCaseSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(gooseCaseMapper::toDto);
    }
}
