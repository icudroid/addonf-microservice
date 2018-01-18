package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.MediaUserService;
import com.adloveyou.ms.domain.MediaUser;
import com.adloveyou.ms.service.dto.MediaUserDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MediaUser.
 */
@Service
@Transactional
public class MediaUserServiceImpl extends GenericServiceImpl<MediaUser, MediaUserDTO, Long> implements MediaUserService{

    public MediaUserServiceImpl(EntityMapper<MediaUserDTO, MediaUser> mapper, ElasticsearchRepository<MediaUser, Long> elasticsearchRepository, JpaRepository<MediaUser, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
