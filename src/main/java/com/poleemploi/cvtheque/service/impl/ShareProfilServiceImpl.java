package com.poleemploi.cvtheque.service.impl;

import com.poleemploi.cvtheque.service.ShareProfilService;
import com.poleemploi.cvtheque.domain.ShareProfil;
import com.poleemploi.cvtheque.repository.ShareProfilRepository;
import com.poleemploi.cvtheque.repository.search.ShareProfilSearchRepository;
import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;
import com.poleemploi.cvtheque.service.mapper.ShareProfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

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

    public ShareProfilServiceImpl(ShareProfilRepository shareProfilRepository, ShareProfilMapper shareProfilMapper, ShareProfilSearchRepository shareProfilSearchRepository) {
        this.shareProfilRepository = shareProfilRepository;
        this.shareProfilMapper = shareProfilMapper;
        this.shareProfilSearchRepository = shareProfilSearchRepository;
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
        return shareProfilRepository.findAll(pageable)
            .map(shareProfilMapper::toDto);
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
