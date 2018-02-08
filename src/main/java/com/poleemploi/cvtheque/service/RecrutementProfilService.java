package com.poleemploi.cvtheque.service;

import com.poleemploi.cvtheque.service.dto.RecrutementProfilDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing RecrutementProfil.
 */
public interface RecrutementProfilService {

    /**
     * Save a recrutementProfil.
     *
     * @param recrutementProfilDTO the entity to save
     * @return the persisted entity
     */
    RecrutementProfilDTO save(RecrutementProfilDTO recrutementProfilDTO);
    
    /**
     * Update a recrutementProfil.
     *
     * @param recrutementProfilDTO the entity to update
     * @return the persisted entity
     */
    RecrutementProfilDTO update(RecrutementProfilDTO recrutementProfilDTO);
    
    /**
     * Get all the recrutementProfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecrutementProfilDTO> findAll(Pageable pageable);
    
    /**
     * Get all the recrutementProfils with current user company.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
	Page<RecrutementProfilDTO> findAllWithCurrentUserCompany(Pageable pageable);
	
	/**
     * Get all the recrutementProfils with not current user company.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
	Page<RecrutementProfilDTO> findAllWithCurrentUserCompanyNot(Pageable pageable);
	
    /**
     * Get the "id" recrutementProfil.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RecrutementProfilDTO findOne(Long id);

    /**
     * Delete the "id" recrutementProfil.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the recrutementProfil corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecrutementProfilDTO> search(String query, Pageable pageable);
}
