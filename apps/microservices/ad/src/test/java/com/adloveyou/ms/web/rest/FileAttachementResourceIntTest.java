package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.ad.FileAttachement;
import com.adloveyou.ms.repository.FileAttachementRepository;
import com.adloveyou.ms.service.FileAttachementService;
import com.adloveyou.ms.repository.search.FileAttachementSearchRepository;
import com.adloveyou.ms.service.dto.FileAttachementDTO;
import com.adloveyou.ms.service.mapper.FileAttachementMapper;
import com.adloveyou.ms.web.rest.errors.ExceptionTranslator;

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

import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FileAttachementResource REST controller.
 *
 * @see FileAttachementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class FileAttachementResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private FileAttachementRepository fileAttachementRepository;

    @Autowired
    private FileAttachementMapper fileAttachementMapper;

    @Autowired
    private FileAttachementService fileAttachementService;

    @Autowired
    private FileAttachementSearchRepository fileAttachementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileAttachementMockMvc;

    private FileAttachement fileAttachement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileAttachementResource fileAttachementResource = new FileAttachementResource(fileAttachementService);
        this.restFileAttachementMockMvc = MockMvcBuilders.standaloneSetup(fileAttachementResource)
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
    public static FileAttachement createEntity(EntityManager em) {
        FileAttachement fileAttachement = new FileAttachement()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return fileAttachement;
    }

    @Before
    public void initTest() {
        fileAttachementSearchRepository.deleteAll();
        fileAttachement = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileAttachement() throws Exception {
        int databaseSizeBeforeCreate = fileAttachementRepository.findAll().size();

        // Create the FileAttachement
        FileAttachementDTO fileAttachementDTO = fileAttachementMapper.toDto(fileAttachement);
        restFileAttachementMockMvc.perform(post("/api/file-attachements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileAttachementDTO)))
            .andExpect(status().isCreated());

        // Validate the FileAttachement in the database
        List<FileAttachement> fileAttachementList = fileAttachementRepository.findAll();
        assertThat(fileAttachementList).hasSize(databaseSizeBeforeCreate + 1);
        FileAttachement testFileAttachement = fileAttachementList.get(fileAttachementList.size() - 1);
        assertThat(testFileAttachement.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testFileAttachement.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);

        // Validate the FileAttachement in Elasticsearch
        FileAttachement fileAttachementEs = fileAttachementSearchRepository.findOne(testFileAttachement.getId());
        assertThat(fileAttachementEs).isEqualToIgnoringGivenFields(testFileAttachement);
    }

    @Test
    @Transactional
    public void createFileAttachementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileAttachementRepository.findAll().size();

        // Create the FileAttachement with an existing ID
        fileAttachement.setId(1L);
        FileAttachementDTO fileAttachementDTO = fileAttachementMapper.toDto(fileAttachement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileAttachementMockMvc.perform(post("/api/file-attachements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileAttachementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileAttachement in the database
        List<FileAttachement> fileAttachementList = fileAttachementRepository.findAll();
        assertThat(fileAttachementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileAttachementRepository.findAll().size();
        // set the field null
        fileAttachement.setFile(null);

        // Create the FileAttachement, which fails.
        FileAttachementDTO fileAttachementDTO = fileAttachementMapper.toDto(fileAttachement);

        restFileAttachementMockMvc.perform(post("/api/file-attachements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileAttachementDTO)))
            .andExpect(status().isBadRequest());

        List<FileAttachement> fileAttachementList = fileAttachementRepository.findAll();
        assertThat(fileAttachementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileAttachements() throws Exception {
        // Initialize the database
        fileAttachementRepository.saveAndFlush(fileAttachement);

        // Get all the fileAttachementList
        restFileAttachementMockMvc.perform(get("/api/file-attachements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileAttachement.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void getFileAttachement() throws Exception {
        // Initialize the database
        fileAttachementRepository.saveAndFlush(fileAttachement);

        // Get the fileAttachement
        restFileAttachementMockMvc.perform(get("/api/file-attachements/{id}", fileAttachement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileAttachement.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingFileAttachement() throws Exception {
        // Get the fileAttachement
        restFileAttachementMockMvc.perform(get("/api/file-attachements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileAttachement() throws Exception {
        // Initialize the database
        fileAttachementRepository.saveAndFlush(fileAttachement);
        fileAttachementSearchRepository.save(fileAttachement);
        int databaseSizeBeforeUpdate = fileAttachementRepository.findAll().size();

        // Update the fileAttachement
        FileAttachement updatedFileAttachement = fileAttachementRepository.findOne(fileAttachement.getId());
        // Disconnect from session so that the updates on updatedFileAttachement are not directly saved in db
        em.detach(updatedFileAttachement);
        updatedFileAttachement
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        FileAttachementDTO fileAttachementDTO = fileAttachementMapper.toDto(updatedFileAttachement);

        restFileAttachementMockMvc.perform(put("/api/file-attachements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileAttachementDTO)))
            .andExpect(status().isOk());

        // Validate the FileAttachement in the database
        List<FileAttachement> fileAttachementList = fileAttachementRepository.findAll();
        assertThat(fileAttachementList).hasSize(databaseSizeBeforeUpdate);
        FileAttachement testFileAttachement = fileAttachementList.get(fileAttachementList.size() - 1);
        assertThat(testFileAttachement.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testFileAttachement.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);

        // Validate the FileAttachement in Elasticsearch
        FileAttachement fileAttachementEs = fileAttachementSearchRepository.findOne(testFileAttachement.getId());
        assertThat(fileAttachementEs).isEqualToIgnoringGivenFields(testFileAttachement);
    }

    @Test
    @Transactional
    public void updateNonExistingFileAttachement() throws Exception {
        int databaseSizeBeforeUpdate = fileAttachementRepository.findAll().size();

        // Create the FileAttachement
        FileAttachementDTO fileAttachementDTO = fileAttachementMapper.toDto(fileAttachement);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileAttachementMockMvc.perform(put("/api/file-attachements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileAttachementDTO)))
            .andExpect(status().isCreated());

        // Validate the FileAttachement in the database
        List<FileAttachement> fileAttachementList = fileAttachementRepository.findAll();
        assertThat(fileAttachementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFileAttachement() throws Exception {
        // Initialize the database
        fileAttachementRepository.saveAndFlush(fileAttachement);
        fileAttachementSearchRepository.save(fileAttachement);
        int databaseSizeBeforeDelete = fileAttachementRepository.findAll().size();

        // Get the fileAttachement
        restFileAttachementMockMvc.perform(delete("/api/file-attachements/{id}", fileAttachement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean fileAttachementExistsInEs = fileAttachementSearchRepository.exists(fileAttachement.getId());
        assertThat(fileAttachementExistsInEs).isFalse();

        // Validate the database is empty
        List<FileAttachement> fileAttachementList = fileAttachementRepository.findAll();
        assertThat(fileAttachementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFileAttachement() throws Exception {
        // Initialize the database
        fileAttachementRepository.saveAndFlush(fileAttachement);
        fileAttachementSearchRepository.save(fileAttachement);

        // Search the fileAttachement
        restFileAttachementMockMvc.perform(get("/api/_search/file-attachements?query=id:" + fileAttachement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileAttachement.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileAttachement.class);
        FileAttachement fileAttachement1 = new FileAttachement();
        fileAttachement1.setId(1L);
        FileAttachement fileAttachement2 = new FileAttachement();
        fileAttachement2.setId(fileAttachement1.getId());
        assertThat(fileAttachement1).isEqualTo(fileAttachement2);
        fileAttachement2.setId(2L);
        assertThat(fileAttachement1).isNotEqualTo(fileAttachement2);
        fileAttachement1.setId(null);
        assertThat(fileAttachement1).isNotEqualTo(fileAttachement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileAttachementDTO.class);
        FileAttachementDTO fileAttachementDTO1 = new FileAttachementDTO();
        fileAttachementDTO1.setId(1L);
        FileAttachementDTO fileAttachementDTO2 = new FileAttachementDTO();
        assertThat(fileAttachementDTO1).isNotEqualTo(fileAttachementDTO2);
        fileAttachementDTO2.setId(fileAttachementDTO1.getId());
        assertThat(fileAttachementDTO1).isEqualTo(fileAttachementDTO2);
        fileAttachementDTO2.setId(2L);
        assertThat(fileAttachementDTO1).isNotEqualTo(fileAttachementDTO2);
        fileAttachementDTO1.setId(null);
        assertThat(fileAttachementDTO1).isNotEqualTo(fileAttachementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fileAttachementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fileAttachementMapper.fromId(null)).isNull();
    }
}
