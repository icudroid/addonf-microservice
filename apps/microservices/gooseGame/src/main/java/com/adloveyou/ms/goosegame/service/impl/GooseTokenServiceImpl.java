package com.adloveyou.ms.goosegame.service.impl;

import com.adloveyou.ms.goosegame.service.GooseTokenService;
import com.adloveyou.ms.goosegame.domain.GooseToken;
import com.adloveyou.ms.goosegame.repository.GooseTokenRepository;
import com.adloveyou.ms.goosegame.repository.search.GooseTokenSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseTokenDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseTokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GooseToken.
 */
@Service
@Transactional
public class GooseTokenServiceImpl implements GooseTokenService{

    private final Logger log = LoggerFactory.getLogger(GooseTokenServiceImpl.class);

    private final GooseTokenRepository gooseTokenRepository;

    private final GooseTokenMapper gooseTokenMapper;

    private final GooseTokenSearchRepository gooseTokenSearchRepository;

    public GooseTokenServiceImpl(GooseTokenRepository gooseTokenRepository, GooseTokenMapper gooseTokenMapper, GooseTokenSearchRepository gooseTokenSearchRepository) {
        this.gooseTokenRepository = gooseTokenRepository;
        this.gooseTokenMapper = gooseTokenMapper;
        this.gooseTokenSearchRepository = gooseTokenSearchRepository;
    }

    /**
     * Save a gooseToken.
     *
     * @param gooseTokenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public GooseTokenDTO save(GooseTokenDTO gooseTokenDTO) {
        log.debug("Request to save GooseToken : {}", gooseTokenDTO);
        GooseToken gooseToken = gooseTokenMapper.toEntity(gooseTokenDTO);
        gooseToken = gooseTokenRepository.save(gooseToken);
        GooseTokenDTO result = gooseTokenMapper.toDto(gooseToken);
        gooseTokenSearchRepository.save(gooseToken);
        return result;
    }

    /**
     * Get all the gooseTokens.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseTokenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GooseTokens");
        return gooseTokenRepository.findAll(pageable)
            .map(gooseTokenMapper::toDto);
    }

    /**
     * Get one gooseToken by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GooseTokenDTO findOne(Long id) {
        log.debug("Request to get GooseToken : {}", id);
        GooseToken gooseToken = gooseTokenRepository.findOne(id);
        return gooseTokenMapper.toDto(gooseToken);
    }

    /**
     * Delete the gooseToken by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GooseToken : {}", id);
        gooseTokenRepository.delete(id);
        gooseTokenSearchRepository.delete(id);
    }

    /**
     * Search for the gooseToken corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GooseTokenDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GooseTokens for query {}", query);
        Page<GooseToken> result = gooseTokenSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(gooseTokenMapper::toDto);
    }
}
