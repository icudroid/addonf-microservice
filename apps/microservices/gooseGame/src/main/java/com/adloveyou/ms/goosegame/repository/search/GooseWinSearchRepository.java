package com.adloveyou.ms.goosegame.repository.search;

import com.adloveyou.ms.goosegame.domain.GooseWin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GooseWin entity.
 */
public interface GooseWinSearchRepository extends ElasticsearchRepository<GooseWin, Long> {
}
