package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.BrandUserService;
import com.adloveyou.ms.domain.brand.BrandUser;
import com.adloveyou.ms.service.dto.BrandUserDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing BrandUser.
 */
@Service
@Transactional
public class BrandUserServiceImpl extends GenericServiceWithDTOImpl<BrandUser, BrandUserDTO, Long> implements BrandUserService{

    public BrandUserServiceImpl(EntityMapper<BrandUserDTO, BrandUser> mapper, ElasticsearchRepository<BrandUser, Long> elasticsearchRepository, JpaRepository<BrandUser, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
