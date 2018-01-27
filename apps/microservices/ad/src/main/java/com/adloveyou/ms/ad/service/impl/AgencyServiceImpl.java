package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.AgencyService;
import com.adloveyou.ms.ad.domain.Agency;
import com.adloveyou.ms.ad.repository.AgencyRepository;
import com.adloveyou.ms.ad.repository.search.AgencySearchRepository;
import com.adloveyou.ms.ad.service.dto.AgencyDTO;
import com.adloveyou.ms.ad.service.mapper.AgencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Agency.
 */
@Service
@Transactional
public class AgencyServiceImpl implements AgencyService {

    private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    private final AgencySearchRepository agencySearchRepository;

    public AgencyServiceImpl(AgencyRepository agencyRepository, AgencyMapper agencyMapper, AgencySearchRepository agencySearchRepository) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
        this.agencySearchRepository = agencySearchRepository;
    }

    /**
     * Save a agency.
     *
     * @param agencyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgencyDTO save(AgencyDTO agencyDTO) {
        log.debug("Request to save Agency : {}", agencyDTO);
        Agency agency = agencyMapper.toEntity(agencyDTO);
        agency = agencyRepository.save(agency);
        AgencyDTO result = agencyMapper.toDto(agency);
        agencySearchRepository.save(agency);
        return result;
    }

    /**
     * Get all the agencies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgencyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agencies");
        return agencyRepository.findAll(pageable)
            .map(agencyMapper::toDto);
    }

    /**
     * Get one agency by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AgencyDTO findOne(Long id) {
        log.debug("Request to get Agency : {}", id);
        Agency agency = agencyRepository.findOne(id);
        return agencyMapper.toDto(agency);
    }

    /**
     * Delete the agency by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agency : {}", id);
        agencyRepository.delete(id);
        agencySearchRepository.delete(id);
    }

    /**
     * Search for the agency corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgencyDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Agencies for query {}", query);
        Page<Agency> result = agencySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(agencyMapper::toDto);
    }
}
