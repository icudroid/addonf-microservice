package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.BrandUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BrandUser entity.
 */
public interface BrandUserSearchRepository extends ElasticsearchRepository<BrandUser, Long> {
}
