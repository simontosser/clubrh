package com.poleemploi.cvtheque.repository.search;

import com.poleemploi.cvtheque.domain.RecrutementProfil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RecrutementProfil entity.
 */
public interface RecrutementProfilSearchRepository extends ElasticsearchRepository<RecrutementProfil, Long> {
}
