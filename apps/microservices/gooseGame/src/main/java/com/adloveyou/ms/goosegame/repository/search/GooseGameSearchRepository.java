package com.adloveyou.ms.goosegame.repository.search;

import com.adloveyou.ms.goosegame.domain.GooseGame;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GooseGame entity.
 */
public interface GooseGameSearchRepository extends ElasticsearchRepository<GooseGame, Long> {
}
