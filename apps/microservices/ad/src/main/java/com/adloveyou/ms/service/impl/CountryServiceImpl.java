package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.CountryService;
import com.adloveyou.ms.domain.country.Country;
import com.adloveyou.ms.repository.CountryRepository;
import com.adloveyou.ms.repository.search.CountrySearchRepository;
import com.adloveyou.ms.service.GenericServiceWithDTO;
import com.adloveyou.ms.service.dto.CountryDTO;
import com.adloveyou.ms.service.mapper.CountryMapper;
import com.adloveyou.ms.service.mapper.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryServiceImpl  extends GenericServiceWithDTOImpl<Country, CountryDTO, Long> implements CountryService{

    public CountryServiceImpl(EntityMapper<CountryDTO, Country> mapper, ElasticsearchRepository<Country, Long> elasticsearchRepository, JpaRepository<Country, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
