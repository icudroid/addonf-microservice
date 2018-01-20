package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.GenericServiceWithDTO;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class GenericServiceWithDTOImpl<DOMAIN, DTO, PK extends Serializable> implements GenericServiceWithDTO<DOMAIN, DTO, PK> {


    final protected EntityMapper<DTO, DOMAIN> mapper;
    final protected ElasticsearchRepository<DOMAIN, PK> elasticsearchRepository;
    final protected JpaRepository<DOMAIN, PK> repository;
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());


    public GenericServiceWithDTOImpl(EntityMapper<DTO, DOMAIN> mapper, ElasticsearchRepository<DOMAIN, PK> elasticsearchRepository, JpaRepository<DOMAIN, PK> repository) {
        this.mapper = mapper;
        this.elasticsearchRepository = elasticsearchRepository;
        this.repository = repository;
    }


    @Override
    public DTO save(DTO dto) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to save {} : {}", aClass.getSimpleName(), dto);
        DOMAIN aDOMAIN = mapper.toEntity(dto);
        aDOMAIN = repository.save(aDOMAIN);
        DTO result = mapper.toDto(aDOMAIN);
        elasticsearchRepository.save(aDOMAIN);
        return result;
    }

    @Override
    public Page<DTO> findAll(Pageable pageable) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to get all {}", aClass.getSimpleName());
        return repository.findAll(pageable)
            .map(mapper::toDto);
    }

    @Override
    public DTO findOne(PK id) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to get {} : {}", aClass.getSimpleName(), id);
        DOMAIN aDOMAIN = repository.findOne(id);
        return mapper.toDto(aDOMAIN);
    }

    @Override
    public void delete(PK id) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to delete {} : {}", aClass.getSimpleName(), id);
        repository.delete(id);
        elasticsearchRepository.delete(id);
    }

    @Override
    public Page<DTO> search(String query, Pageable pageable) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to search for a page of {} for query {}", aClass.getSimpleName(), query);
        Page<DOMAIN> result = elasticsearchRepository.search(queryStringQuery(query), pageable);
        return result.map(mapper::toDto);
    }


}
