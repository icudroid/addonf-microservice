package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.BrandUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BrandUser entity.
 */
public interface BrandUserSearchRepository extends ElasticsearchRepository<BrandUser, Long> {
}
