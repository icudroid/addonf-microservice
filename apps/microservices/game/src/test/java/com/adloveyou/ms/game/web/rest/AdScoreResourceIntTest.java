package com.adloveyou.ms.game.web.rest;

import com.adloveyou.ms.game.GameApp;

import com.adloveyou.ms.game.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.game.domain.AdScore;
import com.adloveyou.ms.game.repository.AdScoreRepository;
import com.adloveyou.ms.game.service.AdScoreService;
import com.adloveyou.ms.game.repository.search.AdScoreSearchRepository;
import com.adloveyou.ms.game.service.dto.AdScoreDTO;
import com.adloveyou.ms.game.service.mapper.AdScoreMapper;
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
 * Test class for the AdScoreResource REST controller.
 *
 * @see AdScoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameApp.class, SecurityBeanOverrideConfiguration.class})
public class AdScoreResourceIntTest {

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    @Autowired
    private AdScoreRepository adScoreRepository;

    @Autowired
    private AdScoreMapper adScoreMapper;

    @Autowired
    private AdScoreService adScoreService;

    @Autowired
    private AdScoreSearchRepository adScoreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdScoreMockMvc;

    private AdScore adScore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdScoreResource adScoreResource = new AdScoreResource(adScoreService);
        this.restAdScoreMockMvc = MockMvcBuilders.standaloneSetup(adScoreResource)
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
    public static AdScore createEntity(EntityManager em) {
        AdScore adScore = new AdScore()
            .score(DEFAULT_SCORE);
        return adScore;
    }

    @Before
    public void initTest() {
        adScoreSearchRepository.deleteAll();
        adScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdScore() throws Exception {
        int databaseSizeBeforeCreate = adScoreRepository.findAll().size();

        // Create the AdScore
        AdScoreDTO adScoreDTO = adScoreMapper.toDto(adScore);
        restAdScoreMockMvc.perform(post("/api/ad-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adScoreDTO)))
            .andExpect(status().isCreated());

        // Validate the AdScore in the database
        List<AdScore> adScoreList = adScoreRepository.findAll();
        assertThat(adScoreList).hasSize(databaseSizeBeforeCreate + 1);
        AdScore testAdScore = adScoreList.get(adScoreList.size() - 1);
        assertThat(testAdScore.getScore()).isEqualTo(DEFAULT_SCORE);

        // Validate the AdScore in Elasticsearch
        AdScore adScoreEs = adScoreSearchRepository.findOne(testAdScore.getId());
        assertThat(adScoreEs).isEqualToIgnoringGivenFields(testAdScore);
    }

    @Test
    @Transactional
    public void createAdScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adScoreRepository.findAll().size();

        // Create the AdScore with an existing ID
        adScore.setId(1L);
        AdScoreDTO adScoreDTO = adScoreMapper.toDto(adScore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdScoreMockMvc.perform(post("/api/ad-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdScore in the database
        List<AdScore> adScoreList = adScoreRepository.findAll();
        assertThat(adScoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdScores() throws Exception {
        // Initialize the database
        adScoreRepository.saveAndFlush(adScore);

        // Get all the adScoreList
        restAdScoreMockMvc.perform(get("/api/ad-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    @Test
    @Transactional
    public void getAdScore() throws Exception {
        // Initialize the database
        adScoreRepository.saveAndFlush(adScore);

        // Get the adScore
        restAdScoreMockMvc.perform(get("/api/ad-scores/{id}", adScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adScore.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingAdScore() throws Exception {
        // Get the adScore
        restAdScoreMockMvc.perform(get("/api/ad-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdScore() throws Exception {
        // Initialize the database
        adScoreRepository.saveAndFlush(adScore);
        adScoreSearchRepository.save(adScore);
        int databaseSizeBeforeUpdate = adScoreRepository.findAll().size();

        // Update the adScore
        AdScore updatedAdScore = adScoreRepository.findOne(adScore.getId());
        // Disconnect from session so that the updates on updatedAdScore are not directly saved in db
        em.detach(updatedAdScore);
        updatedAdScore
            .score(UPDATED_SCORE);
        AdScoreDTO adScoreDTO = adScoreMapper.toDto(updatedAdScore);

        restAdScoreMockMvc.perform(put("/api/ad-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adScoreDTO)))
            .andExpect(status().isOk());

        // Validate the AdScore in the database
        List<AdScore> adScoreList = adScoreRepository.findAll();
        assertThat(adScoreList).hasSize(databaseSizeBeforeUpdate);
        AdScore testAdScore = adScoreList.get(adScoreList.size() - 1);
        assertThat(testAdScore.getScore()).isEqualTo(UPDATED_SCORE);

        // Validate the AdScore in Elasticsearch
        AdScore adScoreEs = adScoreSearchRepository.findOne(testAdScore.getId());
        assertThat(adScoreEs).isEqualToIgnoringGivenFields(testAdScore);
    }

    @Test
    @Transactional
    public void updateNonExistingAdScore() throws Exception {
        int databaseSizeBeforeUpdate = adScoreRepository.findAll().size();

        // Create the AdScore
        AdScoreDTO adScoreDTO = adScoreMapper.toDto(adScore);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdScoreMockMvc.perform(put("/api/ad-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adScoreDTO)))
            .andExpect(status().isCreated());

        // Validate the AdScore in the database
        List<AdScore> adScoreList = adScoreRepository.findAll();
        assertThat(adScoreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdScore() throws Exception {
        // Initialize the database
        adScoreRepository.saveAndFlush(adScore);
        adScoreSearchRepository.save(adScore);
        int databaseSizeBeforeDelete = adScoreRepository.findAll().size();

        // Get the adScore
        restAdScoreMockMvc.perform(delete("/api/ad-scores/{id}", adScore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adScoreExistsInEs = adScoreSearchRepository.exists(adScore.getId());
        assertThat(adScoreExistsInEs).isFalse();

        // Validate the database is empty
        List<AdScore> adScoreList = adScoreRepository.findAll();
        assertThat(adScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdScore() throws Exception {
        // Initialize the database
        adScoreRepository.saveAndFlush(adScore);
        adScoreSearchRepository.save(adScore);

        // Search the adScore
        restAdScoreMockMvc.perform(get("/api/_search/ad-scores?query=id:" + adScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdScore.class);
        AdScore adScore1 = new AdScore();
        adScore1.setId(1L);
        AdScore adScore2 = new AdScore();
        adScore2.setId(adScore1.getId());
        assertThat(adScore1).isEqualTo(adScore2);
        adScore2.setId(2L);
        assertThat(adScore1).isNotEqualTo(adScore2);
        adScore1.setId(null);
        assertThat(adScore1).isNotEqualTo(adScore2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdScoreDTO.class);
        AdScoreDTO adScoreDTO1 = new AdScoreDTO();
        adScoreDTO1.setId(1L);
        AdScoreDTO adScoreDTO2 = new AdScoreDTO();
        assertThat(adScoreDTO1).isNotEqualTo(adScoreDTO2);
        adScoreDTO2.setId(adScoreDTO1.getId());
        assertThat(adScoreDTO1).isEqualTo(adScoreDTO2);
        adScoreDTO2.setId(2L);
        assertThat(adScoreDTO1).isNotEqualTo(adScoreDTO2);
        adScoreDTO1.setId(null);
        assertThat(adScoreDTO1).isNotEqualTo(adScoreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adScoreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adScoreMapper.fromId(null)).isNull();
    }
}
