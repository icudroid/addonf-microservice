package com.adloveyou.ms.game.repository.search;

import com.adloveyou.ms.game.domain.AdChoise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdChoise entity.
 */
public interface AdChoiseSearchRepository extends ElasticsearchRepository<AdChoise, Long> {
}
