package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.MediaTarget;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MediaTarget entity.
 */
public interface MediaTargetSearchRepository extends ElasticsearchRepository<MediaTarget, Long> {
}
