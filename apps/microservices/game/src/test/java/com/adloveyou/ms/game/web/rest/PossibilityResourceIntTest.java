package com.adloveyou.ms.game.web.rest;

import com.adloveyou.ms.game.GameApp;

import com.adloveyou.ms.game.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.game.domain.Possibility;
import com.adloveyou.ms.game.repository.PossibilityRepository;
import com.adloveyou.ms.game.service.PossibilityService;
import com.adloveyou.ms.game.repository.search.PossibilitySearchRepository;
import com.adloveyou.ms.game.service.dto.PossibilityDTO;
import com.adloveyou.ms.game.service.mapper.PossibilityMapper;
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
 * Test class for the PossibilityResource REST controller.
 *
 * @see PossibilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GameApp.class, SecurityBeanOverrideConfiguration.class})
public class PossibilityResourceIntTest {

    private static final Long DEFAULT_AD_ID = 1L;
    private static final Long UPDATED_AD_ID = 2L;

    @Autowired
    private PossibilityRepository possibilityRepository;

    @Autowired
    private PossibilityMapper possibilityMapper;

    @Autowired
    private PossibilityService possibilityService;

    @Autowired
    private PossibilitySearchRepository possibilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPossibilityMockMvc;

    private Possibility possibility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PossibilityResource possibilityResource = new PossibilityResource(possibilityService);
        this.restPossibilityMockMvc = MockMvcBuilders.standaloneSetup(possibilityResource)
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
    public static Possibility createEntity(EntityManager em) {
        Possibility possibility = new Possibility()
            .adId(DEFAULT_AD_ID);
        return possibility;
    }

    @Before
    public void initTest() {
        possibilitySearchRepository.deleteAll();
        possibility = createEntity(em);
    }

    @Test
    @Transactional
    public void createPossibility() throws Exception {
        int databaseSizeBeforeCreate = possibilityRepository.findAll().size();

        // Create the Possibility
        PossibilityDTO possibilityDTO = possibilityMapper.toDto(possibility);
        restPossibilityMockMvc.perform(post("/api/possibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Possibility in the database
        List<Possibility> possibilityList = possibilityRepository.findAll();
        assertThat(possibilityList).hasSize(databaseSizeBeforeCreate + 1);
        Possibility testPossibility = possibilityList.get(possibilityList.size() - 1);
        assertThat(testPossibility.getAdId()).isEqualTo(DEFAULT_AD_ID);

        // Validate the Possibility in Elasticsearch
        Possibility possibilityEs = possibilitySearchRepository.findOne(testPossibility.getId());
        assertThat(possibilityEs).isEqualToIgnoringGivenFields(testPossibility);
    }

    @Test
    @Transactional
    public void createPossibilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = possibilityRepository.findAll().size();

        // Create the Possibility with an existing ID
        possibility.setId(1L);
        PossibilityDTO possibilityDTO = possibilityMapper.toDto(possibility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPossibilityMockMvc.perform(post("/api/possibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Possibility in the database
        List<Possibility> possibilityList = possibilityRepository.findAll();
        assertThat(possibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPossibilities() throws Exception {
        // Initialize the database
        possibilityRepository.saveAndFlush(possibility);

        // Get all the possibilityList
        restPossibilityMockMvc.perform(get("/api/possibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(possibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].adId").value(hasItem(DEFAULT_AD_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPossibility() throws Exception {
        // Initialize the database
        possibilityRepository.saveAndFlush(possibility);

        // Get the possibility
        restPossibilityMockMvc.perform(get("/api/possibilities/{id}", possibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(possibility.getId().intValue()))
            .andExpect(jsonPath("$.adId").value(DEFAULT_AD_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPossibility() throws Exception {
        // Get the possibility
        restPossibilityMockMvc.perform(get("/api/possibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePossibility() throws Exception {
        // Initialize the database
        possibilityRepository.saveAndFlush(possibility);
        possibilitySearchRepository.save(possibility);
        int databaseSizeBeforeUpdate = possibilityRepository.findAll().size();

        // Update the possibility
        Possibility updatedPossibility = possibilityRepository.findOne(possibility.getId());
        // Disconnect from session so that the updates on updatedPossibility are not directly saved in db
        em.detach(updatedPossibility);
        updatedPossibility
            .adId(UPDATED_AD_ID);
        PossibilityDTO possibilityDTO = possibilityMapper.toDto(updatedPossibility);

        restPossibilityMockMvc.perform(put("/api/possibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilityDTO)))
            .andExpect(status().isOk());

        // Validate the Possibility in the database
        List<Possibility> possibilityList = possibilityRepository.findAll();
        assertThat(possibilityList).hasSize(databaseSizeBeforeUpdate);
        Possibility testPossibility = possibilityList.get(possibilityList.size() - 1);
        assertThat(testPossibility.getAdId()).isEqualTo(UPDATED_AD_ID);

        // Validate the Possibility in Elasticsearch
        Possibility possibilityEs = possibilitySearchRepository.findOne(testPossibility.getId());
        assertThat(possibilityEs).isEqualToIgnoringGivenFields(testPossibility);
    }

    @Test
    @Transactional
    public void updateNonExistingPossibility() throws Exception {
        int databaseSizeBeforeUpdate = possibilityRepository.findAll().size();

        // Create the Possibility
        PossibilityDTO possibilityDTO = possibilityMapper.toDto(possibility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPossibilityMockMvc.perform(put("/api/possibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Possibility in the database
        List<Possibility> possibilityList = possibilityRepository.findAll();
        assertThat(possibilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePossibility() throws Exception {
        // Initialize the database
        possibilityRepository.saveAndFlush(possibility);
        possibilitySearchRepository.save(possibility);
        int databaseSizeBeforeDelete = possibilityRepository.findAll().size();

        // Get the possibility
        restPossibilityMockMvc.perform(delete("/api/possibilities/{id}", possibility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean possibilityExistsInEs = possibilitySearchRepository.exists(possibility.getId());
        assertThat(possibilityExistsInEs).isFalse();

        // Validate the database is empty
        List<Possibility> possibilityList = possibilityRepository.findAll();
        assertThat(possibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPossibility() throws Exception {
        // Initialize the database
        possibilityRepository.saveAndFlush(possibility);
        possibilitySearchRepository.save(possibility);

        // Search the possibility
        restPossibilityMockMvc.perform(get("/api/_search/possibilities?query=id:" + possibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(possibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].adId").value(hasItem(DEFAULT_AD_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Possibility.class);
        Possibility possibility1 = new Possibility();
        possibility1.setId(1L);
        Possibility possibility2 = new Possibility();
        possibility2.setId(possibility1.getId());
        assertThat(possibility1).isEqualTo(possibility2);
        possibility2.setId(2L);
        assertThat(possibility1).isNotEqualTo(possibility2);
        possibility1.setId(null);
        assertThat(possibility1).isNotEqualTo(possibility2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PossibilityDTO.class);
        PossibilityDTO possibilityDTO1 = new PossibilityDTO();
        possibilityDTO1.setId(1L);
        PossibilityDTO possibilityDTO2 = new PossibilityDTO();
        assertThat(possibilityDTO1).isNotEqualTo(possibilityDTO2);
        possibilityDTO2.setId(possibilityDTO1.getId());
        assertThat(possibilityDTO1).isEqualTo(possibilityDTO2);
        possibilityDTO2.setId(2L);
        assertThat(possibilityDTO1).isNotEqualTo(possibilityDTO2);
        possibilityDTO1.setId(null);
        assertThat(possibilityDTO1).isNotEqualTo(possibilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(possibilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(possibilityMapper.fromId(null)).isNull();
    }
}
