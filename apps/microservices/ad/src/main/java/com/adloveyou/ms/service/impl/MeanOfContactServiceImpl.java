package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.MeanOfContactService;
import com.adloveyou.ms.domain.contact.MeanOfContact;
import com.adloveyou.ms.service.dto.MeanOfContactDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing MeanOfContact.
 */
@Service
@Transactional
public class MeanOfContactServiceImpl extends GenericServiceWithDTOImpl<MeanOfContact, MeanOfContactDTO, Long> implements MeanOfContactService{

    public MeanOfContactServiceImpl(EntityMapper<MeanOfContactDTO, MeanOfContact> mapper, ElasticsearchRepository<MeanOfContact, Long> elasticsearchRepository, JpaRepository<MeanOfContact, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
