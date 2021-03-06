package com.poleemploi.cvtheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import com.poleemploi.cvtheque.security.SecurityUtils;
import com.poleemploi.cvtheque.service.ElasticsearchIndexService;
import com.poleemploi.cvtheque.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * REST controller for managing Elasticsearch index.
 */
@RestController
@RequestMapping("/api")
public class ElasticsearchIndexResource {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexResource.class);

    /** The elasticsearch index service. */
    private final ElasticsearchIndexService elasticsearchIndexService;

    /**
     * Instantiates a new elasticsearch index resource.
     *
     * @param elasticsearchIndexService the elasticsearch index service
     */
    public ElasticsearchIndexResource(ElasticsearchIndexService elasticsearchIndexService) {
        this.elasticsearchIndexService = elasticsearchIndexService;
    }

    /**
     * POST  /elasticsearch/index - Reindex all Elasticsearch documents.
     *
     * @return the response entity
     * @throws URISyntaxException the URI syntax exception
     */
    @PostMapping("/elasticsearch/index")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> reindexAll() throws URISyntaxException {
        log.info("REST request to reindex Elasticsearch by user : {}", SecurityUtils.getCurrentUserLogin());
        elasticsearchIndexService.reindexAll();
        return ResponseEntity.accepted()
            .headers(HeaderUtil.createAlert("elasticsearch.reindex.accepted", null))
            .build();
    }
}
