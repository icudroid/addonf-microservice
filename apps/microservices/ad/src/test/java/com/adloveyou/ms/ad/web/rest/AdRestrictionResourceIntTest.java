package com.adloveyou.ms.ad.web.rest;

import com.adloveyou.ms.ad.AdApp;

import com.adloveyou.ms.ad.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.ad.domain.AdRestriction;
import com.adloveyou.ms.ad.repository.AdRestrictionRepository;
import com.adloveyou.ms.ad.service.AdRestrictionService;
import com.adloveyou.ms.ad.repository.search.AdRestrictionSearchRepository;
import com.adloveyou.ms.ad.service.dto.AdRestrictionDTO;
import com.adloveyou.ms.ad.service.mapper.AdRestrictionMapper;
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
 * Test class for the AdRestrictionResource REST controller.
 *
 * @see AdRestrictionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AdRestrictionResourceIntTest {

    @Autowired
    private AdRestrictionRepository adRestrictionRepository;

    @Autowired
    private AdRestrictionMapper adRestrictionMapper;

    @Autowired
    private AdRestrictionService adRestrictionService;

    @Autowired
    private AdRestrictionSearchRepository adRestrictionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdRestrictionMockMvc;

    private AdRestriction adRestriction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdRestrictionResource adRestrictionResource = new AdRestrictionResource(adRestrictionService);
        this.restAdRestrictionMockMvc = MockMvcBuilders.standaloneSetup(adRestrictionResource)
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
    public static AdRestriction createEntity(EntityManager em) {
        AdRestriction adRestriction = new AdRestriction();
        return adRestriction;
    }

    @Before
    public void initTest() {
        adRestrictionSearchRepository.deleteAll();
        adRestriction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdRestriction() throws Exception {
        int databaseSizeBeforeCreate = adRestrictionRepository.findAll().size();

        // Create the AdRestriction
        AdRestrictionDTO adRestrictionDTO = adRestrictionMapper.toDto(adRestriction);
        restAdRestrictionMockMvc.perform(post("/api/ad-restrictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRestrictionDTO)))
            .andExpect(status().isCreated());

        // Validate the AdRestriction in the database
        List<AdRestriction> adRestrictionList = adRestrictionRepository.findAll();
        assertThat(adRestrictionList).hasSize(databaseSizeBeforeCreate + 1);
        AdRestriction testAdRestriction = adRestrictionList.get(adRestrictionList.size() - 1);

        // Validate the AdRestriction in Elasticsearch
        AdRestriction adRestrictionEs = adRestrictionSearchRepository.findOne(testAdRestriction.getId());
        assertThat(adRestrictionEs).isEqualToIgnoringGivenFields(testAdRestriction);
    }

    @Test
    @Transactional
    public void createAdRestrictionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adRestrictionRepository.findAll().size();

        // Create the AdRestriction with an existing ID
        adRestriction.setId(1L);
        AdRestrictionDTO adRestrictionDTO = adRestrictionMapper.toDto(adRestriction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdRestrictionMockMvc.perform(post("/api/ad-restrictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRestrictionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdRestriction in the database
        List<AdRestriction> adRestrictionList = adRestrictionRepository.findAll();
        assertThat(adRestrictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdRestrictions() throws Exception {
        // Initialize the database
        adRestrictionRepository.saveAndFlush(adRestriction);

        // Get all the adRestrictionList
        restAdRestrictionMockMvc.perform(get("/api/ad-restrictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adRestriction.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAdRestriction() throws Exception {
        // Initialize the database
        adRestrictionRepository.saveAndFlush(adRestriction);

        // Get the adRestriction
        restAdRestrictionMockMvc.perform(get("/api/ad-restrictions/{id}", adRestriction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adRestriction.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdRestriction() throws Exception {
        // Get the adRestriction
        restAdRestrictionMockMvc.perform(get("/api/ad-restrictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdRestriction() throws Exception {
        // Initialize the database
        adRestrictionRepository.saveAndFlush(adRestriction);
        adRestrictionSearchRepository.save(adRestriction);
        int databaseSizeBeforeUpdate = adRestrictionRepository.findAll().size();

        // Update the adRestriction
        AdRestriction updatedAdRestriction = adRestrictionRepository.findOne(adRestriction.getId());
        // Disconnect from session so that the updates on updatedAdRestriction are not directly saved in db
        em.detach(updatedAdRestriction);
        AdRestrictionDTO adRestrictionDTO = adRestrictionMapper.toDto(updatedAdRestriction);

        restAdRestrictionMockMvc.perform(put("/api/ad-restrictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRestrictionDTO)))
            .andExpect(status().isOk());

        // Validate the AdRestriction in the database
        List<AdRestriction> adRestrictionList = adRestrictionRepository.findAll();
        assertThat(adRestrictionList).hasSize(databaseSizeBeforeUpdate);
        AdRestriction testAdRestriction = adRestrictionList.get(adRestrictionList.size() - 1);

        // Validate the AdRestriction in Elasticsearch
        AdRestriction adRestrictionEs = adRestrictionSearchRepository.findOne(testAdRestriction.getId());
        assertThat(adRestrictionEs).isEqualToIgnoringGivenFields(testAdRestriction);
    }

    @Test
    @Transactional
    public void updateNonExistingAdRestriction() throws Exception {
        int databaseSizeBeforeUpdate = adRestrictionRepository.findAll().size();

        // Create the AdRestriction
        AdRestrictionDTO adRestrictionDTO = adRestrictionMapper.toDto(adRestriction);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdRestrictionMockMvc.perform(put("/api/ad-restrictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRestrictionDTO)))
            .andExpect(status().isCreated());

        // Validate the AdRestriction in the database
        List<AdRestriction> adRestrictionList = adRestrictionRepository.findAll();
        assertThat(adRestrictionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdRestriction() throws Exception {
        // Initialize the database
        adRestrictionRepository.saveAndFlush(adRestriction);
        adRestrictionSearchRepository.save(adRestriction);
        int databaseSizeBeforeDelete = adRestrictionRepository.findAll().size();

        // Get the adRestriction
        restAdRestrictionMockMvc.perform(delete("/api/ad-restrictions/{id}", adRestriction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adRestrictionExistsInEs = adRestrictionSearchRepository.exists(adRestriction.getId());
        assertThat(adRestrictionExistsInEs).isFalse();

        // Validate the database is empty
        List<AdRestriction> adRestrictionList = adRestrictionRepository.findAll();
        assertThat(adRestrictionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdRestriction() throws Exception {
        // Initialize the database
        adRestrictionRepository.saveAndFlush(adRestriction);
        adRestrictionSearchRepository.save(adRestriction);

        // Search the adRestriction
        restAdRestrictionMockMvc.perform(get("/api/_search/ad-restrictions?query=id:" + adRestriction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adRestriction.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdRestriction.class);
        AdRestriction adRestriction1 = new AdRestriction();
        adRestriction1.setId(1L);
        AdRestriction adRestriction2 = new AdRestriction();
        adRestriction2.setId(adRestriction1.getId());
        assertThat(adRestriction1).isEqualTo(adRestriction2);
        adRestriction2.setId(2L);
        assertThat(adRestriction1).isNotEqualTo(adRestriction2);
        adRestriction1.setId(null);
        assertThat(adRestriction1).isNotEqualTo(adRestriction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdRestrictionDTO.class);
        AdRestrictionDTO adRestrictionDTO1 = new AdRestrictionDTO();
        adRestrictionDTO1.setId(1L);
        AdRestrictionDTO adRestrictionDTO2 = new AdRestrictionDTO();
        assertThat(adRestrictionDTO1).isNotEqualTo(adRestrictionDTO2);
        adRestrictionDTO2.setId(adRestrictionDTO1.getId());
        assertThat(adRestrictionDTO1).isEqualTo(adRestrictionDTO2);
        adRestrictionDTO2.setId(2L);
        assertThat(adRestrictionDTO1).isNotEqualTo(adRestrictionDTO2);
        adRestrictionDTO1.setId(null);
        assertThat(adRestrictionDTO1).isNotEqualTo(adRestrictionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adRestrictionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adRestrictionMapper.fromId(null)).isNull();
    }
}
