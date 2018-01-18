package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.MediaUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MediaUser entity.
 */
public interface MediaUserSearchRepository extends ElasticsearchRepository<MediaUser, Long> {
}
