package com.adloveyou.ms.game.repository.search;

import com.adloveyou.ms.game.domain.AdScore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdScore entity.
 */
public interface AdScoreSearchRepository extends ElasticsearchRepository<AdScore, Long> {
}
