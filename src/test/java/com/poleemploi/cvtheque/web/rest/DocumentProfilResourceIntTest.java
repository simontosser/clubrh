package com.poleemploi.cvtheque.web.rest;

import com.poleemploi.cvtheque.CvthequeApp;

import com.poleemploi.cvtheque.domain.DocumentProfil;
import com.poleemploi.cvtheque.repository.DocumentProfilRepository;
import com.poleemploi.cvtheque.service.DocumentProfilService;
import com.poleemploi.cvtheque.repository.search.DocumentProfilSearchRepository;
import com.poleemploi.cvtheque.service.dto.DocumentProfilDTO;
import com.poleemploi.cvtheque.service.mapper.DocumentProfilMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.poleemploi.cvtheque.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DocumentProfilResource REST controller.
 *
 * @see DocumentProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CvthequeApp.class)
public class DocumentProfilResourceIntTest {

    private static final byte[] DEFAULT_DOCUMENT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOCUMENT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private DocumentProfilRepository documentProfilRepository;

    @Autowired
    private DocumentProfilMapper documentProfilMapper;

    @Autowired
    private DocumentProfilService documentProfilService;

    @Autowired
    private DocumentProfilSearchRepository documentProfilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentProfilMockMvc;

    private DocumentProfil documentProfil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentProfilResource documentProfilResource = new DocumentProfilResource(documentProfilService);
        this.restDocumentProfilMockMvc = MockMvcBuilders.standaloneSetup(documentProfilResource)
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
    public static DocumentProfil createEntity(EntityManager em) {
        DocumentProfil documentProfil = new DocumentProfil()
            .documentFile(DEFAULT_DOCUMENT_FILE)
            .documentFileContentType(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE);
        return documentProfil;
    }

    @Before
    public void initTest() {
        documentProfilSearchRepository.deleteAll();
        documentProfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentProfil() throws Exception {
        int databaseSizeBeforeCreate = documentProfilRepository.findAll().size();

        // Create the DocumentProfil
        DocumentProfilDTO documentProfilDTO = documentProfilMapper.toDto(documentProfil);
        restDocumentProfilMockMvc.perform(post("/api/document-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentProfilDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentProfil in the database
        List<DocumentProfil> documentProfilList = documentProfilRepository.findAll();
        assertThat(documentProfilList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentProfil testDocumentProfil = documentProfilList.get(documentProfilList.size() - 1);
        assertThat(testDocumentProfil.getDocumentFile()).isEqualTo(DEFAULT_DOCUMENT_FILE);
        assertThat(testDocumentProfil.getDocumentFileContentType()).isEqualTo(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE);

        // Validate the DocumentProfil in Elasticsearch
        DocumentProfil documentProfilEs = documentProfilSearchRepository.findOne(testDocumentProfil.getId());
        assertThat(documentProfilEs).isEqualToIgnoringGivenFields(testDocumentProfil);
    }

    @Test
    @Transactional
    public void createDocumentProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentProfilRepository.findAll().size();

        // Create the DocumentProfil with an existing ID
        documentProfil.setId(1L);
        DocumentProfilDTO documentProfilDTO = documentProfilMapper.toDto(documentProfil);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentProfilMockMvc.perform(post("/api/document-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentProfilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentProfil in the database
        List<DocumentProfil> documentProfilList = documentProfilRepository.findAll();
        assertThat(documentProfilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDocumentFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentProfilRepository.findAll().size();
        // set the field null
        documentProfil.setDocumentFile(null);

        // Create the DocumentProfil, which fails.
        DocumentProfilDTO documentProfilDTO = documentProfilMapper.toDto(documentProfil);

        restDocumentProfilMockMvc.perform(post("/api/document-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentProfilDTO)))
            .andExpect(status().isBadRequest());

        List<DocumentProfil> documentProfilList = documentProfilRepository.findAll();
        assertThat(documentProfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentProfils() throws Exception {
        // Initialize the database
        documentProfilRepository.saveAndFlush(documentProfil);

        // Get all the documentProfilList
        restDocumentProfilMockMvc.perform(get("/api/document-profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentFileContentType").value(hasItem(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE))));
    }

    @Test
    @Transactional
    public void getDocumentProfil() throws Exception {
        // Initialize the database
        documentProfilRepository.saveAndFlush(documentProfil);

        // Get the documentProfil
        restDocumentProfilMockMvc.perform(get("/api/document-profils/{id}", documentProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentProfil.getId().intValue()))
            .andExpect(jsonPath("$.documentFileContentType").value(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentFile").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentProfil() throws Exception {
        // Get the documentProfil
        restDocumentProfilMockMvc.perform(get("/api/document-profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentProfil() throws Exception {
        // Initialize the database
        documentProfilRepository.saveAndFlush(documentProfil);
        documentProfilSearchRepository.save(documentProfil);
        int databaseSizeBeforeUpdate = documentProfilRepository.findAll().size();

        // Update the documentProfil
        DocumentProfil updatedDocumentProfil = documentProfilRepository.findOne(documentProfil.getId());
        // Disconnect from session so that the updates on updatedDocumentProfil are not directly saved in db
        em.detach(updatedDocumentProfil);
        updatedDocumentProfil
            .documentFile(UPDATED_DOCUMENT_FILE)
            .documentFileContentType(UPDATED_DOCUMENT_FILE_CONTENT_TYPE);
        DocumentProfilDTO documentProfilDTO = documentProfilMapper.toDto(updatedDocumentProfil);

        restDocumentProfilMockMvc.perform(put("/api/document-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentProfilDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentProfil in the database
        List<DocumentProfil> documentProfilList = documentProfilRepository.findAll();
        assertThat(documentProfilList).hasSize(databaseSizeBeforeUpdate);
        DocumentProfil testDocumentProfil = documentProfilList.get(documentProfilList.size() - 1);
        assertThat(testDocumentProfil.getDocumentFile()).isEqualTo(UPDATED_DOCUMENT_FILE);
        assertThat(testDocumentProfil.getDocumentFileContentType()).isEqualTo(UPDATED_DOCUMENT_FILE_CONTENT_TYPE);

        // Validate the DocumentProfil in Elasticsearch
        DocumentProfil documentProfilEs = documentProfilSearchRepository.findOne(testDocumentProfil.getId());
        assertThat(documentProfilEs).isEqualToIgnoringGivenFields(testDocumentProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentProfil() throws Exception {
        int databaseSizeBeforeUpdate = documentProfilRepository.findAll().size();

        // Create the DocumentProfil
        DocumentProfilDTO documentProfilDTO = documentProfilMapper.toDto(documentProfil);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDocumentProfilMockMvc.perform(put("/api/document-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentProfilDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentProfil in the database
        List<DocumentProfil> documentProfilList = documentProfilRepository.findAll();
        assertThat(documentProfilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDocumentProfil() throws Exception {
        // Initialize the database
        documentProfilRepository.saveAndFlush(documentProfil);
        documentProfilSearchRepository.save(documentProfil);
        int databaseSizeBeforeDelete = documentProfilRepository.findAll().size();

        // Get the documentProfil
        restDocumentProfilMockMvc.perform(delete("/api/document-profils/{id}", documentProfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean documentProfilExistsInEs = documentProfilSearchRepository.exists(documentProfil.getId());
        assertThat(documentProfilExistsInEs).isFalse();

        // Validate the database is empty
        List<DocumentProfil> documentProfilList = documentProfilRepository.findAll();
        assertThat(documentProfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDocumentProfil() throws Exception {
        // Initialize the database
        documentProfilRepository.saveAndFlush(documentProfil);
        documentProfilSearchRepository.save(documentProfil);

        // Search the documentProfil
        restDocumentProfilMockMvc.perform(get("/api/_search/document-profils?query=id:" + documentProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentFileContentType").value(hasItem(DEFAULT_DOCUMENT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentProfil.class);
        DocumentProfil documentProfil1 = new DocumentProfil();
        documentProfil1.setId(1L);
        DocumentProfil documentProfil2 = new DocumentProfil();
        documentProfil2.setId(documentProfil1.getId());
        assertThat(documentProfil1).isEqualTo(documentProfil2);
        documentProfil2.setId(2L);
        assertThat(documentProfil1).isNotEqualTo(documentProfil2);
        documentProfil1.setId(null);
        assertThat(documentProfil1).isNotEqualTo(documentProfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentProfilDTO.class);
        DocumentProfilDTO documentProfilDTO1 = new DocumentProfilDTO();
        documentProfilDTO1.setId(1L);
        DocumentProfilDTO documentProfilDTO2 = new DocumentProfilDTO();
        assertThat(documentProfilDTO1).isNotEqualTo(documentProfilDTO2);
        documentProfilDTO2.setId(documentProfilDTO1.getId());
        assertThat(documentProfilDTO1).isEqualTo(documentProfilDTO2);
        documentProfilDTO2.setId(2L);
        assertThat(documentProfilDTO1).isNotEqualTo(documentProfilDTO2);
        documentProfilDTO1.setId(null);
        assertThat(documentProfilDTO1).isNotEqualTo(documentProfilDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentProfilMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentProfilMapper.fromId(null)).isNull();
    }
}
