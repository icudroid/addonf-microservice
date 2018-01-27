package com.adloveyou.ms.goosegame.web.rest;

import com.adloveyou.ms.goosegame.GooseGameApp;

import com.adloveyou.ms.goosegame.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.goosegame.domain.GooseCase;
import com.adloveyou.ms.goosegame.repository.GooseCaseRepository;
import com.adloveyou.ms.goosegame.service.GooseCaseService;
import com.adloveyou.ms.goosegame.repository.search.GooseCaseSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseCaseDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseCaseMapper;
import com.adloveyou.ms.web.rest.TestUtil;
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

import javax.persistence.EntityManager;
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GooseCaseResource REST controller.
 *
 * @see GooseCaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GooseGameApp.class, SecurityBeanOverrideConfiguration.class})
public class GooseCaseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private GooseCaseRepository gooseCaseRepository;

    @Autowired
    private GooseCaseMapper gooseCaseMapper;

    @Autowired
    private GooseCaseService gooseCaseService;

    @Autowired
    private GooseCaseSearchRepository gooseCaseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGooseCaseMockMvc;

    private GooseCase gooseCase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GooseCaseResource gooseCaseResource = new GooseCaseResource(gooseCaseService);
        this.restGooseCaseMockMvc = MockMvcBuilders.standaloneSetup(gooseCaseResource)
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
    public static GooseCase createEntity(EntityManager em) {
        GooseCase gooseCase = new GooseCase()
            .name(DEFAULT_NAME);
        return gooseCase;
    }

    @Before
    public void initTest() {
        gooseCaseSearchRepository.deleteAll();
        gooseCase = createEntity(em);
    }

    @Test
    @Transactional
    public void createGooseCase() throws Exception {
        int databaseSizeBeforeCreate = gooseCaseRepository.findAll().size();

        // Create the GooseCase
        GooseCaseDTO gooseCaseDTO = gooseCaseMapper.toDto(gooseCase);
        restGooseCaseMockMvc.perform(post("/api/goose-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseCaseDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseCase in the database
        List<GooseCase> gooseCaseList = gooseCaseRepository.findAll();
        assertThat(gooseCaseList).hasSize(databaseSizeBeforeCreate + 1);
        GooseCase testGooseCase = gooseCaseList.get(gooseCaseList.size() - 1);
        assertThat(testGooseCase.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the GooseCase in Elasticsearch
        GooseCase gooseCaseEs = gooseCaseSearchRepository.findOne(testGooseCase.getId());
        assertThat(gooseCaseEs).isEqualToIgnoringGivenFields(testGooseCase);
    }

    @Test
    @Transactional
    public void createGooseCaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gooseCaseRepository.findAll().size();

        // Create the GooseCase with an existing ID
        gooseCase.setId(1L);
        GooseCaseDTO gooseCaseDTO = gooseCaseMapper.toDto(gooseCase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGooseCaseMockMvc.perform(post("/api/goose-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseCaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GooseCase in the database
        List<GooseCase> gooseCaseList = gooseCaseRepository.findAll();
        assertThat(gooseCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGooseCases() throws Exception {
        // Initialize the database
        gooseCaseRepository.saveAndFlush(gooseCase);

        // Get all the gooseCaseList
        restGooseCaseMockMvc.perform(get("/api/goose-cases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getGooseCase() throws Exception {
        // Initialize the database
        gooseCaseRepository.saveAndFlush(gooseCase);

        // Get the gooseCase
        restGooseCaseMockMvc.perform(get("/api/goose-cases/{id}", gooseCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gooseCase.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGooseCase() throws Exception {
        // Get the gooseCase
        restGooseCaseMockMvc.perform(get("/api/goose-cases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGooseCase() throws Exception {
        // Initialize the database
        gooseCaseRepository.saveAndFlush(gooseCase);
        gooseCaseSearchRepository.save(gooseCase);
        int databaseSizeBeforeUpdate = gooseCaseRepository.findAll().size();

        // Update the gooseCase
        GooseCase updatedGooseCase = gooseCaseRepository.findOne(gooseCase.getId());
        // Disconnect from session so that the updates on updatedGooseCase are not directly saved in db
        em.detach(updatedGooseCase);
        updatedGooseCase
            .name(UPDATED_NAME);
        GooseCaseDTO gooseCaseDTO = gooseCaseMapper.toDto(updatedGooseCase);

        restGooseCaseMockMvc.perform(put("/api/goose-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseCaseDTO)))
            .andExpect(status().isOk());

        // Validate the GooseCase in the database
        List<GooseCase> gooseCaseList = gooseCaseRepository.findAll();
        assertThat(gooseCaseList).hasSize(databaseSizeBeforeUpdate);
        GooseCase testGooseCase = gooseCaseList.get(gooseCaseList.size() - 1);
        assertThat(testGooseCase.getName()).isEqualTo(UPDATED_NAME);

        // Validate the GooseCase in Elasticsearch
        GooseCase gooseCaseEs = gooseCaseSearchRepository.findOne(testGooseCase.getId());
        assertThat(gooseCaseEs).isEqualToIgnoringGivenFields(testGooseCase);
    }

    @Test
    @Transactional
    public void updateNonExistingGooseCase() throws Exception {
        int databaseSizeBeforeUpdate = gooseCaseRepository.findAll().size();

        // Create the GooseCase
        GooseCaseDTO gooseCaseDTO = gooseCaseMapper.toDto(gooseCase);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGooseCaseMockMvc.perform(put("/api/goose-cases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseCaseDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseCase in the database
        List<GooseCase> gooseCaseList = gooseCaseRepository.findAll();
        assertThat(gooseCaseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGooseCase() throws Exception {
        // Initialize the database
        gooseCaseRepository.saveAndFlush(gooseCase);
        gooseCaseSearchRepository.save(gooseCase);
        int databaseSizeBeforeDelete = gooseCaseRepository.findAll().size();

        // Get the gooseCase
        restGooseCaseMockMvc.perform(delete("/api/goose-cases/{id}", gooseCase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gooseCaseExistsInEs = gooseCaseSearchRepository.exists(gooseCase.getId());
        assertThat(gooseCaseExistsInEs).isFalse();

        // Validate the database is empty
        List<GooseCase> gooseCaseList = gooseCaseRepository.findAll();
        assertThat(gooseCaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGooseCase() throws Exception {
        // Initialize the database
        gooseCaseRepository.saveAndFlush(gooseCase);
        gooseCaseSearchRepository.save(gooseCase);

        // Search the gooseCase
        restGooseCaseMockMvc.perform(get("/api/_search/goose-cases?query=id:" + gooseCase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseCase.class);
        GooseCase gooseCase1 = new GooseCase();
        gooseCase1.setId(1L);
        GooseCase gooseCase2 = new GooseCase();
        gooseCase2.setId(gooseCase1.getId());
        assertThat(gooseCase1).isEqualTo(gooseCase2);
        gooseCase2.setId(2L);
        assertThat(gooseCase1).isNotEqualTo(gooseCase2);
        gooseCase1.setId(null);
        assertThat(gooseCase1).isNotEqualTo(gooseCase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseCaseDTO.class);
        GooseCaseDTO gooseCaseDTO1 = new GooseCaseDTO();
        gooseCaseDTO1.setId(1L);
        GooseCaseDTO gooseCaseDTO2 = new GooseCaseDTO();
        assertThat(gooseCaseDTO1).isNotEqualTo(gooseCaseDTO2);
        gooseCaseDTO2.setId(gooseCaseDTO1.getId());
        assertThat(gooseCaseDTO1).isEqualTo(gooseCaseDTO2);
        gooseCaseDTO2.setId(2L);
        assertThat(gooseCaseDTO1).isNotEqualTo(gooseCaseDTO2);
        gooseCaseDTO1.setId(null);
        assertThat(gooseCaseDTO1).isNotEqualTo(gooseCaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gooseCaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gooseCaseMapper.fromId(null)).isNull();
    }
}
