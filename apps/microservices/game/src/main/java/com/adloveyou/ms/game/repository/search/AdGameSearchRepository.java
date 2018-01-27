package com.adloveyou.ms.game.repository.search;

import com.adloveyou.ms.game.domain.AdGame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdGame entity.
 */
public interface AdGameSearchRepository extends ElasticsearchRepository<AdGame, Long> {
}
