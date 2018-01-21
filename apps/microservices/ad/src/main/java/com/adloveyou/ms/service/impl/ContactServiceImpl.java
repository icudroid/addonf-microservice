package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.ContactService;
import com.adloveyou.ms.domain.contact.Contact;
import com.adloveyou.ms.service.dto.ContactDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Contact.
 */
@Service
@Transactional
public class ContactServiceImpl extends GenericServiceWithDTOImpl<Contact, ContactDTO, Long> implements ContactService{

    public ContactServiceImpl(EntityMapper<ContactDTO, Contact> mapper, ElasticsearchRepository<Contact, Long> elasticsearchRepository, JpaRepository<Contact, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
