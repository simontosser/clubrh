package com.poleemploi.cvtheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poleemploi.cvtheque.service.DocumentProfilService;
import com.poleemploi.cvtheque.web.rest.errors.BadRequestAlertException;
import com.poleemploi.cvtheque.web.rest.util.HeaderUtil;
import com.poleemploi.cvtheque.web.rest.util.PaginationUtil;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DocumentProfil.
 */
@RestController
@RequestMapping("/api")
public class DocumentProfilResource {

    private final Logger log = LoggerFactory.getLogger(DocumentProfilResource.class);

    private static final String ENTITY_NAME = "documentProfil";

    private final DocumentProfilService documentProfilService;

    public DocumentProfilResource(DocumentProfilService documentProfilService) {
        this.documentProfilService = documentProfilService;
    }

    /**
     * POST  /document-profils : Create a new documentProfil.
     *
     * @param documentProfilDTO the documentProfilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentProfilDTO, or with status 400 (Bad Request) if the documentProfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-profils")
    @Timed
    public ResponseEntity<DocumentProfilDTO> createDocumentProfil(@Valid @RequestBody DocumentProfilDTO documentProfilDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentProfil : {}", documentProfilDTO);
        if (documentProfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentProfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentProfilDTO result = documentProfilService.save(documentProfilDTO);
        return ResponseEntity.created(new URI("/api/document-profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-profils : Updates an existing documentProfil.
     *
     * @param documentProfilDTO the documentProfilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentProfilDTO,
     * or with status 400 (Bad Request) if the documentProfilDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentProfilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-profils")
    @Timed
    public ResponseEntity<DocumentProfilDTO> updateDocumentProfil(@Valid @RequestBody DocumentProfilDTO documentProfilDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentProfil : {}", documentProfilDTO);
        if (documentProfilDTO.getId() == null) {
            return createDocumentProfil(documentProfilDTO);
        }
        DocumentProfilDTO result = documentProfilService.save(documentProfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentProfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-profils : get all the documentProfils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentProfils in body
     */
    @GetMapping("/document-profils")
    @Timed
    public ResponseEntity<List<DocumentProfilDTO>> getAllDocumentProfils(Pageable pageable) {
        log.debug("REST request to get a page of DocumentProfils");
        Page<DocumentProfilDTO> page = documentProfilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-profils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-profils/:id : get the "id" documentProfil.
     *
     * @param id the id of the documentProfilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentProfilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/document-profils/{id}")
    @Timed
    public ResponseEntity<DocumentProfilDTO> getDocumentProfil(@PathVariable Long id) {
        log.debug("REST request to get DocumentProfil : {}", id);
        DocumentProfilDTO documentProfilDTO = documentProfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentProfilDTO));
    }

    /**
     * DELETE  /document-profils/:id : delete the "id" documentProfil.
     *
     * @param id the id of the documentProfilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-profils/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentProfil(@PathVariable Long id) {
        log.debug("REST request to delete DocumentProfil : {}", id);
        documentProfilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/document-profils?query=:query : search for the documentProfil corresponding
     * to the query.
     *
     * @param query the query of the documentProfil search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/document-profils")
    @Timed
    public ResponseEntity<List<DocumentProfilDTO>> searchDocumentProfils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentProfils for query {}", query);
        Page<DocumentProfilDTO> page = documentProfilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/document-profils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
