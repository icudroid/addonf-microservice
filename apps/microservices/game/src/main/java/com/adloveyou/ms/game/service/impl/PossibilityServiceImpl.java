package com.adloveyou.ms.game.service.impl;

import com.adloveyou.ms.game.service.PossibilityService;
import com.adloveyou.ms.game.domain.Possibility;
import com.adloveyou.ms.game.repository.PossibilityRepository;
import com.adloveyou.ms.game.repository.search.PossibilitySearchRepository;
import com.adloveyou.ms.game.service.dto.PossibilityDTO;
import com.adloveyou.ms.game.service.mapper.PossibilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Possibility.
 */
@Service
@Transactional
public class PossibilityServiceImpl implements PossibilityService{

    private final Logger log = LoggerFactory.getLogger(PossibilityServiceImpl.class);

    private final PossibilityRepository possibilityRepository;

    private final PossibilityMapper possibilityMapper;

    private final PossibilitySearchRepository possibilitySearchRepository;

    public PossibilityServiceImpl(PossibilityRepository possibilityRepository, PossibilityMapper possibilityMapper, PossibilitySearchRepository possibilitySearchRepository) {
        this.possibilityRepository = possibilityRepository;
        this.possibilityMapper = possibilityMapper;
        this.possibilitySearchRepository = possibilitySearchRepository;
    }

    /**
     * Save a possibility.
     *
     * @param possibilityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PossibilityDTO save(PossibilityDTO possibilityDTO) {
        log.debug("Request to save Possibility : {}", possibilityDTO);
        Possibility possibility = possibilityMapper.toEntity(possibilityDTO);
        possibility = possibilityRepository.save(possibility);
        PossibilityDTO result = possibilityMapper.toDto(possibility);
        possibilitySearchRepository.save(possibility);
        return result;
    }

    /**
     * Get all the possibilities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PossibilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Possibilities");
        return possibilityRepository.findAll(pageable)
            .map(possibilityMapper::toDto);
    }

    /**
     * Get one possibility by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PossibilityDTO findOne(Long id) {
        log.debug("Request to get Possibility : {}", id);
        Possibility possibility = possibilityRepository.findOne(id);
        return possibilityMapper.toDto(possibility);
    }

    /**
     * Delete the possibility by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Possibility : {}", id);
        possibilityRepository.delete(id);
        possibilitySearchRepository.delete(id);
    }

    /**
     * Search for the possibility corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PossibilityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Possibilities for query {}", query);
        Page<Possibility> result = possibilitySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(possibilityMapper::toDto);
    }
}
