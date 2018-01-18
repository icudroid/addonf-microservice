package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.MeanOfContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MeanOfContact entity.
 */
public interface MeanOfContactSearchRepository extends ElasticsearchRepository<MeanOfContact, Long> {
}
