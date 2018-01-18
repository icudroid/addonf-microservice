package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.FileAttachementService;
import com.adloveyou.ms.domain.FileAttachement;
import com.adloveyou.ms.service.dto.FileAttachementDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing FileAttachement.
 */
@Service
@Transactional
public class FileAttachementServiceImpl extends GenericServiceImpl<FileAttachement, FileAttachementDTO, Long> implements FileAttachementService{

    public FileAttachementServiceImpl(EntityMapper<FileAttachementDTO, FileAttachement> mapper, ElasticsearchRepository<FileAttachement, Long> elasticsearchRepository, JpaRepository<FileAttachement, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
