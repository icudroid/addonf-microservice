package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AgencyUserService;
import com.adloveyou.ms.domain.AgencyUser;
import com.adloveyou.ms.repository.AgencyUserRepository;
import com.adloveyou.ms.repository.search.AgencyUserSearchRepository;
import com.adloveyou.ms.service.dto.AgencyUserDTO;
import com.adloveyou.ms.service.mapper.AgencyUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AgencyUser.
 */
@Service
@Transactional
public class AgencyUserServiceImpl implements AgencyUserService{

    private final Logger log = LoggerFactory.getLogger(AgencyUserServiceImpl.class);

    private final AgencyUserRepository agencyUserRepository;

    private final AgencyUserMapper agencyUserMapper;

    private final AgencyUserSearchRepository agencyUserSearchRepository;

    public AgencyUserServiceImpl(AgencyUserRepository agencyUserRepository, AgencyUserMapper agencyUserMapper, AgencyUserSearchRepository agencyUserSearchRepository) {
        this.agencyUserRepository = agencyUserRepository;
        this.agencyUserMapper = agencyUserMapper;
        this.agencyUserSearchRepository = agencyUserSearchRepository;
    }

    /**
     * Save a agencyUser.
     *
     * @param agencyUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgencyUserDTO save(AgencyUserDTO agencyUserDTO) {
        log.debug("Request to save AgencyUser : {}", agencyUserDTO);
        AgencyUser agencyUser = agencyUserMapper.toEntity(agencyUserDTO);
        agencyUser = agencyUserRepository.save(agencyUser);
        AgencyUserDTO result = agencyUserMapper.toDto(agencyUser);
        agencyUserSearchRepository.save(agencyUser);
        return result;
    }

    /**
     * Get all the agencyUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgencyUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AgencyUsers");
        return agencyUserRepository.findAll(pageable)
            .map(agencyUserMapper::toDto);
    }

    /**
     * Get one agencyUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AgencyUserDTO findOne(Long id) {
        log.debug("Request to get AgencyUser : {}", id);
        AgencyUser agencyUser = agencyUserRepository.findOne(id);
        return agencyUserMapper.toDto(agencyUser);
    }

    /**
     * Delete the agencyUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AgencyUser : {}", id);
        agencyUserRepository.delete(id);
        agencyUserSearchRepository.delete(id);
    }

    /**
     * Search for the agencyUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgencyUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AgencyUsers for query {}", query);
        Page<AgencyUser> result = agencyUserSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(agencyUserMapper::toDto);
    }
}
