package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.ad.Ad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ad entity.
 */
public interface AdSearchRepository extends ElasticsearchRepository<Ad, Long> {
}
