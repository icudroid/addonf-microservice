package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.BrandService;
import com.adloveyou.ms.domain.Brand;
import com.adloveyou.ms.service.dto.BrandDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Brand.
 */
@Service
@Transactional
public class BrandServiceImpl extends GenericServiceWithDTOImpl<Brand, BrandDTO, Long> implements BrandService{

    public BrandServiceImpl(EntityMapper<BrandDTO, Brand> mapper, ElasticsearchRepository<Brand, Long> elasticsearchRepository, JpaRepository<Brand, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
