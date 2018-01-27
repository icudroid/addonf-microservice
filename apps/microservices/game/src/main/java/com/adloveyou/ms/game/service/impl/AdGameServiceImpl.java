package com.adloveyou.ms.game.service.impl;

import com.adloveyou.ms.game.service.AdGameService;
import com.adloveyou.ms.game.domain.AdGame;
import com.adloveyou.ms.game.repository.AdGameRepository;
import com.adloveyou.ms.game.repository.search.AdGameSearchRepository;
import com.adloveyou.ms.game.service.dto.AdGameDTO;
import com.adloveyou.ms.game.service.mapper.AdGameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdGame.
 */
@Service
@Transactional
public class AdGameServiceImpl implements AdGameService{

    private final Logger log = LoggerFactory.getLogger(AdGameServiceImpl.class);

    private final AdGameRepository adGameRepository;

    private final AdGameMapper adGameMapper;

    private final AdGameSearchRepository adGameSearchRepository;

    public AdGameServiceImpl(AdGameRepository adGameRepository, AdGameMapper adGameMapper, AdGameSearchRepository adGameSearchRepository) {
        this.adGameRepository = adGameRepository;
        this.adGameMapper = adGameMapper;
        this.adGameSearchRepository = adGameSearchRepository;
    }

    /**
     * Save a adGame.
     *
     * @param adGameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdGameDTO save(AdGameDTO adGameDTO) {
        log.debug("Request to save AdGame : {}", adGameDTO);
        AdGame adGame = adGameMapper.toEntity(adGameDTO);
        adGame = adGameRepository.save(adGame);
        AdGameDTO result = adGameMapper.toDto(adGame);
        adGameSearchRepository.save(adGame);
        return result;
    }

    /**
     * Get all the adGames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdGameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdGames");
        return adGameRepository.findAll(pageable)
            .map(adGameMapper::toDto);
    }

    /**
     * Get one adGame by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdGameDTO findOne(Long id) {
        log.debug("Request to get AdGame : {}", id);
        AdGame adGame = adGameRepository.findOne(id);
        return adGameMapper.toDto(adGame);
    }

    /**
     * Delete the adGame by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdGame : {}", id);
        adGameRepository.delete(id);
        adGameSearchRepository.delete(id);
    }

    /**
     * Search for the adGame corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdGameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdGames for query {}", query);
        Page<AdGame> result = adGameSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adGameMapper::toDto);
    }
}
