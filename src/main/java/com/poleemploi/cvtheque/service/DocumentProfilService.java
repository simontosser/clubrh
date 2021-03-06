package com.poleemploi.cvtheque.service;

import com.poleemploi.cvtheque.domain.DocumentProfil;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DocumentProfil.
 */
public interface DocumentProfilService {

    /**
     * Save a documentProfil.
     *
     * @param documentProfilDTO the entity to save
     * @return the persisted entity
     */
    DocumentProfilDTO save(DocumentProfilDTO documentProfilDTO);
    
    DocumentProfilDTO save(DocumentProfil documentProfil);
    
    /**
     * Save a list documentProfil.
     *
     * @param documentProfils the list of entities to save
     * @return the persisted list entities
     */
    Set<DocumentProfilDTO> save(Set<DocumentProfilDTO> documentProfils);

    /**
     * Get all the documentProfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentProfilDTO> findAll(Pageable pageable);

    /**
     * Get the "id" documentProfil.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DocumentProfilDTO findOne(Long id);

    /**
     * Delete the "id" documentProfil.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the documentProfil corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentProfilDTO> search(String query, Pageable pageable);

    Set<DocumentProfil> diffPersistence(Set<DocumentProfil> temp, Set<DocumentProfil> persistence);


}
