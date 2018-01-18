package com.adloveyou.ms.repository.search;

import com.adloveyou.ms.domain.FileAttachement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FileAttachement entity.
 */
public interface FileAttachementSearchRepository extends ElasticsearchRepository<FileAttachement, Long> {
}
