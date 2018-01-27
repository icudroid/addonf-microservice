package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.AdRestriction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdRestriction entity.
 */
public interface AdRestrictionSearchRepository extends ElasticsearchRepository<AdRestriction, Long> {
}
