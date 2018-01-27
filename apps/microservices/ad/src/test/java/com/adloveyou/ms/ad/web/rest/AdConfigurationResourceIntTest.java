package com.adloveyou.ms.ad.web.rest;

import com.adloveyou.ms.ad.AdApp;

import com.adloveyou.ms.ad.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.ad.domain.AdConfiguration;
import com.adloveyou.ms.ad.repository.AdConfigurationRepository;
import com.adloveyou.ms.ad.service.AdConfigurationService;
import com.adloveyou.ms.ad.repository.search.AdConfigurationSearchRepository;
import com.adloveyou.ms.ad.service.dto.AdConfigurationDTO;
import com.adloveyou.ms.ad.service.mapper.AdConfigurationMapper;
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
 * Test class for the AdConfigurationResource REST controller.
 *
 * @see AdConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AdConfigurationResourceIntTest {

    @Autowired
    private AdConfigurationRepository adConfigurationRepository;

    @Autowired
    private AdConfigurationMapper adConfigurationMapper;

    @Autowired
    private AdConfigurationService adConfigurationService;

    @Autowired
    private AdConfigurationSearchRepository adConfigurationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdConfigurationMockMvc;

    private AdConfiguration adConfiguration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdConfigurationResource adConfigurationResource = new AdConfigurationResource(adConfigurationService);
        this.restAdConfigurationMockMvc = MockMvcBuilders.standaloneSetup(adConfigurationResource)
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
    public static AdConfiguration createEntity(EntityManager em) {
        AdConfiguration adConfiguration = new AdConfiguration();
        return adConfiguration;
    }

    @Before
    public void initTest() {
        adConfigurationSearchRepository.deleteAll();
        adConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdConfiguration() throws Exception {
        int databaseSizeBeforeCreate = adConfigurationRepository.findAll().size();

        // Create the AdConfiguration
        AdConfigurationDTO adConfigurationDTO = adConfigurationMapper.toDto(adConfiguration);
        restAdConfigurationMockMvc.perform(post("/api/ad-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the AdConfiguration in the database
        List<AdConfiguration> adConfigurationList = adConfigurationRepository.findAll();
        assertThat(adConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        AdConfiguration testAdConfiguration = adConfigurationList.get(adConfigurationList.size() - 1);

        // Validate the AdConfiguration in Elasticsearch
        AdConfiguration adConfigurationEs = adConfigurationSearchRepository.findOne(testAdConfiguration.getId());
        assertThat(adConfigurationEs).isEqualToIgnoringGivenFields(testAdConfiguration);
    }

    @Test
    @Transactional
    public void createAdConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adConfigurationRepository.findAll().size();

        // Create the AdConfiguration with an existing ID
        adConfiguration.setId(1L);
        AdConfigurationDTO adConfigurationDTO = adConfigurationMapper.toDto(adConfiguration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdConfigurationMockMvc.perform(post("/api/ad-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdConfiguration in the database
        List<AdConfiguration> adConfigurationList = adConfigurationRepository.findAll();
        assertThat(adConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdConfigurations() throws Exception {
        // Initialize the database
        adConfigurationRepository.saveAndFlush(adConfiguration);

        // Get all the adConfigurationList
        restAdConfigurationMockMvc.perform(get("/api/ad-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adConfiguration.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAdConfiguration() throws Exception {
        // Initialize the database
        adConfigurationRepository.saveAndFlush(adConfiguration);

        // Get the adConfiguration
        restAdConfigurationMockMvc.perform(get("/api/ad-configurations/{id}", adConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adConfiguration.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdConfiguration() throws Exception {
        // Get the adConfiguration
        restAdConfigurationMockMvc.perform(get("/api/ad-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdConfiguration() throws Exception {
        // Initialize the database
        adConfigurationRepository.saveAndFlush(adConfiguration);
        adConfigurationSearchRepository.save(adConfiguration);
        int databaseSizeBeforeUpdate = adConfigurationRepository.findAll().size();

        // Update the adConfiguration
        AdConfiguration updatedAdConfiguration = adConfigurationRepository.findOne(adConfiguration.getId());
        // Disconnect from session so that the updates on updatedAdConfiguration are not directly saved in db
        em.detach(updatedAdConfiguration);
        AdConfigurationDTO adConfigurationDTO = adConfigurationMapper.toDto(updatedAdConfiguration);

        restAdConfigurationMockMvc.perform(put("/api/ad-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adConfigurationDTO)))
            .andExpect(status().isOk());

        // Validate the AdConfiguration in the database
        List<AdConfiguration> adConfigurationList = adConfigurationRepository.findAll();
        assertThat(adConfigurationList).hasSize(databaseSizeBeforeUpdate);
        AdConfiguration testAdConfiguration = adConfigurationList.get(adConfigurationList.size() - 1);

        // Validate the AdConfiguration in Elasticsearch
        AdConfiguration adConfigurationEs = adConfigurationSearchRepository.findOne(testAdConfiguration.getId());
        assertThat(adConfigurationEs).isEqualToIgnoringGivenFields(testAdConfiguration);
    }

    @Test
    @Transactional
    public void updateNonExistingAdConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = adConfigurationRepository.findAll().size();

        // Create the AdConfiguration
        AdConfigurationDTO adConfigurationDTO = adConfigurationMapper.toDto(adConfiguration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdConfigurationMockMvc.perform(put("/api/ad-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adConfigurationDTO)))
            .andExpect(status().isCreated());

        // Validate the AdConfiguration in the database
        List<AdConfiguration> adConfigurationList = adConfigurationRepository.findAll();
        assertThat(adConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdConfiguration() throws Exception {
        // Initialize the database
        adConfigurationRepository.saveAndFlush(adConfiguration);
        adConfigurationSearchRepository.save(adConfiguration);
        int databaseSizeBeforeDelete = adConfigurationRepository.findAll().size();

        // Get the adConfiguration
        restAdConfigurationMockMvc.perform(delete("/api/ad-configurations/{id}", adConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adConfigurationExistsInEs = adConfigurationSearchRepository.exists(adConfiguration.getId());
        assertThat(adConfigurationExistsInEs).isFalse();

        // Validate the database is empty
        List<AdConfiguration> adConfigurationList = adConfigurationRepository.findAll();
        assertThat(adConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdConfiguration() throws Exception {
        // Initialize the database
        adConfigurationRepository.saveAndFlush(adConfiguration);
        adConfigurationSearchRepository.save(adConfiguration);

        // Search the adConfiguration
        restAdConfigurationMockMvc.perform(get("/api/_search/ad-configurations?query=id:" + adConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adConfiguration.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdConfiguration.class);
        AdConfiguration adConfiguration1 = new AdConfiguration();
        adConfiguration1.setId(1L);
        AdConfiguration adConfiguration2 = new AdConfiguration();
        adConfiguration2.setId(adConfiguration1.getId());
        assertThat(adConfiguration1).isEqualTo(adConfiguration2);
        adConfiguration2.setId(2L);
        assertThat(adConfiguration1).isNotEqualTo(adConfiguration2);
        adConfiguration1.setId(null);
        assertThat(adConfiguration1).isNotEqualTo(adConfiguration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdConfigurationDTO.class);
        AdConfigurationDTO adConfigurationDTO1 = new AdConfigurationDTO();
        adConfigurationDTO1.setId(1L);
        AdConfigurationDTO adConfigurationDTO2 = new AdConfigurationDTO();
        assertThat(adConfigurationDTO1).isNotEqualTo(adConfigurationDTO2);
        adConfigurationDTO2.setId(adConfigurationDTO1.getId());
        assertThat(adConfigurationDTO1).isEqualTo(adConfigurationDTO2);
        adConfigurationDTO2.setId(2L);
        assertThat(adConfigurationDTO1).isNotEqualTo(adConfigurationDTO2);
        adConfigurationDTO1.setId(null);
        assertThat(adConfigurationDTO1).isNotEqualTo(adConfigurationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adConfigurationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adConfigurationMapper.fromId(null)).isNull();
    }
}
