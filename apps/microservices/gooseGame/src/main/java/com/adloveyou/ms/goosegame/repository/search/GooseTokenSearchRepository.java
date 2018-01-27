package com.adloveyou.ms.goosegame.repository.search;

import com.adloveyou.ms.goosegame.domain.GooseToken;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GooseToken entity.
 */
public interface GooseTokenSearchRepository extends ElasticsearchRepository<GooseToken, Long> {
}
