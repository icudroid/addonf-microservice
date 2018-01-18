package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.MediaService;
import com.adloveyou.ms.domain.Media;
import com.adloveyou.ms.service.dto.MediaDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Media.
 */
@Service
@Transactional
public class MediaServiceImpl  extends GenericServiceImpl<Media, MediaDTO, Long> implements MediaService{

    public MediaServiceImpl(EntityMapper<MediaDTO, Media> mapper, ElasticsearchRepository<Media, Long> elasticsearchRepository, JpaRepository<Media, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
