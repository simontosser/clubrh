package com.poleemploi.cvtheque.service;

import com.poleemploi.cvtheque.service.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save
     * @return the persisted entity
     */
    AddressDTO save(AddressDTO addressDTO);

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AddressDTO> findAll(Pageable pageable);
    /**
     * Get all the AddressDTO where Company is null.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAllWhereCompanyIsNull();

    /**
     * Get the "id" address.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AddressDTO findOne(Long id);

    /**
     * Delete the "id" address.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the address corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AddressDTO> search(String query, Pageable pageable);
}
