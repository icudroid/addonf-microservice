package com.adloveyou.ms.goosegame.repository.search;

import com.adloveyou.ms.goosegame.domain.GooseLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GooseLevel entity.
 */
public interface GooseLevelSearchRepository extends ElasticsearchRepository<GooseLevel, Long> {
}
