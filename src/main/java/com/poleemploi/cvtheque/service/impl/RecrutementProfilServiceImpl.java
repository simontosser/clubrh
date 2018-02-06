package com.poleemploi.cvtheque.service.impl;

import com.poleemploi.cvtheque.service.RecrutementProfilService;
import com.poleemploi.cvtheque.domain.RecrutementProfil;
import com.poleemploi.cvtheque.domain.User;
import com.poleemploi.cvtheque.repository.AuthorityRepository;
import com.poleemploi.cvtheque.repository.RecrutementProfilRepository;
import com.poleemploi.cvtheque.repository.UserRepository;
import com.poleemploi.cvtheque.repository.search.RecrutementProfilSearchRepository;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import com.poleemploi.cvtheque.security.SecurityUtils;
import com.poleemploi.cvtheque.service.dto.RecrutementProfilDTO;
import com.poleemploi.cvtheque.service.mapper.RecrutementProfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RecrutementProfil.
 */
@Service
@Transactional
public class RecrutementProfilServiceImpl implements RecrutementProfilService {

    private final Logger log = LoggerFactory.getLogger(RecrutementProfilServiceImpl.class);

    private final RecrutementProfilRepository recrutementProfilRepository;

    private final RecrutementProfilMapper recrutementProfilMapper;

    private final RecrutementProfilSearchRepository recrutementProfilSearchRepository;
    
    private final UserRepository userRepository;
    
    private final AuthorityRepository authorityRepository;

    public RecrutementProfilServiceImpl(RecrutementProfilRepository recrutementProfilRepository, RecrutementProfilMapper recrutementProfilMapper, RecrutementProfilSearchRepository recrutementProfilSearchRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.recrutementProfilRepository = recrutementProfilRepository;
        this.recrutementProfilMapper = recrutementProfilMapper;
        this.recrutementProfilSearchRepository = recrutementProfilSearchRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a recrutementProfil.
     *
     * @param recrutementProfilDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecrutementProfilDTO save(RecrutementProfilDTO recrutementProfilDTO) {
        log.debug("Request to save RecrutementProfil : {}", recrutementProfilDTO);
        RecrutementProfil recrutementProfil = recrutementProfilMapper.toEntity(recrutementProfilDTO);
        recrutementProfil = recrutementProfilRepository.save(recrutementProfil);
        RecrutementProfilDTO result = recrutementProfilMapper.toDto(recrutementProfil);
        recrutementProfilSearchRepository.save(recrutementProfil);
        return result;
    }

    /**
     * Get all the recrutementProfil.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecrutementProfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecrutementProfil");
        return SecurityUtils.getCurrentUserLogin()
        .flatMap(userRepository::findOneByLogin)
        .filter(u -> u.getAuthorities().contains(authorityRepository.findOne(AuthoritiesConstants.ADMIN)))
        .map(t -> recrutementProfilRepository.findAll(pageable).map(recrutementProfilMapper::toDto))
        .orElseGet(() -> this.findAllWithCurrentUserCompanyNot(pageable));
    }

    /**
     * Get all the recrutementProfil for a specific user company.
     *
     * @param pageable the pagination information
     * @return 
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecrutementProfilDTO> findAllWithCurrentUserCompany(Pageable pageable) {
        log.debug("Request to get all RecrutementProfil for a specific user company");
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .filter(a -> a.getCompany() != null)
            .map(User::getCompany)
            .map(company -> recrutementProfilRepository.findAllByCompany(pageable, company).map(recrutementProfilMapper::toDto))
            .orElse(null);       
    }
    
    /**
     * Get all the RecrutementProfil for a specific user company.
     *
     * @param pageable the pagination information
     * @return 
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecrutementProfilDTO> findAllWithCurrentUserCompanyNot(Pageable pageable) {
        log.debug("Request to get all RecrutementProfil for a specific user company");
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .filter(a -> a.getCompany() != null)
            .map(User::getCompany)
            .map(company -> recrutementProfilRepository.findAllByCompanyNot(pageable, company).map(recrutementProfilMapper::toDto))
            .orElse(null);       
    }

    /**
     * Get one recrutementProfil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RecrutementProfilDTO findOne(Long id) {
        log.debug("Request to get RecrutementProfil : {}", id);
        RecrutementProfil recrutementProfil = recrutementProfilRepository.findOne(id);
        return recrutementProfilMapper.toDto(recrutementProfil);
    }

    /**
     * Delete the recrutementProfil by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecrutementProfil : {}", id);
        recrutementProfilRepository.delete(id);
        recrutementProfilSearchRepository.delete(id);
    }

    /**
     * Search for the recrutementProfil corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecrutementProfilDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RecrutementProfils for query {}", query);
        Page<RecrutementProfil> result = recrutementProfilSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(recrutementProfilMapper::toDto);
    }
}
