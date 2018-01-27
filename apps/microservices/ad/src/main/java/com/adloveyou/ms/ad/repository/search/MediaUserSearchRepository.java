package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.MediaUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MediaUser entity.
 */
public interface MediaUserSearchRepository extends ElasticsearchRepository<MediaUser, Long> {
}
