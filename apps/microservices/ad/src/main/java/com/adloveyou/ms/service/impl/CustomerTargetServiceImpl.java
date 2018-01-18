package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.CustomerTargetService;
import com.adloveyou.ms.domain.CustomerTarget;
import com.adloveyou.ms.service.dto.CustomerTargetDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing CustomerTarget.
 */
@Service
@Transactional
public class CustomerTargetServiceImpl extends GenericServiceImpl<CustomerTarget, CustomerTargetDTO, Long> implements CustomerTargetService{

    public CustomerTargetServiceImpl(EntityMapper<CustomerTargetDTO, CustomerTarget> mapper, ElasticsearchRepository<CustomerTarget, Long> elasticsearchRepository, JpaRepository<CustomerTarget, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
