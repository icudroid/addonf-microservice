package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.AgencyUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AgencyUser entity.
 */
public interface AgencyUserSearchRepository extends ElasticsearchRepository<AgencyUser, Long> {
}
