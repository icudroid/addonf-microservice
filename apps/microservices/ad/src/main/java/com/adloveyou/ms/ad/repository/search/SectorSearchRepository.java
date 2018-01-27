package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.Sector;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Sector entity.
 */
public interface SectorSearchRepository extends ElasticsearchRepository<Sector, Long> {
}
