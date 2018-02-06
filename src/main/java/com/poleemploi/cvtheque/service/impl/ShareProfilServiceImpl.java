package com.poleemploi.cvtheque.service.impl;

import com.poleemploi.cvtheque.service.ShareProfilService;
import com.poleemploi.cvtheque.domain.Company;
import com.poleemploi.cvtheque.domain.ShareProfil;
import com.poleemploi.cvtheque.domain.User;
import com.poleemploi.cvtheque.repository.AuthorityRepository;
import com.poleemploi.cvtheque.repository.ShareProfilRepository;
import com.poleemploi.cvtheque.repository.search.ShareProfilSearchRepository;
import com.poleemploi.cvtheque.repository.UserRepository;
import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;
import com.poleemploi.cvtheque.service.mapper.ShareProfilMapper;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import com.poleemploi.cvtheque.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import java.util.function.Function;

/**
 * Service Implementation for managing ShareProfil.
 */
@Service
@Transactional
public class ShareProfilServiceImpl implements ShareProfilService {

    private final Logger log = LoggerFactory.getLogger(ShareProfilServiceImpl.class);

    private final ShareProfilRepository shareProfilRepository;

    private final ShareProfilMapper shareProfilMapper;

    private final ShareProfilSearchRepository shareProfilSearchRepository;

    private final UserRepository userRepository;
    
    private final AuthorityRepository authorityRepository;

    public ShareProfilServiceImpl(ShareProfilRepository shareProfilRepository, ShareProfilMapper shareProfilMapper, ShareProfilSearchRepository shareProfilSearchRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.shareProfilRepository = shareProfilRepository;
        this.shareProfilMapper = shareProfilMapper;
        this.shareProfilSearchRepository = shareProfilSearchRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a shareProfil.
     *
     * @param shareProfilDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShareProfilDTO save(ShareProfilDTO shareProfilDTO) {
        log.debug("Request to save ShareProfil : {}", shareProfilDTO);
        ShareProfil shareProfil = shareProfilMapper.toEntity(shareProfilDTO);
        shareProfil = shareProfilRepository.save(shareProfil);
        ShareProfilDTO result = shareProfilMapper.toDto(shareProfil);
        shareProfilSearchRepository.save(shareProfil);
        return result;
    }

    /**
     * Get all the shareProfils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShareProfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShareProfils");
        return SecurityUtils.getCurrentUserLogin()
        .flatMap(userRepository::findOneByLogin)
        .filter(u -> u.getAuthorities().contains(authorityRepository.findOne(AuthoritiesConstants.ADMIN)))
        .map(t -> shareProfilRepository.findAll(pageable).map(shareProfilMapper::toDto))
        .orElseGet(() -> this.findAllWithCurrentUserCompanyNot(pageable));
    }

    /**
     * Get all the shareProfils for a specific user company.
     *
     * @param pageable the pagination information
     * @return 
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShareProfilDTO> findAllWithCurrentUserCompany(Pageable pageable) {
        log.debug("Request to get all ShareProfils for a specific user company");
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .filter(a -> a.getCompany() != null)
            .map(User::getCompany)
            .map(company -> shareProfilRepository.findAllByCompany(pageable, company).map(shareProfilMapper::toDto))
            .orElse(null);       
    }
    
    /**
     * Get all the shareProfils for a specific user company.
     *
     * @param pageable the pagination information
     * @return 
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShareProfilDTO> findAllWithCurrentUserCompanyNot(Pageable pageable) {
        log.debug("Request to get all ShareProfils for a specific user company");
        return SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .filter(a -> a.getCompany() != null)
            .map(User::getCompany)
            .map(company -> shareProfilRepository.findAllByCompanyNot(pageable, company).map(shareProfilMapper::toDto))
            .orElse(null);       
    }

    /**
     * Get one shareProfil by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ShareProfilDTO findOne(Long id) {
        log.debug("Request to get ShareProfil : {}", id);
        ShareProfil shareProfil = shareProfilRepository.findOne(id);
        return shareProfilMapper.toDto(shareProfil);
    }

    /**
     * Delete the shareProfil by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShareProfil : {}", id);
        shareProfilRepository.delete(id);
        shareProfilSearchRepository.delete(id);
    }

    /**
     * Search for the shareProfil corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ShareProfilDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ShareProfils for query {}", query);
        Page<ShareProfil> result = shareProfilSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(shareProfilMapper::toDto);
    }
}
