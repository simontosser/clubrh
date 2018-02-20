package com.poleemploi.cvtheque.web.rest;

import com.poleemploi.cvtheque.CvthequeApp;

import com.poleemploi.cvtheque.domain.ShareProfil;
import com.poleemploi.cvtheque.repository.ShareProfilRepository;
import com.poleemploi.cvtheque.service.ShareProfilService;
import com.poleemploi.cvtheque.repository.search.ShareProfilSearchRepository;
import com.poleemploi.cvtheque.security.AuthoritiesConstants;
import com.poleemploi.cvtheque.service.dto.ShareProfilDTO;
import com.poleemploi.cvtheque.service.mapper.ShareProfilMapper;
import com.poleemploi.cvtheque.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.poleemploi.cvtheque.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShareProfilResource REST controller.
 *
 * @see ShareProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CvthequeApp.class)
public class ShareProfilResourceIntTest {

    private static final String DEFAULT_TARGET_JOB = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_SKILL_SHIELD = "AAAAAAAAAA";
    private static final String UPDATED_SKILL_SHIELD = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_CARRY_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_CARRY_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_SKILL_EXPENDS = "AAAAAAAAAA";
    private static final String UPDATED_SKILL_EXPENDS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_COLLABO_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COLLABO_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONSIDERATION = "AAAAAAAAAA";
    private static final String UPDATED_CONSIDERATION = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private ShareProfilRepository shareProfilRepository;

    @Autowired
    private ShareProfilMapper shareProfilMapper;

    @Autowired
    private ShareProfilService shareProfilService;

    @Autowired
    private ShareProfilSearchRepository shareProfilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShareProfilMockMvc;

    private ShareProfil shareProfil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShareProfilResource shareProfilResource = new ShareProfilResource(shareProfilService);
        this.restShareProfilMockMvc = MockMvcBuilders.standaloneSetup(shareProfilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
        
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin", authorities));
        SecurityContextHolder.setContext(securityContext);
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShareProfil createEntity(EntityManager em) {
        ShareProfil shareProfil = new ShareProfil()
            .targetJob(DEFAULT_TARGET_JOB)
            .skillShield(DEFAULT_SKILL_SHIELD)
            .mainActivity(DEFAULT_MAIN_ACTIVITY)
            .carryActivity(DEFAULT_CARRY_ACTIVITY)
            .skillExpends(DEFAULT_SKILL_EXPENDS)
            .context(DEFAULT_CONTEXT)
            .collaboType(DEFAULT_COLLABO_TYPE)
            .consideration(DEFAULT_CONSIDERATION)
            .comment(DEFAULT_COMMENT);
        return shareProfil;
    }

    @Before
    public void initTest() {
        shareProfilSearchRepository.deleteAll();
        shareProfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createShareProfil() throws Exception {
        int databaseSizeBeforeCreate = shareProfilRepository.findAll().size();

        // Create the ShareProfil
        ShareProfilDTO shareProfilDTO = shareProfilMapper.toDto(shareProfil);
        restShareProfilMockMvc.perform(post("/api/share-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareProfilDTO)))
            .andExpect(status().isCreated());

        // Validate the ShareProfil in the database
        List<ShareProfil> shareProfilList = shareProfilRepository.findAll();
        assertThat(shareProfilList).hasSize(databaseSizeBeforeCreate + 1);
        ShareProfil testShareProfil = shareProfilList.get(shareProfilList.size() - 1);
        assertThat(testShareProfil.getTargetJob()).isEqualTo(DEFAULT_TARGET_JOB);
        assertThat(testShareProfil.getSkillShield()).isEqualTo(DEFAULT_SKILL_SHIELD);
        assertThat(testShareProfil.getMainActivity()).isEqualTo(DEFAULT_MAIN_ACTIVITY);
        assertThat(testShareProfil.getCarryActivity()).isEqualTo(DEFAULT_CARRY_ACTIVITY);
        assertThat(testShareProfil.getSkillExpends()).isEqualTo(DEFAULT_SKILL_EXPENDS);
        assertThat(testShareProfil.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testShareProfil.getCollaboType()).isEqualTo(DEFAULT_COLLABO_TYPE);
        assertThat(testShareProfil.getConsideration()).isEqualTo(DEFAULT_CONSIDERATION);
        assertThat(testShareProfil.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the ShareProfil in Elasticsearch
        ShareProfil shareProfilEs = shareProfilSearchRepository.findOne(testShareProfil.getId());
        assertThat(shareProfilEs).isEqualToIgnoringGivenFields(testShareProfil);
    }

    @Test
    @Transactional
    public void createShareProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shareProfilRepository.findAll().size();

        // Create the ShareProfil with an existing ID
        shareProfil.setId(1L);
        ShareProfilDTO shareProfilDTO = shareProfilMapper.toDto(shareProfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShareProfilMockMvc.perform(post("/api/share-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareProfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShareProfil in the database
        List<ShareProfil> shareProfilList = shareProfilRepository.findAll();
        assertThat(shareProfilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTargetJobIsRequired() throws Exception {
        int databaseSizeBeforeTest = shareProfilRepository.findAll().size();
        // set the field null
        shareProfil.setTargetJob(null);

        // Create the ShareProfil, which fails.
        ShareProfilDTO shareProfilDTO = shareProfilMapper.toDto(shareProfil);

        restShareProfilMockMvc.perform(post("/api/share-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareProfilDTO)))
            .andExpect(status().isBadRequest());

        List<ShareProfil> shareProfilList = shareProfilRepository.findAll();
        assertThat(shareProfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShareProfils() throws Exception {
        // Initialize the database
        shareProfilRepository.saveAndFlush(shareProfil);

        // Get all the shareProfilList
        restShareProfilMockMvc.perform(get("/api/share-profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shareProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetJob").value(hasItem(DEFAULT_TARGET_JOB.toString())))
            .andExpect(jsonPath("$.[*].skillShield").value(hasItem(DEFAULT_SKILL_SHIELD.toString())))
            .andExpect(jsonPath("$.[*].mainActivity").value(hasItem(DEFAULT_MAIN_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].carryActivity").value(hasItem(DEFAULT_CARRY_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].skillExpends").value(hasItem(DEFAULT_SKILL_EXPENDS.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].collaboType").value(hasItem(DEFAULT_COLLABO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].consideration").value(hasItem(DEFAULT_CONSIDERATION.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getShareProfil() throws Exception {
        // Initialize the database
        shareProfilRepository.saveAndFlush(shareProfil);

        // Get the shareProfil
        restShareProfilMockMvc.perform(get("/api/share-profils/{id}", shareProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shareProfil.getId().intValue()))
            .andExpect(jsonPath("$.targetJob").value(DEFAULT_TARGET_JOB.toString()))
            .andExpect(jsonPath("$.skillShield").value(DEFAULT_SKILL_SHIELD.toString()))
            .andExpect(jsonPath("$.mainActivity").value(DEFAULT_MAIN_ACTIVITY.toString()))
            .andExpect(jsonPath("$.carryActivity").value(DEFAULT_CARRY_ACTIVITY.toString()))
            .andExpect(jsonPath("$.skillExpends").value(DEFAULT_SKILL_EXPENDS.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.collaboType").value(DEFAULT_COLLABO_TYPE.toString()))
            .andExpect(jsonPath("$.consideration").value(DEFAULT_CONSIDERATION.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShareProfil() throws Exception {
        // Get the shareProfil
        restShareProfilMockMvc.perform(get("/api/share-profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShareProfil() throws Exception {
        // Initialize the database
        shareProfilRepository.saveAndFlush(shareProfil);
        shareProfilSearchRepository.save(shareProfil);
        int databaseSizeBeforeUpdate = shareProfilRepository.findAll().size();

        // Update the shareProfil
        ShareProfil updatedShareProfil = shareProfilRepository.findOne(shareProfil.getId());
        // Disconnect from session so that the updates on updatedShareProfil are not directly saved in db
        em.detach(updatedShareProfil);
        updatedShareProfil
            .targetJob(UPDATED_TARGET_JOB)
            .skillShield(UPDATED_SKILL_SHIELD)
            .mainActivity(UPDATED_MAIN_ACTIVITY)
            .carryActivity(UPDATED_CARRY_ACTIVITY)
            .skillExpends(UPDATED_SKILL_EXPENDS)
            .context(UPDATED_CONTEXT)
            .collaboType(UPDATED_COLLABO_TYPE)
            .consideration(UPDATED_CONSIDERATION)
            .comment(UPDATED_COMMENT);
        ShareProfilDTO shareProfilDTO = shareProfilMapper.toDto(updatedShareProfil);

        restShareProfilMockMvc.perform(put("/api/share-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareProfilDTO)))
            .andExpect(status().isOk());

        // Validate the ShareProfil in the database
        List<ShareProfil> shareProfilList = shareProfilRepository.findAll();
        assertThat(shareProfilList).hasSize(databaseSizeBeforeUpdate);
        ShareProfil testShareProfil = shareProfilList.get(shareProfilList.size() - 1);
        assertThat(testShareProfil.getTargetJob()).isEqualTo(UPDATED_TARGET_JOB);
        assertThat(testShareProfil.getSkillShield()).isEqualTo(UPDATED_SKILL_SHIELD);
        assertThat(testShareProfil.getMainActivity()).isEqualTo(UPDATED_MAIN_ACTIVITY);
        assertThat(testShareProfil.getCarryActivity()).isEqualTo(UPDATED_CARRY_ACTIVITY);
        assertThat(testShareProfil.getSkillExpends()).isEqualTo(UPDATED_SKILL_EXPENDS);
        assertThat(testShareProfil.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testShareProfil.getCollaboType()).isEqualTo(UPDATED_COLLABO_TYPE);
        assertThat(testShareProfil.getConsideration()).isEqualTo(UPDATED_CONSIDERATION);
        assertThat(testShareProfil.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the ShareProfil in Elasticsearch
        ShareProfil shareProfilEs = shareProfilSearchRepository.findOne(testShareProfil.getId());
        assertThat(shareProfilEs).isEqualToIgnoringGivenFields(testShareProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingShareProfil() throws Exception {
        int databaseSizeBeforeUpdate = shareProfilRepository.findAll().size();

        // Create the ShareProfil
        ShareProfilDTO shareProfilDTO = shareProfilMapper.toDto(shareProfil);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShareProfilMockMvc.perform(put("/api/share-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareProfilDTO)))
            .andExpect(status().isCreated());

        // Validate the ShareProfil in the database
        List<ShareProfil> shareProfilList = shareProfilRepository.findAll();
        assertThat(shareProfilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShareProfil() throws Exception {
        // Initialize the database
        shareProfilRepository.saveAndFlush(shareProfil);
        shareProfilSearchRepository.save(shareProfil);
        int databaseSizeBeforeDelete = shareProfilRepository.findAll().size();

        // Get the shareProfil
        restShareProfilMockMvc.perform(delete("/api/share-profils/{id}", shareProfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shareProfilExistsInEs = shareProfilSearchRepository.exists(shareProfil.getId());
        assertThat(shareProfilExistsInEs).isFalse();

        // Validate the database is empty
        List<ShareProfil> shareProfilList = shareProfilRepository.findAll();
        assertThat(shareProfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShareProfil() throws Exception {
        // Initialize the database
        shareProfilRepository.saveAndFlush(shareProfil);
        shareProfilSearchRepository.save(shareProfil);

        // Search the shareProfil
        restShareProfilMockMvc.perform(get("/api/_search/share-profils?query=id:" + shareProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shareProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].targetJob").value(hasItem(DEFAULT_TARGET_JOB.toString())))
            .andExpect(jsonPath("$.[*].skillShield").value(hasItem(DEFAULT_SKILL_SHIELD.toString())))
            .andExpect(jsonPath("$.[*].mainActivity").value(hasItem(DEFAULT_MAIN_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].carryActivity").value(hasItem(DEFAULT_CARRY_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].skillExpends").value(hasItem(DEFAULT_SKILL_EXPENDS.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].collaboType").value(hasItem(DEFAULT_COLLABO_TYPE.toString())))
            .andExpect(jsonPath("$.[*].consideration").value(hasItem(DEFAULT_CONSIDERATION.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShareProfil.class);
        ShareProfil shareProfil1 = new ShareProfil();
        shareProfil1.setId(1L);
        ShareProfil shareProfil2 = new ShareProfil();
        shareProfil2.setId(shareProfil1.getId());
        assertThat(shareProfil1).isEqualTo(shareProfil2);
        shareProfil2.setId(2L);
        assertThat(shareProfil1).isNotEqualTo(shareProfil2);
        shareProfil1.setId(null);
        assertThat(shareProfil1).isNotEqualTo(shareProfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShareProfilDTO.class);
        ShareProfilDTO shareProfilDTO1 = new ShareProfilDTO();
        shareProfilDTO1.setId(1L);
        ShareProfilDTO shareProfilDTO2 = new ShareProfilDTO();
        assertThat(shareProfilDTO1).isNotEqualTo(shareProfilDTO2);
        shareProfilDTO2.setId(shareProfilDTO1.getId());
        assertThat(shareProfilDTO1).isEqualTo(shareProfilDTO2);
        shareProfilDTO2.setId(2L);
        assertThat(shareProfilDTO1).isNotEqualTo(shareProfilDTO2);
        shareProfilDTO1.setId(null);
        assertThat(shareProfilDTO1).isNotEqualTo(shareProfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shareProfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shareProfilMapper.fromId(null)).isNull();
    }
}
