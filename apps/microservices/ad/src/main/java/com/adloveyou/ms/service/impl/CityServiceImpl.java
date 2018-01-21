package com.adloveyou.ms.service.impl;

import com.adloveyou.ms.service.CityService;
import com.adloveyou.ms.domain.country.City;
import com.adloveyou.ms.repository.CityRepository;
import com.adloveyou.ms.repository.search.CitySearchRepository;
import com.adloveyou.ms.service.GenericServiceWithDTO;
import com.adloveyou.ms.service.dto.CityDTO;
import com.adloveyou.ms.service.mapper.CityMapper;
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
 * Service Implementation for managing City.
 */
@Service
@Transactional
public class CityServiceImpl extends GenericServiceWithDTOImpl<City, CityDTO, Long> implements CityService{


    public CityServiceImpl(EntityMapper<CityDTO, City> mapper, ElasticsearchRepository<City, Long> elasticsearchRepository, JpaRepository<City, Long> repository) {
        super(mapper, elasticsearchRepository, repository);
    }
}
