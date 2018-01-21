package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.rule.AdRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AdRule entity.
 */
public interface AdRuleSearchRepository extends ElasticsearchRepository<AdRule, Long> {
}
