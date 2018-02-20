package com.poleemploi.cvtheque.service.impl;

import com.poleemploi.cvtheque.service.DocumentProfilService;
import com.poleemploi.cvtheque.service.ShareProfilService;
import com.poleemploi.cvtheque.domain.Company;
import com.poleemploi.cvtheque.domain.DocumentProfil;
import com.poleemploi.cvtheque.domain.ShareProfil;
import com.poleemploi.cvtheque.domain.User;
import com.poleemploi.cvtheque.repository.AuthorityRepository;
import com.poleemploi.cvtheque.repository.ShareProfilRepository;
import com.poleemploi.cvtheque.repository.search.ShareProfilSearchRepository;
import com.poleemploi.cvtheque.repository.UserRepository;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;
import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;
import com.poleemploi.cvtheque.service.mapper.DocumentProfilMapper;
import com.poleemploi.cvtheque.service.mapper.ShareProfilMapper;
import com.poleemploi.cvtheque.web.rest.errors.BadRequestAlertException;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import com.poleemploi.cvtheque.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.io.Console;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final DocumentProfilService documentProfilService;

    private final DocumentProfilMapper documentProfilMapper;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    public ShareProfilServiceImpl(ShareProfilRepository shareProfilRepository, ShareProfilMapper shareProfilMapper, ShareProfilSearchRepository shareProfilSearchRepository, UserRepository userRepository, AuthorityRepository authorityRepository, DocumentProfilService documentProfilService, DocumentProfilMapper documentProfilMapper) {
        this.shareProfilRepository = shareProfilRepository;
        this.shareProfilMapper = shareProfilMapper;
        this.shareProfilSearchRepository = shareProfilSearchRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.documentProfilService = documentProfilService;
        this.documentProfilMapper = documentProfilMapper;
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

		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
				&& SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {

			Long companyId = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin)
					.map(User::getCompany)
					.map(Company::getId)
					.orElseThrow(() -> new BadRequestAlertException("You can not create a profile since you are not being reattached to a company", "shareProfil", "forbidden.notcompany"));

			shareProfilDTO.setCompanyId(companyId);
		}

		Set<DocumentProfilDTO> temps = shareProfilDTO.getDocumentProfils();
		shareProfilDTO.setDocumentProfils(null);

        ShareProfil shareProfil = shareProfilMapper.toEntity(shareProfilDTO);
        shareProfil = shareProfilRepository.save(shareProfil);

        for (DocumentProfilDTO documentProfilDTO : temps) {
        	documentProfilDTO.setShareProfilId(shareProfil.getId());
        	documentProfilService.save(documentProfilDTO);
		}

        ShareProfilDTO result = this.findOne(shareProfil.getId());
        shareProfil = shareProfilMapper.toEntity(result);
        shareProfilSearchRepository.save(shareProfil);

        return result;
    }

    /**
     * Update a shareProfil.
     *
     * @param shareProfilDTO the entity to update
     * @return the persisted entity
     */
    @Override
    public ShareProfilDTO update(ShareProfilDTO latestDTO) {
        log.debug("Request to update ShareProfil : {}", latestDTO);

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
        	SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER) &&
        	!this.isCurrentUserProfil(latestDTO)) {
        	throw new BadRequestAlertException("You are not allowed to perform this action", "shareProfil", "forbidden.action");
        }
        
        ShareProfil latest = shareProfilMapper.toEntity(latestDTO);
        
        ShareProfilDTO persistenceDTO = this.findOne(latest.getId());       
        final ShareProfil persistence = shareProfilMapper.toEntity(persistenceDTO);
        
        Set<DocumentProfil> latestDoc = documentProfilService.diffPersistence(latest.getDocumentProfils(), persistence.getDocumentProfils());
        latestDoc.stream()
        	.filter(i -> i.getId() == null)
        	.forEach(i -> {
        		i.setShareProfil(persistence);
        		documentProfilService.save(i);
        	});

        ShareProfilDTO resultDTO = this.findOne(latest.getId());
        ShareProfil result = shareProfilMapper.toEntity(latestDTO);       
        result.setDocumentProfils(null);
        result = shareProfilRepository.save(result);      
        resultDTO = shareProfilMapper.toDto(result);
        shareProfilSearchRepository.save(result);
        
        return resultDTO;
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
        return shareProfilRepository.findAll(pageable)
            .map(shareProfilMapper::toDto);
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
            .map(company -> shareProfilRepository.findAllByCompany(pageable, company)
            .map(shareProfilMapper::toDto))
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

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER) &&
            !this.isCurrentUserProfil(id)) {
            	throw new BadRequestAlertException("You are not allowed to perform this action", "shareProfil", "forbidden.action");
        }
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

    /**
     * Is this the profile of the current user.
     *
     * @param shareProfilDTO the entity
     * @return boolean
     */
    public boolean isCurrentUserProfil(ShareProfilDTO profil) {

    	return SecurityUtils.getCurrentUserLogin()
        		.flatMap(userRepository::findOneByLogin)
        		.map(User::getCompany)
        		.filter(c -> c.getId().equals(profil.getCompanyId()))
        		.isPresent();
    }

    /**
     * Is this the profile of the current user.
     *
     * @param Long id the entity
     * @return boolean
     */
    public boolean isCurrentUserProfil(Long id) {

    	ShareProfilDTO profil = this.findOne(id);

    	return SecurityUtils.getCurrentUserLogin()
        		.flatMap(userRepository::findOneByLogin)
        		.map(User::getCompany)
        		.filter(c -> c.getId().equals(profil.getCompanyId()))
        		.isPresent();
    }

}
