package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.Agency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Agency entity.
 */
public interface AgencySearchRepository extends ElasticsearchRepository<Agency, Long> {
}
