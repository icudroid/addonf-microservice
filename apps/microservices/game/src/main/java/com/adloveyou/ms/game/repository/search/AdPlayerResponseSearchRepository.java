package com.adloveyou.ms.game.repository.search;

import com.adloveyou.ms.game.domain.AdPlayerResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdPlayerResponse entity.
 */
public interface AdPlayerResponseSearchRepository extends ElasticsearchRepository<AdPlayerResponse, Long> {
}
