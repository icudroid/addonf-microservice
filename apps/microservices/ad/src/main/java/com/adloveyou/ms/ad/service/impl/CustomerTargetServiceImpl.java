package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.CustomerTargetService;
import com.adloveyou.ms.ad.domain.CustomerTarget;
import com.adloveyou.ms.ad.repository.CustomerTargetRepository;
import com.adloveyou.ms.ad.repository.search.CustomerTargetSearchRepository;
import com.adloveyou.ms.ad.service.dto.CustomerTargetDTO;
import com.adloveyou.ms.ad.service.mapper.CustomerTargetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CustomerTarget.
 */
@Service
@Transactional
public class CustomerTargetServiceImpl implements CustomerTargetService {

    private final Logger log = LoggerFactory.getLogger(CustomerTargetServiceImpl.class);

    private final CustomerTargetRepository customerTargetRepository;

    private final CustomerTargetMapper customerTargetMapper;

    private final CustomerTargetSearchRepository customerTargetSearchRepository;

    public CustomerTargetServiceImpl(CustomerTargetRepository customerTargetRepository, CustomerTargetMapper customerTargetMapper, CustomerTargetSearchRepository customerTargetSearchRepository) {
        this.customerTargetRepository = customerTargetRepository;
        this.customerTargetMapper = customerTargetMapper;
        this.customerTargetSearchRepository = customerTargetSearchRepository;
    }

    /**
     * Save a customerTarget.
     *
     * @param customerTargetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CustomerTargetDTO save(CustomerTargetDTO customerTargetDTO) {
        log.debug("Request to save CustomerTarget : {}", customerTargetDTO);
        CustomerTarget customerTarget = customerTargetMapper.toEntity(customerTargetDTO);
        customerTarget = customerTargetRepository.save(customerTarget);
        CustomerTargetDTO result = customerTargetMapper.toDto(customerTarget);
        customerTargetSearchRepository.save(customerTarget);
        return result;
    }

    /**
     * Get all the customerTargets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerTargetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerTargets");
        return customerTargetRepository.findAll(pageable)
            .map(customerTargetMapper::toDto);
    }

    /**
     * Get one customerTarget by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerTargetDTO findOne(Long id) {
        log.debug("Request to get CustomerTarget : {}", id);
        CustomerTarget customerTarget = customerTargetRepository.findOne(id);
        return customerTargetMapper.toDto(customerTarget);
    }

    /**
     * Delete the customerTarget by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerTarget : {}", id);
        customerTargetRepository.delete(id);
        customerTargetSearchRepository.delete(id);
    }

    /**
     * Search for the customerTarget corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerTargetDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CustomerTargets for query {}", query);
        Page<CustomerTarget> result = customerTargetSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(customerTargetMapper::toDto);
    }
}
