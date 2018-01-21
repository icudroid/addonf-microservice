package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.ad.Sector;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sector entity.
 */
public interface SectorSearchRepository extends ElasticsearchRepository<Sector, Long> {
}
