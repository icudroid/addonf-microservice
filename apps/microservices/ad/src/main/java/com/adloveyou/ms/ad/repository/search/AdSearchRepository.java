package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.Ad;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ad entity.
 */
public interface AdSearchRepository extends ElasticsearchRepository<Ad, Long> {
}
