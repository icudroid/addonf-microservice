package com.adloveyou.ms.ad.repository.search;

import com.adloveyou.ms.ad.domain.FileAttachement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FileAttachement entity.
 */
public interface FileAttachementSearchRepository extends ElasticsearchRepository<FileAttachement, Long> {
}
