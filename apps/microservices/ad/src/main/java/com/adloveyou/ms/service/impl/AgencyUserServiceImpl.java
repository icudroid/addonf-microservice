package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AgencyUserService;
import com.adloveyou.ms.domain.agency.AgencyUser;
import com.adloveyou.ms.service.dto.AgencyUserDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing AgencyUser.
 */
@Service
@Transactional
public class AgencyUserServiceImpl extends GenericServiceWithDTOImpl<AgencyUser, AgencyUserDTO, Long> implements AgencyUserService{

    public AgencyUserServiceImpl(EntityMapper<AgencyUserDTO, AgencyUser> mapper, ElasticsearchRepository<AgencyUser, Long> elasticsearchRepository, JpaRepository<AgencyUser, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
