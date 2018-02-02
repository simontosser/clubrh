package com.poleemploi.cvtheque.web.rest;

import com.poleemploi.cvtheque.CvthequeApp;

import com.poleemploi.cvtheque.domain.RecrutementProfil;
import com.poleemploi.cvtheque.repository.RecrutementProfilRepository;
import com.poleemploi.cvtheque.service.RecrutementProfilService;
import com.poleemploi.cvtheque.repository.search.RecrutementProfilSearchRepository;
import com.poleemploi.cvtheque.service.dto.RecrutementProfilDTO;
import com.poleemploi.cvtheque.service.mapper.RecrutementProfilMapper;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.poleemploi.cvtheque.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecrutementProfilResource REST controller.
 *
 * @see RecrutementProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CvthequeApp.class)
public class RecrutementProfilResourceIntTest {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FINALY_OBJECT = "AAAAAAAAAA";
    private static final String UPDATED_FINALY_OBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_SKILL = "BBBBBBBBBB";

    private static final String DEFAULT_KNOWNLEDGE = "AAAAAAAAAA";
    private static final String UPDATED_KNOWNLEDGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEADLINE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONSIDERATION = "AAAAAAAAAA";
    private static final String UPDATED_CONSIDERATION = "BBBBBBBBBB";

    private static final String DEFAULT_REMIND = "AAAAAAAAAA";
    private static final String UPDATED_REMIND = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private RecrutementProfilRepository recrutementProfilRepository;

    @Autowired
    private RecrutementProfilMapper recrutementProfilMapper;

    @Autowired
    private RecrutementProfilService recrutementProfilService;

    @Autowired
    private RecrutementProfilSearchRepository recrutementProfilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecrutementProfilMockMvc;

    private RecrutementProfil recrutementProfil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecrutementProfilResource recrutementProfilResource = new RecrutementProfilResource(recrutementProfilService);
        this.restRecrutementProfilMockMvc = MockMvcBuilders.standaloneSetup(recrutementProfilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecrutementProfil createEntity(EntityManager em) {
        RecrutementProfil recrutementProfil = new RecrutementProfil()
            .jobTitle(DEFAULT_JOB_TITLE)
            .context(DEFAULT_CONTEXT)
            .finalyObject(DEFAULT_FINALY_OBJECT)
            .mainActivity(DEFAULT_MAIN_ACTIVITY)
            .mainSkill(DEFAULT_MAIN_SKILL)
            .knownledge(DEFAULT_KNOWNLEDGE)
            .deadline(DEFAULT_DEADLINE)
            .consideration(DEFAULT_CONSIDERATION)
            .remind(DEFAULT_REMIND)
            .comment(DEFAULT_COMMENT);
        return recrutementProfil;
    }

    @Before
    public void initTest() {
        recrutementProfilSearchRepository.deleteAll();
        recrutementProfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecrutementProfil() throws Exception {
        int databaseSizeBeforeCreate = recrutementProfilRepository.findAll().size();

        // Create the RecrutementProfil
        RecrutementProfilDTO recrutementProfilDTO = recrutementProfilMapper.toDto(recrutementProfil);
        restRecrutementProfilMockMvc.perform(post("/api/recrutement-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recrutementProfilDTO)))
            .andExpect(status().isCreated());

        // Validate the RecrutementProfil in the database
        List<RecrutementProfil> recrutementProfilList = recrutementProfilRepository.findAll();
        assertThat(recrutementProfilList).hasSize(databaseSizeBeforeCreate + 1);
        RecrutementProfil testRecrutementProfil = recrutementProfilList.get(recrutementProfilList.size() - 1);
        assertThat(testRecrutementProfil.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testRecrutementProfil.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testRecrutementProfil.getFinalyObject()).isEqualTo(DEFAULT_FINALY_OBJECT);
        assertThat(testRecrutementProfil.getMainActivity()).isEqualTo(DEFAULT_MAIN_ACTIVITY);
        assertThat(testRecrutementProfil.getMainSkill()).isEqualTo(DEFAULT_MAIN_SKILL);
        assertThat(testRecrutementProfil.getKnownledge()).isEqualTo(DEFAULT_KNOWNLEDGE);
        assertThat(testRecrutementProfil.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testRecrutementProfil.getConsideration()).isEqualTo(DEFAULT_CONSIDERATION);
        assertThat(testRecrutementProfil.getRemind()).isEqualTo(DEFAULT_REMIND);
        assertThat(testRecrutementProfil.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the RecrutementProfil in Elasticsearch
        RecrutementProfil recrutementProfilEs = recrutementProfilSearchRepository.findOne(testRecrutementProfil.getId());
        assertThat(recrutementProfilEs).isEqualToIgnoringGivenFields(testRecrutementProfil);
    }

    @Test
    @Transactional
    public void createRecrutementProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recrutementProfilRepository.findAll().size();

        // Create the RecrutementProfil with an existing ID
        recrutementProfil.setId(1L);
        RecrutementProfilDTO recrutementProfilDTO = recrutementProfilMapper.toDto(recrutementProfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecrutementProfilMockMvc.perform(post("/api/recrutement-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recrutementProfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecrutementProfil in the database
        List<RecrutementProfil> recrutementProfilList = recrutementProfilRepository.findAll();
        assertThat(recrutementProfilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJobTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = recrutementProfilRepository.findAll().size();
        // set the field null
        recrutementProfil.setJobTitle(null);

        // Create the RecrutementProfil, which fails.
        RecrutementProfilDTO recrutementProfilDTO = recrutementProfilMapper.toDto(recrutementProfil);

        restRecrutementProfilMockMvc.perform(post("/api/recrutement-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recrutementProfilDTO)))
            .andExpect(status().isBadRequest());

        List<RecrutementProfil> recrutementProfilList = recrutementProfilRepository.findAll();
        assertThat(recrutementProfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecrutementProfils() throws Exception {
        // Initialize the database
        recrutementProfilRepository.saveAndFlush(recrutementProfil);

        // Get all the recrutementProfilList
        restRecrutementProfilMockMvc.perform(get("/api/recrutement-profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recrutementProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].finalyObject").value(hasItem(DEFAULT_FINALY_OBJECT.toString())))
            .andExpect(jsonPath("$.[*].mainActivity").value(hasItem(DEFAULT_MAIN_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].mainSkill").value(hasItem(DEFAULT_MAIN_SKILL.toString())))
            .andExpect(jsonPath("$.[*].knownledge").value(hasItem(DEFAULT_KNOWNLEDGE.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].consideration").value(hasItem(DEFAULT_CONSIDERATION.toString())))
            .andExpect(jsonPath("$.[*].remind").value(hasItem(DEFAULT_REMIND.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getRecrutementProfil() throws Exception {
        // Initialize the database
        recrutementProfilRepository.saveAndFlush(recrutementProfil);

        // Get the recrutementProfil
        restRecrutementProfilMockMvc.perform(get("/api/recrutement-profils/{id}", recrutementProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recrutementProfil.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.finalyObject").value(DEFAULT_FINALY_OBJECT.toString()))
            .andExpect(jsonPath("$.mainActivity").value(DEFAULT_MAIN_ACTIVITY.toString()))
            .andExpect(jsonPath("$.mainSkill").value(DEFAULT_MAIN_SKILL.toString()))
            .andExpect(jsonPath("$.knownledge").value(DEFAULT_KNOWNLEDGE.toString()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()))
            .andExpect(jsonPath("$.consideration").value(DEFAULT_CONSIDERATION.toString()))
            .andExpect(jsonPath("$.remind").value(DEFAULT_REMIND.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecrutementProfil() throws Exception {
        // Get the recrutementProfil
        restRecrutementProfilMockMvc.perform(get("/api/recrutement-profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecrutementProfil() throws Exception {
        // Initialize the database
        recrutementProfilRepository.saveAndFlush(recrutementProfil);
        recrutementProfilSearchRepository.save(recrutementProfil);
        int databaseSizeBeforeUpdate = recrutementProfilRepository.findAll().size();

        // Update the recrutementProfil
        RecrutementProfil updatedRecrutementProfil = recrutementProfilRepository.findOne(recrutementProfil.getId());
        // Disconnect from session so that the updates on updatedRecrutementProfil are not directly saved in db
        em.detach(updatedRecrutementProfil);
        updatedRecrutementProfil
            .jobTitle(UPDATED_JOB_TITLE)
            .context(UPDATED_CONTEXT)
            .finalyObject(UPDATED_FINALY_OBJECT)
            .mainActivity(UPDATED_MAIN_ACTIVITY)
            .mainSkill(UPDATED_MAIN_SKILL)
            .knownledge(UPDATED_KNOWNLEDGE)
            .deadline(UPDATED_DEADLINE)
            .consideration(UPDATED_CONSIDERATION)
            .remind(UPDATED_REMIND)
            .comment(UPDATED_COMMENT);
        RecrutementProfilDTO recrutementProfilDTO = recrutementProfilMapper.toDto(updatedRecrutementProfil);

        restRecrutementProfilMockMvc.perform(put("/api/recrutement-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recrutementProfilDTO)))
            .andExpect(status().isOk());

        // Validate the RecrutementProfil in the database
        List<RecrutementProfil> recrutementProfilList = recrutementProfilRepository.findAll();
        assertThat(recrutementProfilList).hasSize(databaseSizeBeforeUpdate);
        RecrutementProfil testRecrutementProfil = recrutementProfilList.get(recrutementProfilList.size() - 1);
        assertThat(testRecrutementProfil.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testRecrutementProfil.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testRecrutementProfil.getFinalyObject()).isEqualTo(UPDATED_FINALY_OBJECT);
        assertThat(testRecrutementProfil.getMainActivity()).isEqualTo(UPDATED_MAIN_ACTIVITY);
        assertThat(testRecrutementProfil.getMainSkill()).isEqualTo(UPDATED_MAIN_SKILL);
        assertThat(testRecrutementProfil.getKnownledge()).isEqualTo(UPDATED_KNOWNLEDGE);
        assertThat(testRecrutementProfil.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRecrutementProfil.getConsideration()).isEqualTo(UPDATED_CONSIDERATION);
        assertThat(testRecrutementProfil.getRemind()).isEqualTo(UPDATED_REMIND);
        assertThat(testRecrutementProfil.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the RecrutementProfil in Elasticsearch
        RecrutementProfil recrutementProfilEs = recrutementProfilSearchRepository.findOne(testRecrutementProfil.getId());
        assertThat(recrutementProfilEs).isEqualToIgnoringGivenFields(testRecrutementProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingRecrutementProfil() throws Exception {
        int databaseSizeBeforeUpdate = recrutementProfilRepository.findAll().size();

        // Create the RecrutementProfil
        RecrutementProfilDTO recrutementProfilDTO = recrutementProfilMapper.toDto(recrutementProfil);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecrutementProfilMockMvc.perform(put("/api/recrutement-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recrutementProfilDTO)))
            .andExpect(status().isCreated());

        // Validate the RecrutementProfil in the database
        List<RecrutementProfil> recrutementProfilList = recrutementProfilRepository.findAll();
        assertThat(recrutementProfilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecrutementProfil() throws Exception {
        // Initialize the database
        recrutementProfilRepository.saveAndFlush(recrutementProfil);
        recrutementProfilSearchRepository.save(recrutementProfil);
        int databaseSizeBeforeDelete = recrutementProfilRepository.findAll().size();

        // Get the recrutementProfil
        restRecrutementProfilMockMvc.perform(delete("/api/recrutement-profils/{id}", recrutementProfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean recrutementProfilExistsInEs = recrutementProfilSearchRepository.exists(recrutementProfil.getId());
        assertThat(recrutementProfilExistsInEs).isFalse();

        // Validate the database is empty
        List<RecrutementProfil> recrutementProfilList = recrutementProfilRepository.findAll();
        assertThat(recrutementProfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRecrutementProfil() throws Exception {
        // Initialize the database
        recrutementProfilRepository.saveAndFlush(recrutementProfil);
        recrutementProfilSearchRepository.save(recrutementProfil);

        // Search the recrutementProfil
        restRecrutementProfilMockMvc.perform(get("/api/_search/recrutement-profils?query=id:" + recrutementProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recrutementProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].finalyObject").value(hasItem(DEFAULT_FINALY_OBJECT.toString())))
            .andExpect(jsonPath("$.[*].mainActivity").value(hasItem(DEFAULT_MAIN_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].mainSkill").value(hasItem(DEFAULT_MAIN_SKILL.toString())))
            .andExpect(jsonPath("$.[*].knownledge").value(hasItem(DEFAULT_KNOWNLEDGE.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].consideration").value(hasItem(DEFAULT_CONSIDERATION.toString())))
            .andExpect(jsonPath("$.[*].remind").value(hasItem(DEFAULT_REMIND.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecrutementProfil.class);
        RecrutementProfil recrutementProfil1 = new RecrutementProfil();
        recrutementProfil1.setId(1L);
        RecrutementProfil recrutementProfil2 = new RecrutementProfil();
        recrutementProfil2.setId(recrutementProfil1.getId());
        assertThat(recrutementProfil1).isEqualTo(recrutementProfil2);
        recrutementProfil2.setId(2L);
        assertThat(recrutementProfil1).isNotEqualTo(recrutementProfil2);
        recrutementProfil1.setId(null);
        assertThat(recrutementProfil1).isNotEqualTo(recrutementProfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecrutementProfilDTO.class);
        RecrutementProfilDTO recrutementProfilDTO1 = new RecrutementProfilDTO();
        recrutementProfilDTO1.setId(1L);
        RecrutementProfilDTO recrutementProfilDTO2 = new RecrutementProfilDTO();
        assertThat(recrutementProfilDTO1).isNotEqualTo(recrutementProfilDTO2);
        recrutementProfilDTO2.setId(recrutementProfilDTO1.getId());
        assertThat(recrutementProfilDTO1).isEqualTo(recrutementProfilDTO2);
        recrutementProfilDTO2.setId(2L);
        assertThat(recrutementProfilDTO1).isNotEqualTo(recrutementProfilDTO2);
        recrutementProfilDTO1.setId(null);
        assertThat(recrutementProfilDTO1).isNotEqualTo(recrutementProfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recrutementProfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recrutementProfilMapper.fromId(null)).isNull();
    }
}
