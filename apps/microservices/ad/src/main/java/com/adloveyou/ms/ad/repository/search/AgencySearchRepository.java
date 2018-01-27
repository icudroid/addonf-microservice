package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.Agency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Agency entity.
 */
public interface AgencySearchRepository extends ElasticsearchRepository<Agency, Long> {
}
