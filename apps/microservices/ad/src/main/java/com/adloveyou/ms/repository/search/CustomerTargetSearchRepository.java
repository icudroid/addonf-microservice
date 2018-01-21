package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.ad.CustomerTarget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerTarget entity.
 */
public interface CustomerTargetSearchRepository extends ElasticsearchRepository<CustomerTarget, Long> {
}
