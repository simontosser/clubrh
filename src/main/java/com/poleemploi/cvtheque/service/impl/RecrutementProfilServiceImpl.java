package com.poleemploi.cvtheque.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poleemploi.cvtheque.domain.Company;
import com.poleemploi.cvtheque.domain.DocumentProfil;
import com.poleemploi.cvtheque.domain.RecrutementProfil;
import com.poleemploi.cvtheque.domain.User;
import com.poleemploi.cvtheque.repository.AuthorityRepository;
import com.poleemploi.cvtheque.repository.RecrutementProfilRepository;
import com.poleemploi.cvtheque.repository.UserRepository;
import com.poleemploi.cvtheque.repository.search.RecrutementProfilSearchRepository;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import com.poleemploi.cvtheque.security.SecurityUtils;
import com.poleemploi.cvtheque.service.DocumentProfilService;
import com.poleemploi.cvtheque.service.MailService;
import com.poleemploi.cvtheque.service.RecrutementProfilService;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;
import com.poleemploi.cvtheque.service.dto.RecrutementProfilDTO;
import com.poleemploi.cvtheque.service.mapper.RecrutementProfilMapper;
import com.poleemploi.cvtheque.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing RecrutementProfil.
 */
@Service
@Transactional
public class RecrutementProfilServiceImpl implements RecrutementProfilService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(RecrutementProfilServiceImpl.class);

    /** The recrutement profil repository. */
    private final RecrutementProfilRepository recrutementProfilRepository;

    /** The recrutement profil mapper. */
    private final RecrutementProfilMapper recrutementProfilMapper;

    /** The recrutement profil search repository. */
    private final RecrutementProfilSearchRepository recrutementProfilSearchRepository;
    
    /** The document profil service. */
    private final DocumentProfilService documentProfilService;

    /** The user repository. */
    private final UserRepository userRepository;

    /** The authority repository. */
    private final AuthorityRepository authorityRepository;
    
    /** The mail service. */
    private final MailService mailService;

    /**
     * Instantiates a new recrutement profil service impl.
     *
     * @param recrutementProfilRepository the recrutement profil repository
     * @param recrutementProfilMapper the recrutement profil mapper
     * @param recrutementProfilSearchRepository the recrutement profil search repository
     * @param userRepository the user repository
     * @param authorityRepository the authority repository
     * @param documentProfilService the document profil service
     * @param mailService the mail service
     */
    public RecrutementProfilServiceImpl(RecrutementProfilRepository recrutementProfilRepository, RecrutementProfilMapper recrutementProfilMapper, RecrutementProfilSearchRepository recrutementProfilSearchRepository, UserRepository userRepository, AuthorityRepository authorityRepository, DocumentProfilService documentProfilService, MailService mailService) {
        this.recrutementProfilRepository = recrutementProfilRepository;
        this.recrutementProfilMapper = recrutementProfilMapper;
        this.recrutementProfilSearchRepository = recrutementProfilSearchRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.documentProfilService = documentProfilService;
        this.mailService = mailService;
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

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
				&& SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {

			Long companyId = SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin)
					.map(User::getCompany)
					.map(Company::getId)
					.orElseThrow(() -> new BadRequestAlertException("You can not create a profile since you are not being reattached to a company", "recrutementProfil", "forbidden.notcompany"));

			recrutementProfilDTO.setCompanyId(companyId);
		}

        Set<DocumentProfilDTO> temps = recrutementProfilDTO.getDocumentProfils();
		recrutementProfilDTO.setDocumentProfils(null);

        RecrutementProfil recrutementProfil = recrutementProfilMapper.toEntity(recrutementProfilDTO);
        recrutementProfil = recrutementProfilRepository.save(recrutementProfil);

        for (DocumentProfilDTO documentProfilDTO : temps) {
        	documentProfilDTO.setRecrutementProfilId(recrutementProfil.getId());
        	documentProfilService.save(documentProfilDTO);
		}

        RecrutementProfilDTO result = this.findOne(recrutementProfil.getId());
        recrutementProfil = recrutementProfilMapper.toEntity(result);
        recrutementProfilSearchRepository.save(recrutementProfil);
        
      //Sending e-mail to inform share profil creation
        List<String> authorities = new ArrayList<String>();
        authorities.add("ROLE_USER");
        
        List<User> users = userRepository.findAllBySendNotification();
        
        users.stream().forEach(user -> mailService.sendRecrutementProfilCreationEmail(user, result));

        return result;
    }

    /**
     * Update a recrutementProfil.
     *
     * @param latestDTO the latest DTO
     * @return the persisted entity
     */
    @Override
    public RecrutementProfilDTO update(RecrutementProfilDTO latestDTO) {
        log.debug("Request to update RecrutementProfil : {}", latestDTO);

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER) &&
            !this.isCurrentUserProfil(latestDTO)) {
        	throw new BadRequestAlertException("You are not allowed to perform this action", "recrutementProfil", "forbidden.action");
        }

        RecrutementProfil latest = recrutementProfilMapper.toEntity(latestDTO);
        
        RecrutementProfilDTO persistenceDTO = this.findOne(latest.getId());       
        final RecrutementProfil persistence = recrutementProfilMapper.toEntity(persistenceDTO);
        
        Set<DocumentProfil> latestDoc = documentProfilService.diffPersistence(latest.getDocumentProfils(), persistence.getDocumentProfils());
        latestDoc.stream()
        	.filter(i -> i.getId() == null)
        	.forEach(i -> {
        		i.setRecrutementProfil(persistence);
        		documentProfilService.save(i);
        	});

        RecrutementProfilDTO resultDTO = this.findOne(latest.getId());
        RecrutementProfil result = recrutementProfilMapper.toEntity(latestDTO);       
        result.setDocumentProfils(null);
        result = recrutementProfilRepository.save(result);      
        resultDTO = recrutementProfilMapper.toDto(result);
        recrutementProfilSearchRepository.save(result);
        
        return resultDTO;
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
        return recrutementProfilRepository.findAll(pageable)
            .map(recrutementProfilMapper::toDto);
    }

    /**
     * Get all the recrutementProfil for a specific user company.
     *
     * @param pageable the pagination information
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

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN) &&
            SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER) &&
            !this.isCurrentUserProfil(id)) {
            	throw new BadRequestAlertException("You are not allowed to perform this action", "recrutementProfil", "forbidden.action");
            }
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

    /**
     * Is this the profile of the current user.
     *
     * @param profil the profil
     * @return boolean
     */
    public boolean isCurrentUserProfil(RecrutementProfilDTO profil) {
    	return SecurityUtils.getCurrentUserLogin()
        		.flatMap(userRepository::findOneByLogin)
        		.map(User::getCompany)
        		.filter(c -> c.getId().equals(profil.getCompanyId()))
        		.isPresent();
    }

    /**
     * Is this the profile of the current user.
     *
     * @param id the id
     * @return boolean
     */
    public boolean isCurrentUserProfil(Long id) {

    	RecrutementProfilDTO profil = this.findOne(id);

    	return SecurityUtils.getCurrentUserLogin()
        		.flatMap(userRepository::findOneByLogin)
        		.map(User::getCompany)
        		.filter(c -> c.getId().equals(profil.getCompanyId()))
        		.isPresent();
    }

    /**
     * Update document profil.
     *
     * @param recrutementProfilDTO the recrutement profil DTO
     * @return the recrutement profil
     */
    public RecrutementProfil updateDocumentProfil(RecrutementProfilDTO recrutementProfilDTO) {

    	RecrutementProfil recrutementProfil = recrutementProfilRepository.findOne(recrutementProfilDTO.getId());
    	if (recrutementProfil.getDocumentProfils() != null) {
	    	for (DocumentProfil document : recrutementProfil.getDocumentProfils()) {
				documentProfilService.delete(document.getId());
			}
    	}
    	for (DocumentProfilDTO document : recrutementProfilDTO.getDocumentProfils()) {
    		document.setId(null);
    		document.setRecrutementProfilId(recrutementProfilDTO.getId());
			documentProfilService.save(document);
		}

    	return recrutementProfilRepository.findOne(recrutementProfilDTO.getId());
    }
}
