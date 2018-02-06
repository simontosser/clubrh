package com.poleemploi.cvtheque.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.poleemploi.cvtheque.service.RecrutementProfilService;
import com.poleemploi.cvtheque.web.rest.errors.BadRequestAlertException;
import com.poleemploi.cvtheque.web.rest.util.HeaderUtil;
import com.poleemploi.cvtheque.web.rest.util.PaginationUtil;
import com.poleemploi.cvtheque.service.dto.RecrutementProfilDTO;
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
 * REST controller for managing RecrutementProfil.
 */
@RestController
@RequestMapping("/api")
public class RecrutementProfilResource {

    private final Logger log = LoggerFactory.getLogger(RecrutementProfilResource.class);

    private static final String ENTITY_NAME = "recrutementProfil";

    private final RecrutementProfilService recrutementProfilService;

    public RecrutementProfilResource(RecrutementProfilService recrutementProfilService) {
        this.recrutementProfilService = recrutementProfilService;
    }

    /**
     * POST  /recrutement-profils : Create a new recrutementProfil.
     *
     * @param recrutementProfilDTO the recrutementProfilDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recrutementProfilDTO, or with status 400 (Bad Request) if the recrutementProfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recrutement-profils")
    @Timed
    public ResponseEntity<RecrutementProfilDTO> createRecrutementProfil(@Valid @RequestBody RecrutementProfilDTO recrutementProfilDTO) throws URISyntaxException {
        log.debug("REST request to save RecrutementProfil : {}", recrutementProfilDTO);
        if (recrutementProfilDTO.getId() != null) {
            throw new BadRequestAlertException("A new recrutementProfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecrutementProfilDTO result = recrutementProfilService.save(recrutementProfilDTO);
        return ResponseEntity.created(new URI("/api/recrutement-profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recrutement-profils : Updates an existing recrutementProfil.
     *
     * @param recrutementProfilDTO the recrutementProfilDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recrutementProfilDTO,
     * or with status 400 (Bad Request) if the recrutementProfilDTO is not valid,
     * or with status 500 (Internal Server Error) if the recrutementProfilDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recrutement-profils")
    @Timed
    public ResponseEntity<RecrutementProfilDTO> updateRecrutementProfil(@Valid @RequestBody RecrutementProfilDTO recrutementProfilDTO) throws URISyntaxException {
        log.debug("REST request to update RecrutementProfil : {}", recrutementProfilDTO);
        if (recrutementProfilDTO.getId() == null) {
            return createRecrutementProfil(recrutementProfilDTO);
        }
        RecrutementProfilDTO result = recrutementProfilService.save(recrutementProfilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recrutementProfilDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recrutement-profils : get all the recrutementProfils.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recrutementProfils in body
     */
    @GetMapping("/recrutement-profils")
    @Timed
    public ResponseEntity<List<RecrutementProfilDTO>> getAllRecrutementProfils(Pageable pageable) {
        log.debug("REST request to get a page of RecrutementProfils");
        Page<RecrutementProfilDTO> page = recrutementProfilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recrutement-profils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /recrutement-profils/current : get all the recrutementProfils with current user.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recrutementProfils in body
     */
    @GetMapping("/recrutement-profils/current")
    @Timed
    public ResponseEntity<List<RecrutementProfilDTO>> getAllRecrutementProfilsWithCurrentUser(Pageable pageable) {
        log.debug("REST request to get a page of RecrutementProfils with current user");
        Page<RecrutementProfilDTO> page = recrutementProfilService.findAllWithCurrentUserCompany(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recrutement-profils/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recrutement-profils/:id : get the "id" recrutementProfil.
     *
     * @param id the id of the recrutementProfilDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recrutementProfilDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recrutement-profils/{id}")
    @Timed
    public ResponseEntity<RecrutementProfilDTO> getRecrutementProfil(@PathVariable Long id) {
        log.debug("REST request to get RecrutementProfil : {}", id);
        RecrutementProfilDTO recrutementProfilDTO = recrutementProfilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recrutementProfilDTO));
    }

    /**
     * DELETE  /recrutement-profils/:id : delete the "id" recrutementProfil.
     *
     * @param id the id of the recrutementProfilDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recrutement-profils/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecrutementProfil(@PathVariable Long id) {
        log.debug("REST request to delete RecrutementProfil : {}", id);
        recrutementProfilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recrutement-profils?query=:query : search for the recrutementProfil corresponding
     * to the query.
     *
     * @param query the query of the recrutementProfil search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recrutement-profils")
    @Timed
    public ResponseEntity<List<RecrutementProfilDTO>> searchRecrutementProfils(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RecrutementProfils for query {}", query);
        Page<RecrutementProfilDTO> page = recrutementProfilService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recrutement-profils");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
