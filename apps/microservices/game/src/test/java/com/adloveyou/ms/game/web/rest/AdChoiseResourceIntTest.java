package com.adloveyou.ms.game.web.rest;

import com.adloveyou.ms.game.GameApp;

import com.adloveyou.ms.game.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.game.domain.AdChoise;
import com.adloveyou.ms.game.repository.AdChoiseRepository;
import com.adloveyou.ms.game.service.AdChoiseService;
import com.adloveyou.ms.game.repository.search.AdChoiseSearchRepository;
import com.adloveyou.ms.game.service.dto.AdChoiseDTO;
import com.adloveyou.ms.game.service.mapper.AdChoiseMapper;
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
 * Test class for the AdChoiseResource REST controller.
 *
 * @see AdChoiseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameApp.class, SecurityBeanOverrideConfiguration.class})
public class AdChoiseResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Long DEFAULT_BID_ID = 1L;
    private static final Long UPDATED_BID_ID = 2L;

    private static final Long DEFAULT_AD_CONFIG_ID = 1L;
    private static final Long UPDATED_AD_CONFIG_ID = 2L;

    @Autowired
    private AdChoiseRepository adChoiseRepository;

    @Autowired
    private AdChoiseMapper adChoiseMapper;

    @Autowired
    private AdChoiseService adChoiseService;

    @Autowired
    private AdChoiseSearchRepository adChoiseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdChoiseMockMvc;

    private AdChoise adChoise;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdChoiseResource adChoiseResource = new AdChoiseResource(adChoiseService);
        this.restAdChoiseMockMvc = MockMvcBuilders.standaloneSetup(adChoiseResource)
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
    public static AdChoise createEntity(EntityManager em) {
        AdChoise adChoise = new AdChoise()
            .number(DEFAULT_NUMBER)
            .question(DEFAULT_QUESTION)
            .bidId(DEFAULT_BID_ID)
            .adConfigId(DEFAULT_AD_CONFIG_ID);
        return adChoise;
    }

    @Before
    public void initTest() {
        adChoiseSearchRepository.deleteAll();
        adChoise = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdChoise() throws Exception {
        int databaseSizeBeforeCreate = adChoiseRepository.findAll().size();

        // Create the AdChoise
        AdChoiseDTO adChoiseDTO = adChoiseMapper.toDto(adChoise);
        restAdChoiseMockMvc.perform(post("/api/ad-choises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adChoiseDTO)))
            .andExpect(status().isCreated());

        // Validate the AdChoise in the database
        List<AdChoise> adChoiseList = adChoiseRepository.findAll();
        assertThat(adChoiseList).hasSize(databaseSizeBeforeCreate + 1);
        AdChoise testAdChoise = adChoiseList.get(adChoiseList.size() - 1);
        assertThat(testAdChoise.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAdChoise.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testAdChoise.getBidId()).isEqualTo(DEFAULT_BID_ID);
        assertThat(testAdChoise.getAdConfigId()).isEqualTo(DEFAULT_AD_CONFIG_ID);

        // Validate the AdChoise in Elasticsearch
        AdChoise adChoiseEs = adChoiseSearchRepository.findOne(testAdChoise.getId());
        assertThat(adChoiseEs).isEqualToIgnoringGivenFields(testAdChoise);
    }

    @Test
    @Transactional
    public void createAdChoiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adChoiseRepository.findAll().size();

        // Create the AdChoise with an existing ID
        adChoise.setId(1L);
        AdChoiseDTO adChoiseDTO = adChoiseMapper.toDto(adChoise);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdChoiseMockMvc.perform(post("/api/ad-choises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adChoiseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdChoise in the database
        List<AdChoise> adChoiseList = adChoiseRepository.findAll();
        assertThat(adChoiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdChoises() throws Exception {
        // Initialize the database
        adChoiseRepository.saveAndFlush(adChoise);

        // Get all the adChoiseList
        restAdChoiseMockMvc.perform(get("/api/ad-choises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adChoise.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].bidId").value(hasItem(DEFAULT_BID_ID.intValue())))
            .andExpect(jsonPath("$.[*].adConfigId").value(hasItem(DEFAULT_AD_CONFIG_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAdChoise() throws Exception {
        // Initialize the database
        adChoiseRepository.saveAndFlush(adChoise);

        // Get the adChoise
        restAdChoiseMockMvc.perform(get("/api/ad-choises/{id}", adChoise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adChoise.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.bidId").value(DEFAULT_BID_ID.intValue()))
            .andExpect(jsonPath("$.adConfigId").value(DEFAULT_AD_CONFIG_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdChoise() throws Exception {
        // Get the adChoise
        restAdChoiseMockMvc.perform(get("/api/ad-choises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdChoise() throws Exception {
        // Initialize the database
        adChoiseRepository.saveAndFlush(adChoise);
        adChoiseSearchRepository.save(adChoise);
        int databaseSizeBeforeUpdate = adChoiseRepository.findAll().size();

        // Update the adChoise
        AdChoise updatedAdChoise = adChoiseRepository.findOne(adChoise.getId());
        // Disconnect from session so that the updates on updatedAdChoise are not directly saved in db
        em.detach(updatedAdChoise);
        updatedAdChoise
            .number(UPDATED_NUMBER)
            .question(UPDATED_QUESTION)
            .bidId(UPDATED_BID_ID)
            .adConfigId(UPDATED_AD_CONFIG_ID);
        AdChoiseDTO adChoiseDTO = adChoiseMapper.toDto(updatedAdChoise);

        restAdChoiseMockMvc.perform(put("/api/ad-choises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adChoiseDTO)))
            .andExpect(status().isOk());

        // Validate the AdChoise in the database
        List<AdChoise> adChoiseList = adChoiseRepository.findAll();
        assertThat(adChoiseList).hasSize(databaseSizeBeforeUpdate);
        AdChoise testAdChoise = adChoiseList.get(adChoiseList.size() - 1);
        assertThat(testAdChoise.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAdChoise.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAdChoise.getBidId()).isEqualTo(UPDATED_BID_ID);
        assertThat(testAdChoise.getAdConfigId()).isEqualTo(UPDATED_AD_CONFIG_ID);

        // Validate the AdChoise in Elasticsearch
        AdChoise adChoiseEs = adChoiseSearchRepository.findOne(testAdChoise.getId());
        assertThat(adChoiseEs).isEqualToIgnoringGivenFields(testAdChoise);
    }

    @Test
    @Transactional
    public void updateNonExistingAdChoise() throws Exception {
        int databaseSizeBeforeUpdate = adChoiseRepository.findAll().size();

        // Create the AdChoise
        AdChoiseDTO adChoiseDTO = adChoiseMapper.toDto(adChoise);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdChoiseMockMvc.perform(put("/api/ad-choises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adChoiseDTO)))
            .andExpect(status().isCreated());

        // Validate the AdChoise in the database
        List<AdChoise> adChoiseList = adChoiseRepository.findAll();
        assertThat(adChoiseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdChoise() throws Exception {
        // Initialize the database
        adChoiseRepository.saveAndFlush(adChoise);
        adChoiseSearchRepository.save(adChoise);
        int databaseSizeBeforeDelete = adChoiseRepository.findAll().size();

        // Get the adChoise
        restAdChoiseMockMvc.perform(delete("/api/ad-choises/{id}", adChoise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adChoiseExistsInEs = adChoiseSearchRepository.exists(adChoise.getId());
        assertThat(adChoiseExistsInEs).isFalse();

        // Validate the database is empty
        List<AdChoise> adChoiseList = adChoiseRepository.findAll();
        assertThat(adChoiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdChoise() throws Exception {
        // Initialize the database
        adChoiseRepository.saveAndFlush(adChoise);
        adChoiseSearchRepository.save(adChoise);

        // Search the adChoise
        restAdChoiseMockMvc.perform(get("/api/_search/ad-choises?query=id:" + adChoise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adChoise.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].bidId").value(hasItem(DEFAULT_BID_ID.intValue())))
            .andExpect(jsonPath("$.[*].adConfigId").value(hasItem(DEFAULT_AD_CONFIG_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdChoise.class);
        AdChoise adChoise1 = new AdChoise();
        adChoise1.setId(1L);
        AdChoise adChoise2 = new AdChoise();
        adChoise2.setId(adChoise1.getId());
        assertThat(adChoise1).isEqualTo(adChoise2);
        adChoise2.setId(2L);
        assertThat(adChoise1).isNotEqualTo(adChoise2);
        adChoise1.setId(null);
        assertThat(adChoise1).isNotEqualTo(adChoise2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdChoiseDTO.class);
        AdChoiseDTO adChoiseDTO1 = new AdChoiseDTO();
        adChoiseDTO1.setId(1L);
        AdChoiseDTO adChoiseDTO2 = new AdChoiseDTO();
        assertThat(adChoiseDTO1).isNotEqualTo(adChoiseDTO2);
        adChoiseDTO2.setId(adChoiseDTO1.getId());
        assertThat(adChoiseDTO1).isEqualTo(adChoiseDTO2);
        adChoiseDTO2.setId(2L);
        assertThat(adChoiseDTO1).isNotEqualTo(adChoiseDTO2);
        adChoiseDTO1.setId(null);
        assertThat(adChoiseDTO1).isNotEqualTo(adChoiseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adChoiseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adChoiseMapper.fromId(null)).isNull();
    }
}
