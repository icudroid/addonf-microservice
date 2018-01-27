package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.AdConfiguration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdConfiguration entity.
 */
public interface AdConfigurationSearchRepository extends ElasticsearchRepository<AdConfiguration, Long> {
}
