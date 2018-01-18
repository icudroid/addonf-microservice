package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.MediaTarget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MediaTarget entity.
 */
public interface MediaTargetSearchRepository extends ElasticsearchRepository<MediaTarget, Long> {
}
