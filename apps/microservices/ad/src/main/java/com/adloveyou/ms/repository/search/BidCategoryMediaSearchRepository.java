package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.BidCategoryMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BidCategoryMedia entity.
 */
public interface BidCategoryMediaSearchRepository extends ElasticsearchRepository<BidCategoryMedia, Long> {
}
