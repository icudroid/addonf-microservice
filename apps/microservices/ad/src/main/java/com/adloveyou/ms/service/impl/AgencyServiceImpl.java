package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AgencyService;
import com.adloveyou.ms.domain.agency.Agency;
import com.adloveyou.ms.service.dto.AgencyDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Agency.
 */
@Service
@Transactional
public class AgencyServiceImpl extends GenericServiceWithDTOImpl<Agency, AgencyDTO, Long> implements AgencyService{

    public AgencyServiceImpl(EntityMapper<AgencyDTO, Agency> mapper, ElasticsearchRepository<Agency, Long> elasticsearchRepository, JpaRepository<Agency, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
