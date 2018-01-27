package com.adloveyou.ms.ad.service.impl;

import com.adloveyou.ms.ad.service.BidCategoryMediaService;
import com.adloveyou.ms.ad.domain.BidCategoryMedia;
import com.adloveyou.ms.ad.repository.BidCategoryMediaRepository;
import com.adloveyou.ms.ad.repository.search.BidCategoryMediaSearchRepository;
import com.adloveyou.ms.ad.service.dto.BidCategoryMediaDTO;
import com.adloveyou.ms.ad.service.mapper.BidCategoryMediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BidCategoryMedia.
 */
@Service
@Transactional
public class BidCategoryMediaServiceImpl implements BidCategoryMediaService {

    private final Logger log = LoggerFactory.getLogger(BidCategoryMediaServiceImpl.class);

    private final BidCategoryMediaRepository bidCategoryMediaRepository;

    private final BidCategoryMediaMapper bidCategoryMediaMapper;

    private final BidCategoryMediaSearchRepository bidCategoryMediaSearchRepository;

    public BidCategoryMediaServiceImpl(BidCategoryMediaRepository bidCategoryMediaRepository, BidCategoryMediaMapper bidCategoryMediaMapper, BidCategoryMediaSearchRepository bidCategoryMediaSearchRepository) {
        this.bidCategoryMediaRepository = bidCategoryMediaRepository;
        this.bidCategoryMediaMapper = bidCategoryMediaMapper;
        this.bidCategoryMediaSearchRepository = bidCategoryMediaSearchRepository;
    }

    /**
     * Save a bidCategoryMedia.
     *
     * @param bidCategoryMediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BidCategoryMediaDTO save(BidCategoryMediaDTO bidCategoryMediaDTO) {
        log.debug("Request to save BidCategoryMedia : {}", bidCategoryMediaDTO);
        BidCategoryMedia bidCategoryMedia = bidCategoryMediaMapper.toEntity(bidCategoryMediaDTO);
        bidCategoryMedia = bidCategoryMediaRepository.save(bidCategoryMedia);
        BidCategoryMediaDTO result = bidCategoryMediaMapper.toDto(bidCategoryMedia);
        bidCategoryMediaSearchRepository.save(bidCategoryMedia);
        return result;
    }

    /**
     * Get all the bidCategoryMedias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BidCategoryMediaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BidCategoryMedias");
        return bidCategoryMediaRepository.findAll(pageable)
            .map(bidCategoryMediaMapper::toDto);
    }

    /**
     * Get one bidCategoryMedia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BidCategoryMediaDTO findOne(Long id) {
        log.debug("Request to get BidCategoryMedia : {}", id);
        BidCategoryMedia bidCategoryMedia = bidCategoryMediaRepository.findOne(id);
        return bidCategoryMediaMapper.toDto(bidCategoryMedia);
    }

    /**
     * Delete the bidCategoryMedia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BidCategoryMedia : {}", id);
        bidCategoryMediaRepository.delete(id);
        bidCategoryMediaSearchRepository.delete(id);
    }

    /**
     * Search for the bidCategoryMedia corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BidCategoryMediaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BidCategoryMedias for query {}", query);
        Page<BidCategoryMedia> result = bidCategoryMediaSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(bidCategoryMediaMapper::toDto);
    }
}
