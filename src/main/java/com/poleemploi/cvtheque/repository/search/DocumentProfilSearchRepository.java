package com.poleemploi.cvtheque.repository.search;

import com.poleemploi.cvtheque.domain.DocumentProfil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DocumentProfil entity.
 */
public interface DocumentProfilSearchRepository extends ElasticsearchRepository<DocumentProfil, Long> {
}
