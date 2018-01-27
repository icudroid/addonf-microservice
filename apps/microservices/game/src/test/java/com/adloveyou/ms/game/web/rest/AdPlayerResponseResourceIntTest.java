package com.adloveyou.ms.game.web.rest;

import com.adloveyou.ms.game.GameApp;

import com.adloveyou.ms.game.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.game.domain.AdPlayerResponse;
import com.adloveyou.ms.game.repository.AdPlayerResponseRepository;
import com.adloveyou.ms.game.service.AdPlayerResponseService;
import com.adloveyou.ms.game.repository.search.AdPlayerResponseSearchRepository;
import com.adloveyou.ms.game.service.dto.AdPlayerResponseDTO;
import com.adloveyou.ms.game.service.mapper.AdPlayerResponseMapper;
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
 * Test class for the AdPlayerResponseResource REST controller.
 *
 * @see AdPlayerResponseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameApp.class, SecurityBeanOverrideConfiguration.class})
public class AdPlayerResponseResourceIntTest {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Boolean DEFAULT_CORRECT_ANSWER = false;
    private static final Boolean UPDATED_CORRECT_ANSWER = true;

    private static final Boolean DEFAULT_PLAYED = false;
    private static final Boolean UPDATED_PLAYED = true;

    @Autowired
    private AdPlayerResponseRepository adPlayerResponseRepository;

    @Autowired
    private AdPlayerResponseMapper adPlayerResponseMapper;

    @Autowired
    private AdPlayerResponseService adPlayerResponseService;

    @Autowired
    private AdPlayerResponseSearchRepository adPlayerResponseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdPlayerResponseMockMvc;

    private AdPlayerResponse adPlayerResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdPlayerResponseResource adPlayerResponseResource = new AdPlayerResponseResource(adPlayerResponseService);
        this.restAdPlayerResponseMockMvc = MockMvcBuilders.standaloneSetup(adPlayerResponseResource)
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
    public static AdPlayerResponse createEntity(EntityManager em) {
        AdPlayerResponse adPlayerResponse = new AdPlayerResponse()
            .number(DEFAULT_NUMBER)
            .correctAnswer(DEFAULT_CORRECT_ANSWER)
            .played(DEFAULT_PLAYED);
        return adPlayerResponse;
    }

    @Before
    public void initTest() {
        adPlayerResponseSearchRepository.deleteAll();
        adPlayerResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdPlayerResponse() throws Exception {
        int databaseSizeBeforeCreate = adPlayerResponseRepository.findAll().size();

        // Create the AdPlayerResponse
        AdPlayerResponseDTO adPlayerResponseDTO = adPlayerResponseMapper.toDto(adPlayerResponse);
        restAdPlayerResponseMockMvc.perform(post("/api/ad-player-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adPlayerResponseDTO)))
            .andExpect(status().isCreated());

        // Validate the AdPlayerResponse in the database
        List<AdPlayerResponse> adPlayerResponseList = adPlayerResponseRepository.findAll();
        assertThat(adPlayerResponseList).hasSize(databaseSizeBeforeCreate + 1);
        AdPlayerResponse testAdPlayerResponse = adPlayerResponseList.get(adPlayerResponseList.size() - 1);
        assertThat(testAdPlayerResponse.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAdPlayerResponse.isCorrectAnswer()).isEqualTo(DEFAULT_CORRECT_ANSWER);
        assertThat(testAdPlayerResponse.isPlayed()).isEqualTo(DEFAULT_PLAYED);

        // Validate the AdPlayerResponse in Elasticsearch
        AdPlayerResponse adPlayerResponseEs = adPlayerResponseSearchRepository.findOne(testAdPlayerResponse.getId());
        assertThat(adPlayerResponseEs).isEqualToIgnoringGivenFields(testAdPlayerResponse);
    }

    @Test
    @Transactional
    public void createAdPlayerResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adPlayerResponseRepository.findAll().size();

        // Create the AdPlayerResponse with an existing ID
        adPlayerResponse.setId(1L);
        AdPlayerResponseDTO adPlayerResponseDTO = adPlayerResponseMapper.toDto(adPlayerResponse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdPlayerResponseMockMvc.perform(post("/api/ad-player-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adPlayerResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdPlayerResponse in the database
        List<AdPlayerResponse> adPlayerResponseList = adPlayerResponseRepository.findAll();
        assertThat(adPlayerResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdPlayerResponses() throws Exception {
        // Initialize the database
        adPlayerResponseRepository.saveAndFlush(adPlayerResponse);

        // Get all the adPlayerResponseList
        restAdPlayerResponseMockMvc.perform(get("/api/ad-player-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adPlayerResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER.booleanValue())))
            .andExpect(jsonPath("$.[*].played").value(hasItem(DEFAULT_PLAYED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAdPlayerResponse() throws Exception {
        // Initialize the database
        adPlayerResponseRepository.saveAndFlush(adPlayerResponse);

        // Get the adPlayerResponse
        restAdPlayerResponseMockMvc.perform(get("/api/ad-player-responses/{id}", adPlayerResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adPlayerResponse.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.correctAnswer").value(DEFAULT_CORRECT_ANSWER.booleanValue()))
            .andExpect(jsonPath("$.played").value(DEFAULT_PLAYED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdPlayerResponse() throws Exception {
        // Get the adPlayerResponse
        restAdPlayerResponseMockMvc.perform(get("/api/ad-player-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdPlayerResponse() throws Exception {
        // Initialize the database
        adPlayerResponseRepository.saveAndFlush(adPlayerResponse);
        adPlayerResponseSearchRepository.save(adPlayerResponse);
        int databaseSizeBeforeUpdate = adPlayerResponseRepository.findAll().size();

        // Update the adPlayerResponse
        AdPlayerResponse updatedAdPlayerResponse = adPlayerResponseRepository.findOne(adPlayerResponse.getId());
        // Disconnect from session so that the updates on updatedAdPlayerResponse are not directly saved in db
        em.detach(updatedAdPlayerResponse);
        updatedAdPlayerResponse
            .number(UPDATED_NUMBER)
            .correctAnswer(UPDATED_CORRECT_ANSWER)
            .played(UPDATED_PLAYED);
        AdPlayerResponseDTO adPlayerResponseDTO = adPlayerResponseMapper.toDto(updatedAdPlayerResponse);

        restAdPlayerResponseMockMvc.perform(put("/api/ad-player-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adPlayerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AdPlayerResponse in the database
        List<AdPlayerResponse> adPlayerResponseList = adPlayerResponseRepository.findAll();
        assertThat(adPlayerResponseList).hasSize(databaseSizeBeforeUpdate);
        AdPlayerResponse testAdPlayerResponse = adPlayerResponseList.get(adPlayerResponseList.size() - 1);
        assertThat(testAdPlayerResponse.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAdPlayerResponse.isCorrectAnswer()).isEqualTo(UPDATED_CORRECT_ANSWER);
        assertThat(testAdPlayerResponse.isPlayed()).isEqualTo(UPDATED_PLAYED);

        // Validate the AdPlayerResponse in Elasticsearch
        AdPlayerResponse adPlayerResponseEs = adPlayerResponseSearchRepository.findOne(testAdPlayerResponse.getId());
        assertThat(adPlayerResponseEs).isEqualToIgnoringGivenFields(testAdPlayerResponse);
    }

    @Test
    @Transactional
    public void updateNonExistingAdPlayerResponse() throws Exception {
        int databaseSizeBeforeUpdate = adPlayerResponseRepository.findAll().size();

        // Create the AdPlayerResponse
        AdPlayerResponseDTO adPlayerResponseDTO = adPlayerResponseMapper.toDto(adPlayerResponse);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdPlayerResponseMockMvc.perform(put("/api/ad-player-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adPlayerResponseDTO)))
            .andExpect(status().isCreated());

        // Validate the AdPlayerResponse in the database
        List<AdPlayerResponse> adPlayerResponseList = adPlayerResponseRepository.findAll();
        assertThat(adPlayerResponseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdPlayerResponse() throws Exception {
        // Initialize the database
        adPlayerResponseRepository.saveAndFlush(adPlayerResponse);
        adPlayerResponseSearchRepository.save(adPlayerResponse);
        int databaseSizeBeforeDelete = adPlayerResponseRepository.findAll().size();

        // Get the adPlayerResponse
        restAdPlayerResponseMockMvc.perform(delete("/api/ad-player-responses/{id}", adPlayerResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adPlayerResponseExistsInEs = adPlayerResponseSearchRepository.exists(adPlayerResponse.getId());
        assertThat(adPlayerResponseExistsInEs).isFalse();

        // Validate the database is empty
        List<AdPlayerResponse> adPlayerResponseList = adPlayerResponseRepository.findAll();
        assertThat(adPlayerResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdPlayerResponse() throws Exception {
        // Initialize the database
        adPlayerResponseRepository.saveAndFlush(adPlayerResponse);
        adPlayerResponseSearchRepository.save(adPlayerResponse);

        // Search the adPlayerResponse
        restAdPlayerResponseMockMvc.perform(get("/api/_search/ad-player-responses?query=id:" + adPlayerResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adPlayerResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].correctAnswer").value(hasItem(DEFAULT_CORRECT_ANSWER.booleanValue())))
            .andExpect(jsonPath("$.[*].played").value(hasItem(DEFAULT_PLAYED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdPlayerResponse.class);
        AdPlayerResponse adPlayerResponse1 = new AdPlayerResponse();
        adPlayerResponse1.setId(1L);
        AdPlayerResponse adPlayerResponse2 = new AdPlayerResponse();
        adPlayerResponse2.setId(adPlayerResponse1.getId());
        assertThat(adPlayerResponse1).isEqualTo(adPlayerResponse2);
        adPlayerResponse2.setId(2L);
        assertThat(adPlayerResponse1).isNotEqualTo(adPlayerResponse2);
        adPlayerResponse1.setId(null);
        assertThat(adPlayerResponse1).isNotEqualTo(adPlayerResponse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdPlayerResponseDTO.class);
        AdPlayerResponseDTO adPlayerResponseDTO1 = new AdPlayerResponseDTO();
        adPlayerResponseDTO1.setId(1L);
        AdPlayerResponseDTO adPlayerResponseDTO2 = new AdPlayerResponseDTO();
        assertThat(adPlayerResponseDTO1).isNotEqualTo(adPlayerResponseDTO2);
        adPlayerResponseDTO2.setId(adPlayerResponseDTO1.getId());
        assertThat(adPlayerResponseDTO1).isEqualTo(adPlayerResponseDTO2);
        adPlayerResponseDTO2.setId(2L);
        assertThat(adPlayerResponseDTO1).isNotEqualTo(adPlayerResponseDTO2);
        adPlayerResponseDTO1.setId(null);
        assertThat(adPlayerResponseDTO1).isNotEqualTo(adPlayerResponseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adPlayerResponseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adPlayerResponseMapper.fromId(null)).isNull();
    }
}
