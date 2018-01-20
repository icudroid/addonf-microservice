package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.BidCategoryMediaService;
import com.adloveyou.ms.domain.BidCategoryMedia;
import com.adloveyou.ms.service.dto.BidCategoryMediaDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing BidCategoryMedia.
 */
@Service
@Transactional
public class BidCategoryMediaServiceImpl  extends GenericServiceWithDTOImpl<BidCategoryMedia, BidCategoryMediaDTO, Long> implements BidCategoryMediaService{

    public BidCategoryMediaServiceImpl(EntityMapper<BidCategoryMediaDTO, BidCategoryMedia> mapper, ElasticsearchRepository<BidCategoryMedia, Long> elasticsearchRepository, JpaRepository<BidCategoryMedia, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
