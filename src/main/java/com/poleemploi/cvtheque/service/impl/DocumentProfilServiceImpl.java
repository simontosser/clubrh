package com.poleemploi.cvtheque.service.impl;

import com.poleemploi.cvtheque.service.DocumentProfilService;
import com.poleemploi.cvtheque.domain.DocumentProfil;
import com.poleemploi.cvtheque.repository.DocumentProfilRepository;
import com.poleemploi.cvtheque.repository.search.DocumentProfilSearchRepository;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;
import com.poleemploi.cvtheque.service.mapper.DocumentProfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentProfil.
 */
@Service
@Transactional
public class DocumentProfilServiceImpl implements DocumentProfilService {

    private final Logger log = LoggerFactory.getLogger(DocumentProfilServiceImpl.class);

    private final DocumentProfilRepository documentProfilRepository;

    private final DocumentProfilMapper documentProfilMapper;

    private final DocumentProfilSearchRepository documentProfilSearchRepository;

    public DocumentProfilServiceImpl(DocumentProfilRepository documentProfilRepository, DocumentProfilMapper documentProfilMapper, DocumentProfilSearchRepository documentProfilSearchRepository) {
        this.documentProfilRepository = documentProfilRepository;
        this.documentProfilMapper = documentProfilMapper;
        this.documentProfilSearchRepository = documentProfilSearchRepository;
    }

    /**
     * Save a documentProfil.
     *
     * @param documentProfilDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentProfilDTO save(DocumentProfilDTO documentProfilDTO) {
        log.debug("Request to save DocumentProfil : {}", documentProfilDTO);
        DocumentProfil documentProfil = documentProfilMapper.toEntity(documentProfilDTO);
        documentProfil = documentProfilRepository.save(documentProfil);
        DocumentProfilDTO result = documentProfilMapper.toDto(documentProfil);
        documentProfilSearchRepository.save(documentProfil);
        return result;
    }

    /**
     * Get all the documentProfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentProfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentProfils");
        return documentProfilRepository.findAll(pageable)
            .map(documentProfilMapper::toDto);
    }

    /**
     * Get one documentProfil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DocumentProfilDTO findOne(Long id) {
        log.debug("Request to get DocumentProfil : {}", id);
        DocumentProfil documentProfil = documentProfilRepository.findOne(id);
        return documentProfilMapper.toDto(documentProfil);
    }

    /**
     * Delete the documentProfil by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentProfil : {}", id);
        documentProfilRepository.delete(id);
        documentProfilSearchRepository.delete(id);
    }

    /**
     * Search for the documentProfil corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentProfilDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentProfils for query {}", query);
        Page<DocumentProfil> result = documentProfilSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(documentProfilMapper::toDto);
    }
}
