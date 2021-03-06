package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.GenericService;
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

public class GenericServiceImpl<DOMAIN, PK extends Serializable> implements GenericService<DOMAIN, PK> {


    final protected ElasticsearchRepository<DOMAIN, PK> elasticsearchRepository;
    final protected JpaRepository<DOMAIN, PK> repository;
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());


    public GenericServiceImpl(ElasticsearchRepository<DOMAIN, PK> elasticsearchRepository, JpaRepository<DOMAIN, PK> repository) {
        this.elasticsearchRepository = elasticsearchRepository;
        this.repository = repository;
    }


    @Override
    public DOMAIN save(DOMAIN dto) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to save {} : {}", aClass.getSimpleName(), dto);
        DOMAIN result = repository.save(dto);
        elasticsearchRepository.save(result);
        return result;
    }

    @Override
    public Page<DOMAIN> findAll(Pageable pageable) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to get all {}", aClass.getSimpleName());
        return repository.findAll(pageable);
    }

    @Override
    public DOMAIN findOne(PK id) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to get {} : {}", aClass.getSimpleName(), id);
        return repository.findOne(id);
    }

    @Override
    public void delete(PK id) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to delete {} : {}", aClass.getSimpleName(), id);
        repository.delete(id);
        elasticsearchRepository.delete(id);
    }

    @Override
    public Page<DOMAIN> search(String query, Pageable pageable) {
        Class<DOMAIN> aClass = (Class<DOMAIN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        log.debug("Request to search for a page of {} for query {}", aClass.getSimpleName(), query);
        Page<DOMAIN> result = elasticsearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }


}
