package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.CustomerTarget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerTarget entity.
 */
public interface CustomerTargetSearchRepository extends ElasticsearchRepository<CustomerTarget, Long> {
}
