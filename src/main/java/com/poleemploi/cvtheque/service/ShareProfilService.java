package com.poleemploi.cvtheque.service;

import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShareProfil.
 */
public interface ShareProfilService {

    /**
     * Save a shareProfil.
     *
     * @param shareProfilDTO the entity to save
     * @return the persisted entity
     */
    ShareProfilDTO save(ShareProfilDTO shareProfilDTO);

    /**
     * Get all the shareProfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShareProfilDTO> findAll(Pageable pageable);
    
    /**
     * Get all the shareProfils with current user company.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
	Page<ShareProfilDTO> findAllWithCurrentUserCompany(Pageable pageable);
	
	/**
     * Get all the shareProfils with not current user company.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
	Page<ShareProfilDTO> findAllWithCurrentUserCompanyNot(Pageable pageable);

    /**
     * Get the "id" shareProfil.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShareProfilDTO findOne(Long id);

    /**
     * Delete the "id" shareProfil.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the shareProfil corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShareProfilDTO> search(String query, Pageable pageable);

}
