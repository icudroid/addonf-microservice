package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.MediaTargetService;
import com.adloveyou.ms.domain.ad.MediaTarget;
import com.adloveyou.ms.service.dto.MediaTargetDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MediaTarget.
 */
@Service
@Transactional
public class MediaTargetServiceImpl extends GenericServiceWithDTOImpl<MediaTarget, MediaTargetDTO, Long> implements MediaTargetService{

    public MediaTargetServiceImpl(EntityMapper<MediaTargetDTO, MediaTarget> mapper, ElasticsearchRepository<MediaTarget, Long> elasticsearchRepository, JpaRepository<MediaTarget, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
