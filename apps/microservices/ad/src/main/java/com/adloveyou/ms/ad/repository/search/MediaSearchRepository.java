package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.Media;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Media entity.
 */
public interface MediaSearchRepository extends ElasticsearchRepository<Media, Long> {
}
