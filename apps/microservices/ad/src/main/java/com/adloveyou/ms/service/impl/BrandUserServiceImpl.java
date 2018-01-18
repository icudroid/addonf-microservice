package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.BrandUserService;
import com.adloveyou.ms.domain.BrandUser;
import com.adloveyou.ms.repository.BrandUserRepository;
import com.adloveyou.ms.repository.search.BrandUserSearchRepository;
import com.adloveyou.ms.service.dto.BrandUserDTO;
import com.adloveyou.ms.service.mapper.BrandUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BrandUser.
 */
@Service
@Transactional
public class BrandUserServiceImpl implements BrandUserService{

    private final Logger log = LoggerFactory.getLogger(BrandUserServiceImpl.class);

    private final BrandUserRepository brandUserRepository;

    private final BrandUserMapper brandUserMapper;

    private final BrandUserSearchRepository brandUserSearchRepository;

    public BrandUserServiceImpl(BrandUserRepository brandUserRepository, BrandUserMapper brandUserMapper, BrandUserSearchRepository brandUserSearchRepository) {
        this.brandUserRepository = brandUserRepository;
        this.brandUserMapper = brandUserMapper;
        this.brandUserSearchRepository = brandUserSearchRepository;
    }

    /**
     * Save a brandUser.
     *
     * @param brandUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BrandUserDTO save(BrandUserDTO brandUserDTO) {
        log.debug("Request to save BrandUser : {}", brandUserDTO);
        BrandUser brandUser = brandUserMapper.toEntity(brandUserDTO);
        brandUser = brandUserRepository.save(brandUser);
        BrandUserDTO result = brandUserMapper.toDto(brandUser);
        brandUserSearchRepository.save(brandUser);
        return result;
    }

    /**
     * Get all the brandUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BrandUsers");
        return brandUserRepository.findAll(pageable)
            .map(brandUserMapper::toDto);
    }

    /**
     * Get one brandUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BrandUserDTO findOne(Long id) {
        log.debug("Request to get BrandUser : {}", id);
        BrandUser brandUser = brandUserRepository.findOne(id);
        return brandUserMapper.toDto(brandUser);
    }

    /**
     * Delete the brandUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BrandUser : {}", id);
        brandUserRepository.delete(id);
        brandUserSearchRepository.delete(id);
    }

    /**
     * Search for the brandUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BrandUsers for query {}", query);
        Page<BrandUser> result = brandUserSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(brandUserMapper::toDto);
    }
}
