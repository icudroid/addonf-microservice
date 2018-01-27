package com.adloveyou.ms.game.service.impl;

import com.adloveyou.ms.game.service.AdScoreService;
import com.adloveyou.ms.game.domain.AdScore;
import com.adloveyou.ms.game.repository.AdScoreRepository;
import com.adloveyou.ms.game.repository.search.AdScoreSearchRepository;
import com.adloveyou.ms.game.service.dto.AdScoreDTO;
import com.adloveyou.ms.game.service.mapper.AdScoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AdScore.
 */
@Service
@Transactional
public class AdScoreServiceImpl implements AdScoreService{

    private final Logger log = LoggerFactory.getLogger(AdScoreServiceImpl.class);

    private final AdScoreRepository adScoreRepository;

    private final AdScoreMapper adScoreMapper;

    private final AdScoreSearchRepository adScoreSearchRepository;

    public AdScoreServiceImpl(AdScoreRepository adScoreRepository, AdScoreMapper adScoreMapper, AdScoreSearchRepository adScoreSearchRepository) {
        this.adScoreRepository = adScoreRepository;
        this.adScoreMapper = adScoreMapper;
        this.adScoreSearchRepository = adScoreSearchRepository;
    }

    /**
     * Save a adScore.
     *
     * @param adScoreDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdScoreDTO save(AdScoreDTO adScoreDTO) {
        log.debug("Request to save AdScore : {}", adScoreDTO);
        AdScore adScore = adScoreMapper.toEntity(adScoreDTO);
        adScore = adScoreRepository.save(adScore);
        AdScoreDTO result = adScoreMapper.toDto(adScore);
        adScoreSearchRepository.save(adScore);
        return result;
    }

    /**
     * Get all the adScores.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdScoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdScores");
        return adScoreRepository.findAll(pageable)
            .map(adScoreMapper::toDto);
    }

    /**
     * Get one adScore by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdScoreDTO findOne(Long id) {
        log.debug("Request to get AdScore : {}", id);
        AdScore adScore = adScoreRepository.findOne(id);
        return adScoreMapper.toDto(adScore);
    }

    /**
     * Delete the adScore by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdScore : {}", id);
        adScoreRepository.delete(id);
        adScoreSearchRepository.delete(id);
    }

    /**
     * Search for the adScore corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdScoreDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AdScores for query {}", query);
        Page<AdScore> result = adScoreSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(adScoreMapper::toDto);
    }
}
