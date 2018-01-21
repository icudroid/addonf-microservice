package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.media.Media;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Media entity.
 */
public interface MediaSearchRepository extends ElasticsearchRepository<Media, Long> {
}
