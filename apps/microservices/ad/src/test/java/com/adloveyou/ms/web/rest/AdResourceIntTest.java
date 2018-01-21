package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.ad.Ad;
import com.adloveyou.ms.repository.AdRepository;
import com.adloveyou.ms.service.AdService;
import com.adloveyou.ms.repository.search.AdSearchRepository;
import com.adloveyou.ms.service.dto.AdDTO;
import com.adloveyou.ms.service.mapper.AdMapper;
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

import com.adloveyou.ms.domain.enumeration.AdStatus;
/**
 * Test class for the AdResource REST controller.
 *
 * @see AdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AdResourceIntTest {

    private static final Long DEFAULT_DURATION = 1L;
    private static final Long UPDATED_DURATION = 2L;

    private static final AdStatus DEFAULT_STATUS = AdStatus.UPLOADING;
    private static final AdStatus UPDATED_STATUS = AdStatus.UPLOADED;

    private static final Long DEFAULT_ADFILE_ID = 1L;
    private static final Long UPDATED_ADFILE_ID = 2L;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdMapper adMapper;

    @Autowired
    private AdService adService;

    @Autowired
    private AdSearchRepository adSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdMockMvc;

    private Ad ad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdResource adResource = new AdResource(adService);
        this.restAdMockMvc = MockMvcBuilders.standaloneSetup(adResource)
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
    public static Ad createEntity(EntityManager em) {
        Ad ad = new Ad()
            .duration(DEFAULT_DURATION)
            .status(DEFAULT_STATUS)
            .adfileId(DEFAULT_ADFILE_ID);
        return ad;
    }

    @Before
    public void initTest() {
        adSearchRepository.deleteAll();
        ad = createEntity(em);
    }

    @Test
    @Transactional
    public void createAd() throws Exception {
        int databaseSizeBeforeCreate = adRepository.findAll().size();

        // Create the Ad
        AdDTO adDTO = adMapper.toDto(ad);
        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isCreated());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeCreate + 1);
        Ad testAd = adList.get(adList.size() - 1);
        assertThat(testAd.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testAd.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAd.getAdfileId()).isEqualTo(DEFAULT_ADFILE_ID);

        // Validate the Ad in Elasticsearch
        Ad adEs = adSearchRepository.findOne(testAd.getId());
        assertThat(adEs).isEqualToIgnoringGivenFields(testAd);
    }

    @Test
    @Transactional
    public void createAdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adRepository.findAll().size();

        // Create the Ad with an existing ID
        ad.setId(1L);
        AdDTO adDTO = adMapper.toDto(ad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = adRepository.findAll().size();
        // set the field null
        ad.setDuration(null);

        // Create the Ad, which fails.
        AdDTO adDTO = adMapper.toDto(ad);

        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isBadRequest());

        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = adRepository.findAll().size();
        // set the field null
        ad.setStatus(null);

        // Create the Ad, which fails.
        AdDTO adDTO = adMapper.toDto(ad);

        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isBadRequest());

        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdfileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adRepository.findAll().size();
        // set the field null
        ad.setAdfileId(null);

        // Create the Ad, which fails.
        AdDTO adDTO = adMapper.toDto(ad);

        restAdMockMvc.perform(post("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isBadRequest());

        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAds() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get all the adList
        restAdMockMvc.perform(get("/api/ads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ad.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].adfileId").value(hasItem(DEFAULT_ADFILE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);

        // Get the ad
        restAdMockMvc.perform(get("/api/ads/{id}", ad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ad.getId().intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.adfileId").value(DEFAULT_ADFILE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAd() throws Exception {
        // Get the ad
        restAdMockMvc.perform(get("/api/ads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);
        adSearchRepository.save(ad);
        int databaseSizeBeforeUpdate = adRepository.findAll().size();

        // Update the ad
        Ad updatedAd = adRepository.findOne(ad.getId());
        // Disconnect from session so that the updates on updatedAd are not directly saved in db
        em.detach(updatedAd);
        updatedAd
            .duration(UPDATED_DURATION)
            .status(UPDATED_STATUS)
            .adfileId(UPDATED_ADFILE_ID);
        AdDTO adDTO = adMapper.toDto(updatedAd);

        restAdMockMvc.perform(put("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isOk());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeUpdate);
        Ad testAd = adList.get(adList.size() - 1);
        assertThat(testAd.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testAd.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAd.getAdfileId()).isEqualTo(UPDATED_ADFILE_ID);

        // Validate the Ad in Elasticsearch
        Ad adEs = adSearchRepository.findOne(testAd.getId());
        assertThat(adEs).isEqualToIgnoringGivenFields(testAd);
    }

    @Test
    @Transactional
    public void updateNonExistingAd() throws Exception {
        int databaseSizeBeforeUpdate = adRepository.findAll().size();

        // Create the Ad
        AdDTO adDTO = adMapper.toDto(ad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdMockMvc.perform(put("/api/ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adDTO)))
            .andExpect(status().isCreated());

        // Validate the Ad in the database
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);
        adSearchRepository.save(ad);
        int databaseSizeBeforeDelete = adRepository.findAll().size();

        // Get the ad
        restAdMockMvc.perform(delete("/api/ads/{id}", ad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adExistsInEs = adSearchRepository.exists(ad.getId());
        assertThat(adExistsInEs).isFalse();

        // Validate the database is empty
        List<Ad> adList = adRepository.findAll();
        assertThat(adList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAd() throws Exception {
        // Initialize the database
        adRepository.saveAndFlush(ad);
        adSearchRepository.save(ad);

        // Search the ad
        restAdMockMvc.perform(get("/api/_search/ads?query=id:" + ad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ad.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].adfileId").value(hasItem(DEFAULT_ADFILE_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ad.class);
        Ad ad1 = new Ad();
        ad1.setId(1L);
        Ad ad2 = new Ad();
        ad2.setId(ad1.getId());
        assertThat(ad1).isEqualTo(ad2);
        ad2.setId(2L);
        assertThat(ad1).isNotEqualTo(ad2);
        ad1.setId(null);
        assertThat(ad1).isNotEqualTo(ad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdDTO.class);
        AdDTO adDTO1 = new AdDTO();
        adDTO1.setId(1L);
        AdDTO adDTO2 = new AdDTO();
        assertThat(adDTO1).isNotEqualTo(adDTO2);
        adDTO2.setId(adDTO1.getId());
        assertThat(adDTO1).isEqualTo(adDTO2);
        adDTO2.setId(2L);
        assertThat(adDTO1).isNotEqualTo(adDTO2);
        adDTO1.setId(null);
        assertThat(adDTO1).isNotEqualTo(adDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adMapper.fromId(null)).isNull();
    }
}
