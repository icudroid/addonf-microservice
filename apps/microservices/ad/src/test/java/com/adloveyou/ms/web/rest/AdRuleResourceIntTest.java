package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.AdRule;
import com.adloveyou.ms.repository.AdRuleRepository;
import com.adloveyou.ms.service.AdRuleService;
import com.adloveyou.ms.repository.search.AdRuleSearchRepository;
import com.adloveyou.ms.service.dto.AdRuleDTO;
import com.adloveyou.ms.service.mapper.AdRuleMapper;
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
 * Test class for the AdRuleResource REST controller.
 *
 * @see AdRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AdRuleResourceIntTest {

    @Autowired
    private AdRuleRepository adRuleRepository;

    @Autowired
    private AdRuleMapper adRuleMapper;

    @Autowired
    private AdRuleService adRuleService;

    @Autowired
    private AdRuleSearchRepository adRuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdRuleMockMvc;

    private AdRule adRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdRuleResource adRuleResource = new AdRuleResource(adRuleService);
        this.restAdRuleMockMvc = MockMvcBuilders.standaloneSetup(adRuleResource)
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
    public static AdRule createEntity(EntityManager em) {
        AdRule adRule = new AdRule();
        return adRule;
    }

    @Before
    public void initTest() {
        adRuleSearchRepository.deleteAll();
        adRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdRule() throws Exception {
        int databaseSizeBeforeCreate = adRuleRepository.findAll().size();

        // Create the AdRule
        AdRuleDTO adRuleDTO = adRuleMapper.toDto(adRule);
        restAdRuleMockMvc.perform(post("/api/ad-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the AdRule in the database
        List<AdRule> adRuleList = adRuleRepository.findAll();
        assertThat(adRuleList).hasSize(databaseSizeBeforeCreate + 1);
        AdRule testAdRule = adRuleList.get(adRuleList.size() - 1);

        // Validate the AdRule in Elasticsearch
        AdRule adRuleEs = adRuleSearchRepository.findOne(testAdRule.getId());
        assertThat(adRuleEs).isEqualToIgnoringGivenFields(testAdRule);
    }

    @Test
    @Transactional
    public void createAdRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adRuleRepository.findAll().size();

        // Create the AdRule with an existing ID
        adRule.setId(1L);
        AdRuleDTO adRuleDTO = adRuleMapper.toDto(adRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdRuleMockMvc.perform(post("/api/ad-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdRule in the database
        List<AdRule> adRuleList = adRuleRepository.findAll();
        assertThat(adRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdRules() throws Exception {
        // Initialize the database
        adRuleRepository.saveAndFlush(adRule);

        // Get all the adRuleList
        restAdRuleMockMvc.perform(get("/api/ad-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adRule.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAdRule() throws Exception {
        // Initialize the database
        adRuleRepository.saveAndFlush(adRule);

        // Get the adRule
        restAdRuleMockMvc.perform(get("/api/ad-rules/{id}", adRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adRule.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAdRule() throws Exception {
        // Get the adRule
        restAdRuleMockMvc.perform(get("/api/ad-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdRule() throws Exception {
        // Initialize the database
        adRuleRepository.saveAndFlush(adRule);
        adRuleSearchRepository.save(adRule);
        int databaseSizeBeforeUpdate = adRuleRepository.findAll().size();

        // Update the adRule
        AdRule updatedAdRule = adRuleRepository.findOne(adRule.getId());
        // Disconnect from session so that the updates on updatedAdRule are not directly saved in db
        em.detach(updatedAdRule);
        AdRuleDTO adRuleDTO = adRuleMapper.toDto(updatedAdRule);

        restAdRuleMockMvc.perform(put("/api/ad-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRuleDTO)))
            .andExpect(status().isOk());

        // Validate the AdRule in the database
        List<AdRule> adRuleList = adRuleRepository.findAll();
        assertThat(adRuleList).hasSize(databaseSizeBeforeUpdate);
        AdRule testAdRule = adRuleList.get(adRuleList.size() - 1);

        // Validate the AdRule in Elasticsearch
        AdRule adRuleEs = adRuleSearchRepository.findOne(testAdRule.getId());
        assertThat(adRuleEs).isEqualToIgnoringGivenFields(testAdRule);
    }

    @Test
    @Transactional
    public void updateNonExistingAdRule() throws Exception {
        int databaseSizeBeforeUpdate = adRuleRepository.findAll().size();

        // Create the AdRule
        AdRuleDTO adRuleDTO = adRuleMapper.toDto(adRule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdRuleMockMvc.perform(put("/api/ad-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the AdRule in the database
        List<AdRule> adRuleList = adRuleRepository.findAll();
        assertThat(adRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAdRule() throws Exception {
        // Initialize the database
        adRuleRepository.saveAndFlush(adRule);
        adRuleSearchRepository.save(adRule);
        int databaseSizeBeforeDelete = adRuleRepository.findAll().size();

        // Get the adRule
        restAdRuleMockMvc.perform(delete("/api/ad-rules/{id}", adRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean adRuleExistsInEs = adRuleSearchRepository.exists(adRule.getId());
        assertThat(adRuleExistsInEs).isFalse();

        // Validate the database is empty
        List<AdRule> adRuleList = adRuleRepository.findAll();
        assertThat(adRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAdRule() throws Exception {
        // Initialize the database
        adRuleRepository.saveAndFlush(adRule);
        adRuleSearchRepository.save(adRule);

        // Search the adRule
        restAdRuleMockMvc.perform(get("/api/_search/ad-rules?query=id:" + adRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adRule.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdRule.class);
        AdRule adRule1 = new AdRule();
        adRule1.setId(1L);
        AdRule adRule2 = new AdRule();
        adRule2.setId(adRule1.getId());
        assertThat(adRule1).isEqualTo(adRule2);
        adRule2.setId(2L);
        assertThat(adRule1).isNotEqualTo(adRule2);
        adRule1.setId(null);
        assertThat(adRule1).isNotEqualTo(adRule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdRuleDTO.class);
        AdRuleDTO adRuleDTO1 = new AdRuleDTO();
        adRuleDTO1.setId(1L);
        AdRuleDTO adRuleDTO2 = new AdRuleDTO();
        assertThat(adRuleDTO1).isNotEqualTo(adRuleDTO2);
        adRuleDTO2.setId(adRuleDTO1.getId());
        assertThat(adRuleDTO1).isEqualTo(adRuleDTO2);
        adRuleDTO2.setId(2L);
        assertThat(adRuleDTO1).isNotEqualTo(adRuleDTO2);
        adRuleDTO1.setId(null);
        assertThat(adRuleDTO1).isNotEqualTo(adRuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(adRuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(adRuleMapper.fromId(null)).isNull();
    }
}
