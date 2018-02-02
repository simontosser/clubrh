package com.poleemploi.cvtheque.repository.search;

import com.poleemploi.cvtheque.domain.ShareProfil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShareProfil entity.
 */
public interface ShareProfilSearchRepository extends ElasticsearchRepository<ShareProfil, Long> {
}
