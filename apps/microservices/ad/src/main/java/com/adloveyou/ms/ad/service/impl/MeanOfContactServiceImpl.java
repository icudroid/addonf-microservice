package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.MeanOfContactService;
import com.adloveyou.ms.ad.domain.MeanOfContact;
import com.adloveyou.ms.ad.repository.MeanOfContactRepository;
import com.adloveyou.ms.ad.repository.search.MeanOfContactSearchRepository;
import com.adloveyou.ms.ad.service.dto.MeanOfContactDTO;
import com.adloveyou.ms.ad.service.mapper.MeanOfContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MeanOfContact.
 */
@Service
@Transactional
public class MeanOfContactServiceImpl implements MeanOfContactService {

    private final Logger log = LoggerFactory.getLogger(MeanOfContactServiceImpl.class);

    private final MeanOfContactRepository meanOfContactRepository;

    private final MeanOfContactMapper meanOfContactMapper;

    private final MeanOfContactSearchRepository meanOfContactSearchRepository;

    public MeanOfContactServiceImpl(MeanOfContactRepository meanOfContactRepository, MeanOfContactMapper meanOfContactMapper, MeanOfContactSearchRepository meanOfContactSearchRepository) {
        this.meanOfContactRepository = meanOfContactRepository;
        this.meanOfContactMapper = meanOfContactMapper;
        this.meanOfContactSearchRepository = meanOfContactSearchRepository;
    }

    /**
     * Save a meanOfContact.
     *
     * @param meanOfContactDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MeanOfContactDTO save(MeanOfContactDTO meanOfContactDTO) {
        log.debug("Request to save MeanOfContact : {}", meanOfContactDTO);
        MeanOfContact meanOfContact = meanOfContactMapper.toEntity(meanOfContactDTO);
        meanOfContact = meanOfContactRepository.save(meanOfContact);
        MeanOfContactDTO result = meanOfContactMapper.toDto(meanOfContact);
        meanOfContactSearchRepository.save(meanOfContact);
        return result;
    }

    /**
     * Get all the meanOfContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeanOfContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeanOfContacts");
        return meanOfContactRepository.findAll(pageable)
            .map(meanOfContactMapper::toDto);
    }

    /**
     * Get one meanOfContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeanOfContactDTO findOne(Long id) {
        log.debug("Request to get MeanOfContact : {}", id);
        MeanOfContact meanOfContact = meanOfContactRepository.findOne(id);
        return meanOfContactMapper.toDto(meanOfContact);
    }

    /**
     * Delete the meanOfContact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeanOfContact : {}", id);
        meanOfContactRepository.delete(id);
        meanOfContactSearchRepository.delete(id);
    }

    /**
     * Search for the meanOfContact corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeanOfContactDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MeanOfContacts for query {}", query);
        Page<MeanOfContact> result = meanOfContactSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(meanOfContactMapper::toDto);
    }
}
