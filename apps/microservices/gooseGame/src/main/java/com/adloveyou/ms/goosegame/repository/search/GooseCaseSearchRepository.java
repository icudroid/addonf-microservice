package com.adloveyou.ms.goosegame.repository.search;

import com.adloveyou.ms.goosegame.domain.GooseCase;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GooseCase entity.
 */
public interface GooseCaseSearchRepository extends ElasticsearchRepository<GooseCase, Long> {
}
