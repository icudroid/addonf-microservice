package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.AdCampaing;
import com.adloveyou.ms.repository.AdCampaingRepository;
import com.adloveyou.ms.service.AdCampaingService;
import com.adloveyou.ms.repository.search.AdCampaingSearchRepository;
import com.adloveyou.ms.service.dto.AdCampaingDTO;
import com.adloveyou.ms.service.mapper.AdCampaingMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.sameInstant;
import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AdCampaingResource REST controller.
 *
 * @see AdCampaingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AdCampaingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_INITIAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INITIAL_AMOUNT = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AdCampaingRepository adCampaingRepository;

    @Autowired
    private AdCampaingMapper adCampaingMapper;

    @Autowired
    private AdCampaingService adCampaingService;

    @Autowired
    private AdCampaingSearchRepository adCampaingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdCampaingMockMvc;

    private AdCampaing adCampaing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdCampaingResource adCampaingResource = new AdCampaingResource(adCampaingService);
        this.restAdCampaingMockMvc = MockMvcBuilders.standaloneSetup(adCampaingResource)
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
    public static AdCampaing createEntity(EntityManager em) {
        AdCampaing adCampaing = new AdCampaing()
            .name(DEFAULT_NAME)
            .initialAmount(DEFAULT_INITIAL_AMOUNT)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return adCampaing;
    }

    @Before
    public void initTest() {
        adCampaingSearchRepository.deleteAll();
        adCampaing = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdCampaing() throws Exception {
        int databaseSizeBeforeCreate = adCampaingRepository.findAll().size();

        // Create the AdCampaing
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);
        restAdCampaingMockMvc.perform(post("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isCreated());

        // Validate the AdCampaing in the database
        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeCreate + 1);
        AdCampaing testAdCampaing = adCampaingList.get(adCampaingList.size() - 1);
        assertThat(testAdCampaing.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdCampaing.getInitialAmount()).isEqualTo(DEFAULT_INITIAL_AMOUNT);
        assertThat(testAdCampaing.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testAdCampaing.getEnd()).isEqualTo(DEFAULT_END);

        // Validate the AdCampaing in Elasticsearch
        AdCampaing adCampaingEs = adCampaingSearchRepository.findOne(testAdCampaing.getId());
        assertThat(testAdCampaing.getStart()).isEqualTo(testAdCampaing.getStart());
        assertThat(testAdCampaing.getEnd()).isEqualTo(testAdCampaing.getEnd());
        assertThat(adCampaingEs).isEqualToIgnoringGivenFields(testAdCampaing, "start", "end");
    }

    @Test
    @Transactional
    public void createAdCampaingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adCampaingRepository.findAll().size();

        // Create the AdCampaing with an existing ID
        adCampaing.setId(1L);
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdCampaingMockMvc.perform(post("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdCampaing in the database
        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCampaingRepository.findAll().size();
        // set the field null
        adCampaing.setName(null);

        // Create the AdCampaing, which fails.
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);

        restAdCampaingMockMvc.perform(post("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isBadRequest());

        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInitialAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCampaingRepository.findAll().size();
        // set the field null
        adCampaing.setInitialAmount(null);

        // Create the AdCampaing, which fails.
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);

        restAdCampaingMockMvc.perform(post("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isBadRequest());

        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCampaingRepository.findAll().size();
        // set the field null
        adCampaing.setStart(null);

        // Create the AdCampaing, which fails.
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);

        restAdCampaingMockMvc.perform(post("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isBadRequest());

        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = adCampaingRepository.findAll().size();
        // set the field null
        adCampaing.setEnd(null);

        // Create the AdCampaing, which fails.
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);

        restAdCampaingMockMvc.perform(post("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isBadRequest());

        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdCampaings() throws Exception {
        // Initialize the database
        adCampaingRepository.saveAndFlush(adCampaing);

        // Get all the adCampaingList
        restAdCampaingMockMvc.perform(get("/api/ad-campaings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adCampaing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].initialAmount").value(hasItem(DEFAULT_INITIAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));
    }

    @Test
    @Transactional
    public void getAdCampaing() throws Exception {
        // Initialize the database
        adCampaingRepository.saveAndFlush(adCampaing);

        // Get the adCampaing
        restAdCampaingMockMvc.perform(get("/api/ad-campaings/{id}", adCampaing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adCampaing.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.initialAmount").value(DEFAULT_INITIAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.end").value(sameInstant(DEFAULT_END)));
    }

    @Test
    @Transactional
    public void getNonExistingAdCampaing() throws Exception {
        // Get the adCampaing
        restAdCampaingMockMvc.perform(get("/api/ad-campaings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdCampaing() throws Exception {
        // Initialize the database
        adCampaingRepository.saveAndFlush(adCampaing);
        adCampaingSearchRepository.save(adCampaing);
        int databaseSizeBeforeUpdate = adCampaingRepository.findAll().size();

        // Update the adCampaing
        AdCampaing updatedAdCampaing = adCampaingRepository.findOne(adCampaing.getId());
        // Disconnect from session so that the updates on updatedAdCampaing are not directly saved in db
        em.detach(updatedAdCampaing);
        updatedAdCampaing
            .name(UPDATED_NAME)
            .initialAmount(UPDATED_INITIAL_AMOUNT)
            .start(UPDATED_START)
            .end(UPDATED_END);
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(updatedAdCampaing);

        restAdCampaingMockMvc.perform(put("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isOk());

        // Validate the AdCampaing in the database
        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeUpdate);
        AdCampaing testAdCampaing = adCampaingList.get(adCampaingList.size() - 1);
        assertThat(testAdCampaing.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdCampaing.getInitialAmount()).isEqualTo(UPDATED_INITIAL_AMOUNT);
        assertThat(testAdCampaing.getStart()).isEqualTo(UPDATED_START);
        assertThat(testAdCampaing.getEnd()).isEqualTo(UPDATED_END);

        // Validate the AdCampaing in Elasticsearch
        AdCampaing adCampaingEs = adCampaingSearchRepository.findOne(testAdCampaing.getId());
        assertThat(testAdCampaing.getStart()).isEqualTo(testAdCampaing.getStart());
        assertThat(testAdCampaing.getEnd()).isEqualTo(testAdCampaing.getEnd());
        assertThat(adCampaingEs).isEqualToIgnoringGivenFields(testAdCampaing, "start", "end");
    }

    @Test
    @Transactional
    public void updateNonExistingAdCampaing() throws Exception {
        int databaseSizeBeforeUpdate = adCampaingRepository.findAll().size();

        // Create the AdCampaing
        AdCampaingDTO adCampaingDTO = adCampaingMapper.toDto(adCampaing);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdCampaingMockMvc.perform(put("/api/ad-campaings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adCampaingDTO)))
            .andExpect(status().isCreated());

        // Validate the AdCampaing in the database
        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdCampaing() throws Exception {
        // Initialize the database
        adCampaingRepository.saveAndFlush(adCampaing);
        adCampaingSearchRepository.save(adCampaing);
        int databaseSizeBeforeDelete = adCampaingRepository.findAll().size();

        // Get the adCampaing
        restAdCampaingMockMvc.perform(delete("/api/ad-campaings/{id}", adCampaing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adCampaingExistsInEs = adCampaingSearchRepository.exists(adCampaing.getId());
        assertThat(adCampaingExistsInEs).isFalse();

        // Validate the database is empty
        List<AdCampaing> adCampaingList = adCampaingRepository.findAll();
        assertThat(adCampaingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdCampaing() throws Exception {
        // Initialize the database
        adCampaingRepository.saveAndFlush(adCampaing);
        adCampaingSearchRepository.save(adCampaing);

        // Search the adCampaing
        restAdCampaingMockMvc.perform(get("/api/_search/ad-campaings?query=id:" + adCampaing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adCampaing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].initialAmount").value(hasItem(DEFAULT_INITIAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdCampaing.class);
        AdCampaing adCampaing1 = new AdCampaing();
        adCampaing1.setId(1L);
        AdCampaing adCampaing2 = new AdCampaing();
        adCampaing2.setId(adCampaing1.getId());
        assertThat(adCampaing1).isEqualTo(adCampaing2);
        adCampaing2.setId(2L);
        assertThat(adCampaing1).isNotEqualTo(adCampaing2);
        adCampaing1.setId(null);
        assertThat(adCampaing1).isNotEqualTo(adCampaing2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdCampaingDTO.class);
        AdCampaingDTO adCampaingDTO1 = new AdCampaingDTO();
        adCampaingDTO1.setId(1L);
        AdCampaingDTO adCampaingDTO2 = new AdCampaingDTO();
        assertThat(adCampaingDTO1).isNotEqualTo(adCampaingDTO2);
        adCampaingDTO2.setId(adCampaingDTO1.getId());
        assertThat(adCampaingDTO1).isEqualTo(adCampaingDTO2);
        adCampaingDTO2.setId(2L);
        assertThat(adCampaingDTO1).isNotEqualTo(adCampaingDTO2);
        adCampaingDTO1.setId(null);
        assertThat(adCampaingDTO1).isNotEqualTo(adCampaingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adCampaingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adCampaingMapper.fromId(null)).isNull();
    }
}
