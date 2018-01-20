package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.SectorService;
import com.adloveyou.ms.domain.Sector;
import com.adloveyou.ms.service.dto.SectorDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Sector.
 */
@Service
@Transactional
public class SectorServiceImpl extends GenericServiceWithDTOImpl<Sector, SectorDTO, Long> implements SectorService{

    public SectorServiceImpl(EntityMapper<SectorDTO, Sector> mapper, ElasticsearchRepository<Sector, Long> elasticsearchRepository, JpaRepository<Sector, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
