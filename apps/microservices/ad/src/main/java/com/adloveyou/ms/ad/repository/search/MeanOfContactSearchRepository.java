package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.MeanOfContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MeanOfContact entity.
 */
public interface MeanOfContactSearchRepository extends ElasticsearchRepository<MeanOfContact, Long> {
}
