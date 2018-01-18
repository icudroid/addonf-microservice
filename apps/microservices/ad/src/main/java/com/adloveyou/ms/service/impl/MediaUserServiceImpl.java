package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.MediaUserService;
import com.adloveyou.ms.domain.MediaUser;
import com.adloveyou.ms.repository.MediaUserRepository;
import com.adloveyou.ms.repository.search.MediaUserSearchRepository;
import com.adloveyou.ms.service.dto.MediaUserDTO;
import com.adloveyou.ms.service.mapper.MediaUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MediaUser.
 */
@Service
@Transactional
public class MediaUserServiceImpl implements MediaUserService{

    private final Logger log = LoggerFactory.getLogger(MediaUserServiceImpl.class);

    private final MediaUserRepository mediaUserRepository;

    private final MediaUserMapper mediaUserMapper;

    private final MediaUserSearchRepository mediaUserSearchRepository;

    public MediaUserServiceImpl(MediaUserRepository mediaUserRepository, MediaUserMapper mediaUserMapper, MediaUserSearchRepository mediaUserSearchRepository) {
        this.mediaUserRepository = mediaUserRepository;
        this.mediaUserMapper = mediaUserMapper;
        this.mediaUserSearchRepository = mediaUserSearchRepository;
    }

    /**
     * Save a mediaUser.
     *
     * @param mediaUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaUserDTO save(MediaUserDTO mediaUserDTO) {
        log.debug("Request to save MediaUser : {}", mediaUserDTO);
        MediaUser mediaUser = mediaUserMapper.toEntity(mediaUserDTO);
        mediaUser = mediaUserRepository.save(mediaUser);
        MediaUserDTO result = mediaUserMapper.toDto(mediaUser);
        mediaUserSearchRepository.save(mediaUser);
        return result;
    }

    /**
     * Get all the mediaUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MediaUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MediaUsers");
        return mediaUserRepository.findAll(pageable)
            .map(mediaUserMapper::toDto);
    }

    /**
     * Get one mediaUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MediaUserDTO findOne(Long id) {
        log.debug("Request to get MediaUser : {}", id);
        MediaUser mediaUser = mediaUserRepository.findOne(id);
        return mediaUserMapper.toDto(mediaUser);
    }

    /**
     * Delete the mediaUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MediaUser : {}", id);
        mediaUserRepository.delete(id);
        mediaUserSearchRepository.delete(id);
    }

    /**
     * Search for the mediaUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MediaUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MediaUsers for query {}", query);
        Page<MediaUser> result = mediaUserSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(mediaUserMapper::toDto);
    }
}
