package com.adloveyou.ms.game.repository.search;

import com.adloveyou.ms.game.domain.Possibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Possibility entity.
 */
public interface PossibilitySearchRepository extends ElasticsearchRepository<Possibility, Long> {
}
