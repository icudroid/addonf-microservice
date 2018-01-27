package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.BidCategoryMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BidCategoryMedia entity.
 */
public interface BidCategoryMediaSearchRepository extends ElasticsearchRepository<BidCategoryMedia, Long> {
}
