package com.adloveyou.ms.goosegame.web.rest;

import com.adloveyou.ms.goosegame.GooseGameApp;

import com.adloveyou.ms.goosegame.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.goosegame.domain.GooseLevel;
import com.adloveyou.ms.goosegame.repository.GooseLevelRepository;
import com.adloveyou.ms.goosegame.service.GooseLevelService;
import com.adloveyou.ms.goosegame.repository.search.GooseLevelSearchRepository;
import com.adloveyou.ms.goosegame.service.dto.GooseLevelDTO;
import com.adloveyou.ms.goosegame.service.mapper.GooseLevelMapper;
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
 * Test class for the GooseLevelResource REST controller.
 *
 * @see GooseLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GooseGameApp.class, SecurityBeanOverrideConfiguration.class})
public class GooseLevelResourceIntTest {

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Long DEFAULT_LIMITED_TIME = 1L;
    private static final Long UPDATED_LIMITED_TIME = 2L;

    private static final Integer DEFAULT_NB_MAX_AD_BY_PLAY = 1;
    private static final Integer UPDATED_NB_MAX_AD_BY_PLAY = 2;

    @Autowired
    private GooseLevelRepository gooseLevelRepository;

    @Autowired
    private GooseLevelMapper gooseLevelMapper;

    @Autowired
    private GooseLevelService gooseLevelService;

    @Autowired
    private GooseLevelSearchRepository gooseLevelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGooseLevelMockMvc;

    private GooseLevel gooseLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GooseLevelResource gooseLevelResource = new GooseLevelResource(gooseLevelService);
        this.restGooseLevelMockMvc = MockMvcBuilders.standaloneSetup(gooseLevelResource)
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
    public static GooseLevel createEntity(EntityManager em) {
        GooseLevel gooseLevel = new GooseLevel()
            .level(DEFAULT_LEVEL)
            .limitedTime(DEFAULT_LIMITED_TIME)
            .nbMaxAdByPlay(DEFAULT_NB_MAX_AD_BY_PLAY);
        return gooseLevel;
    }

    @Before
    public void initTest() {
        gooseLevelSearchRepository.deleteAll();
        gooseLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createGooseLevel() throws Exception {
        int databaseSizeBeforeCreate = gooseLevelRepository.findAll().size();

        // Create the GooseLevel
        GooseLevelDTO gooseLevelDTO = gooseLevelMapper.toDto(gooseLevel);
        restGooseLevelMockMvc.perform(post("/api/goose-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseLevel in the database
        List<GooseLevel> gooseLevelList = gooseLevelRepository.findAll();
        assertThat(gooseLevelList).hasSize(databaseSizeBeforeCreate + 1);
        GooseLevel testGooseLevel = gooseLevelList.get(gooseLevelList.size() - 1);
        assertThat(testGooseLevel.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testGooseLevel.getLimitedTime()).isEqualTo(DEFAULT_LIMITED_TIME);
        assertThat(testGooseLevel.getNbMaxAdByPlay()).isEqualTo(DEFAULT_NB_MAX_AD_BY_PLAY);

        // Validate the GooseLevel in Elasticsearch
        GooseLevel gooseLevelEs = gooseLevelSearchRepository.findOne(testGooseLevel.getId());
        assertThat(gooseLevelEs).isEqualToIgnoringGivenFields(testGooseLevel);
    }

    @Test
    @Transactional
    public void createGooseLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gooseLevelRepository.findAll().size();

        // Create the GooseLevel with an existing ID
        gooseLevel.setId(1L);
        GooseLevelDTO gooseLevelDTO = gooseLevelMapper.toDto(gooseLevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGooseLevelMockMvc.perform(post("/api/goose-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseLevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GooseLevel in the database
        List<GooseLevel> gooseLevelList = gooseLevelRepository.findAll();
        assertThat(gooseLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGooseLevels() throws Exception {
        // Initialize the database
        gooseLevelRepository.saveAndFlush(gooseLevel);

        // Get all the gooseLevelList
        restGooseLevelMockMvc.perform(get("/api/goose-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].limitedTime").value(hasItem(DEFAULT_LIMITED_TIME.intValue())))
            .andExpect(jsonPath("$.[*].nbMaxAdByPlay").value(hasItem(DEFAULT_NB_MAX_AD_BY_PLAY)));
    }

    @Test
    @Transactional
    public void getGooseLevel() throws Exception {
        // Initialize the database
        gooseLevelRepository.saveAndFlush(gooseLevel);

        // Get the gooseLevel
        restGooseLevelMockMvc.perform(get("/api/goose-levels/{id}", gooseLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gooseLevel.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.limitedTime").value(DEFAULT_LIMITED_TIME.intValue()))
            .andExpect(jsonPath("$.nbMaxAdByPlay").value(DEFAULT_NB_MAX_AD_BY_PLAY));
    }

    @Test
    @Transactional
    public void getNonExistingGooseLevel() throws Exception {
        // Get the gooseLevel
        restGooseLevelMockMvc.perform(get("/api/goose-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGooseLevel() throws Exception {
        // Initialize the database
        gooseLevelRepository.saveAndFlush(gooseLevel);
        gooseLevelSearchRepository.save(gooseLevel);
        int databaseSizeBeforeUpdate = gooseLevelRepository.findAll().size();

        // Update the gooseLevel
        GooseLevel updatedGooseLevel = gooseLevelRepository.findOne(gooseLevel.getId());
        // Disconnect from session so that the updates on updatedGooseLevel are not directly saved in db
        em.detach(updatedGooseLevel);
        updatedGooseLevel
            .level(UPDATED_LEVEL)
            .limitedTime(UPDATED_LIMITED_TIME)
            .nbMaxAdByPlay(UPDATED_NB_MAX_AD_BY_PLAY);
        GooseLevelDTO gooseLevelDTO = gooseLevelMapper.toDto(updatedGooseLevel);

        restGooseLevelMockMvc.perform(put("/api/goose-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseLevelDTO)))
            .andExpect(status().isOk());

        // Validate the GooseLevel in the database
        List<GooseLevel> gooseLevelList = gooseLevelRepository.findAll();
        assertThat(gooseLevelList).hasSize(databaseSizeBeforeUpdate);
        GooseLevel testGooseLevel = gooseLevelList.get(gooseLevelList.size() - 1);
        assertThat(testGooseLevel.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testGooseLevel.getLimitedTime()).isEqualTo(UPDATED_LIMITED_TIME);
        assertThat(testGooseLevel.getNbMaxAdByPlay()).isEqualTo(UPDATED_NB_MAX_AD_BY_PLAY);

        // Validate the GooseLevel in Elasticsearch
        GooseLevel gooseLevelEs = gooseLevelSearchRepository.findOne(testGooseLevel.getId());
        assertThat(gooseLevelEs).isEqualToIgnoringGivenFields(testGooseLevel);
    }

    @Test
    @Transactional
    public void updateNonExistingGooseLevel() throws Exception {
        int databaseSizeBeforeUpdate = gooseLevelRepository.findAll().size();

        // Create the GooseLevel
        GooseLevelDTO gooseLevelDTO = gooseLevelMapper.toDto(gooseLevel);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGooseLevelMockMvc.perform(put("/api/goose-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gooseLevelDTO)))
            .andExpect(status().isCreated());

        // Validate the GooseLevel in the database
        List<GooseLevel> gooseLevelList = gooseLevelRepository.findAll();
        assertThat(gooseLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGooseLevel() throws Exception {
        // Initialize the database
        gooseLevelRepository.saveAndFlush(gooseLevel);
        gooseLevelSearchRepository.save(gooseLevel);
        int databaseSizeBeforeDelete = gooseLevelRepository.findAll().size();

        // Get the gooseLevel
        restGooseLevelMockMvc.perform(delete("/api/goose-levels/{id}", gooseLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean gooseLevelExistsInEs = gooseLevelSearchRepository.exists(gooseLevel.getId());
        assertThat(gooseLevelExistsInEs).isFalse();

        // Validate the database is empty
        List<GooseLevel> gooseLevelList = gooseLevelRepository.findAll();
        assertThat(gooseLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGooseLevel() throws Exception {
        // Initialize the database
        gooseLevelRepository.saveAndFlush(gooseLevel);
        gooseLevelSearchRepository.save(gooseLevel);

        // Search the gooseLevel
        restGooseLevelMockMvc.perform(get("/api/_search/goose-levels?query=id:" + gooseLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gooseLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].limitedTime").value(hasItem(DEFAULT_LIMITED_TIME.intValue())))
            .andExpect(jsonPath("$.[*].nbMaxAdByPlay").value(hasItem(DEFAULT_NB_MAX_AD_BY_PLAY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseLevel.class);
        GooseLevel gooseLevel1 = new GooseLevel();
        gooseLevel1.setId(1L);
        GooseLevel gooseLevel2 = new GooseLevel();
        gooseLevel2.setId(gooseLevel1.getId());
        assertThat(gooseLevel1).isEqualTo(gooseLevel2);
        gooseLevel2.setId(2L);
        assertThat(gooseLevel1).isNotEqualTo(gooseLevel2);
        gooseLevel1.setId(null);
        assertThat(gooseLevel1).isNotEqualTo(gooseLevel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GooseLevelDTO.class);
        GooseLevelDTO gooseLevelDTO1 = new GooseLevelDTO();
        gooseLevelDTO1.setId(1L);
        GooseLevelDTO gooseLevelDTO2 = new GooseLevelDTO();
        assertThat(gooseLevelDTO1).isNotEqualTo(gooseLevelDTO2);
        gooseLevelDTO2.setId(gooseLevelDTO1.getId());
        assertThat(gooseLevelDTO1).isEqualTo(gooseLevelDTO2);
        gooseLevelDTO2.setId(2L);
        assertThat(gooseLevelDTO1).isNotEqualTo(gooseLevelDTO2);
        gooseLevelDTO1.setId(null);
        assertThat(gooseLevelDTO1).isNotEqualTo(gooseLevelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(gooseLevelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(gooseLevelMapper.fromId(null)).isNull();
    }
}
