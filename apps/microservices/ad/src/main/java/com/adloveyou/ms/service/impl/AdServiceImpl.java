package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AdService;
import com.adloveyou.ms.domain.Ad;
import com.adloveyou.ms.service.dto.AdDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Ad.
 */
@Service
@Transactional
public class AdServiceImpl extends GenericServiceWithDTOImpl<Ad, AdDTO, Long> implements AdService{

    public AdServiceImpl(EntityMapper<AdDTO, Ad> mapper, ElasticsearchRepository<Ad, Long> elasticsearchRepository, JpaRepository<Ad, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
