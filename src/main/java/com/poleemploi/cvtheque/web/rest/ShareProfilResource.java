package com.poleemploi.cvtheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poleemploi.cvtheque.domain.ShareProfil;
import com.poleemploi.cvtheque.service.ShareProfilService;
import com.poleemploi.cvtheque.web.rest.errors.BadRequestAlertException;
import com.poleemploi.cvtheque.web.rest.util.HeaderUtil;
import com.poleemploi.cvtheque.web.rest.util.PaginationUtil;
import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;
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
 * REST controller for managing ShareProfil.
 */
@RestController
@RequestMapping("/api")
public class ShareProfilResource {

    private final Logger log = LoggerFactory.getLogger(ShareProfilResource.class);

    private static final String ENTITY_NAME = "shareProfil";

    private final ShareProfilService shareProfilService;

    public ShareProfilResource(ShareProfilService shareProfilService) {
        this.shareProfilService = shareProfilService;
    }

    /**
     * POST  /share-profils : Create a new shareProfil.
     *
     * @param shareProfilDTO the shareProfilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shareProfilDTO, or with status 400 (Bad Request) if the shareProfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/share-profils")
    @Timed
    public ResponseEntity<ShareProfilDTO> createShareProfil(@Valid @RequestBody ShareProfilDTO shareProfilDTO) throws URISyntaxException {
        log.debug("REST request to save ShareProfil : {}", shareProfilDTO);
        if (shareProfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new shareProfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShareProfilDTO result = shareProfilService.save(shareProfilDTO);
        return ResponseEntity.created(new URI("/api/share-profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /share-profils : Updates an existing shareProfil.
     *
     * @param shareProfilDTO the shareProfilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shareProfilDTO,
     * or with status 400 (Bad Request) if the shareProfilDTO is not valid,
     * or with status 500 (Internal Server Error) if the shareProfilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/share-profils")
    @Timed
    public ResponseEntity<ShareProfilDTO> updateShareProfil(@Valid @RequestBody ShareProfilDTO shareProfilDTO) throws URISyntaxException {
        log.debug("REST request to update ShareProfil : {}", shareProfilDTO);
        if (shareProfilDTO.getId() == null) {
            return createShareProfil(shareProfilDTO);
        }
        ShareProfilDTO result = shareProfilService.update(shareProfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shareProfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /share-profils : get all the shareProfils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shareProfils in body
     */
    @GetMapping("/share-profils")
    @Timed
    public ResponseEntity<List<ShareProfilDTO>> getAllShareProfils(Pageable pageable) {
        log.debug("REST request to get a page of ShareProfils");
        Page<ShareProfilDTO> page = shareProfilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/share-profils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /share-profils/current : get all the shareProfils with current user.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shareProfils in body
     */
    @GetMapping("/share-profils/current")
    @Timed
    public ResponseEntity<List<ShareProfilDTO>> getAllShareProfilsWithCurrentUser(Pageable pageable) {
        log.debug("REST request to get a page of ShareProfils with current user");
        Page<ShareProfilDTO> page = shareProfilService.findAllWithCurrentUserCompany(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/share-profils/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /share-profils/:id : get the "id" shareProfil.
     *
     * @param id the id of the shareProfilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shareProfilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/share-profils/{id}")
    @Timed
    public ResponseEntity<ShareProfilDTO> getShareProfil(@PathVariable Long id) {
        log.debug("REST request to get ShareProfil : {}", id);
        ShareProfilDTO shareProfilDTO = shareProfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shareProfilDTO));
    }

    /**
     * DELETE  /share-profils/:id : delete the "id" shareProfil.
     *
     * @param id the id of the shareProfilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/share-profils/{id}")
    @Timed
    public ResponseEntity<Void> deleteShareProfil(@PathVariable Long id) {
        log.debug("REST request to delete ShareProfil : {}", id);
        shareProfilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/share-profils?query=:query : search for the shareProfil corresponding
     * to the query.
     *
     * @param query the query of the shareProfil search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/share-profils")
    @Timed
    public ResponseEntity<List<ShareProfilDTO>> searchShareProfils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ShareProfils for query {}", query);
        Page<ShareProfilDTO> page = shareProfilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/share-profils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
