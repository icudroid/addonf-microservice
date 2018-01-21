package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.ad.AdCampaing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdCampaing entity.
 */
public interface AdCampaingSearchRepository extends ElasticsearchRepository<AdCampaing, Long> {
}
