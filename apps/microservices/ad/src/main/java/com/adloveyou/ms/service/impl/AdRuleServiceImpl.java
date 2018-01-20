package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.AdRuleService;
import com.adloveyou.ms.domain.AdRule;
import com.adloveyou.ms.service.dto.AdRuleDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing AdRule.
 */
@Service
@Transactional
public class AdRuleServiceImpl extends GenericServiceWithDTOImpl<AdRule,AdRuleDTO,Long> implements AdRuleService{

    public AdRuleServiceImpl(EntityMapper<AdRuleDTO, AdRule> mapper, ElasticsearchRepository<AdRule, Long> elasticsearchRepository, JpaRepository<AdRule, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
