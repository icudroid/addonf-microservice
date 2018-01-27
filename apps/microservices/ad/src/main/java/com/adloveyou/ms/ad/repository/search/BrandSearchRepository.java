package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.Brand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Brand entity.
 */
public interface BrandSearchRepository extends ElasticsearchRepository<Brand, Long> {
}
