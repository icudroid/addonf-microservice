package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.Agency;
import com.adloveyou.ms.repository.AgencyRepository;
import com.adloveyou.ms.service.AgencyService;
import com.adloveyou.ms.repository.search.AgencySearchRepository;
import com.adloveyou.ms.service.dto.AgencyDTO;
import com.adloveyou.ms.service.mapper.AgencyMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adloveyou.ms.domain.enumeration.LegalStatus;
/**
 * Test class for the AgencyResource REST controller.
 *
 * @see AgencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AgencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SIRET = "AAAAAAAAAA";
    private static final String UPDATED_SIRET = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final LegalStatus DEFAULT_LEGAL_STATUS = LegalStatus.SARL;
    private static final LegalStatus UPDATED_LEGAL_STATUS = LegalStatus.SA;

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private AgencyMapper agencyMapper;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AgencySearchRepository agencySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgencyMockMvc;

    private Agency agency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgencyResource agencyResource = new AgencyResource(agencyService);
        this.restAgencyMockMvc = MockMvcBuilders.standaloneSetup(agencyResource)
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
    public static Agency createEntity(EntityManager em) {
        Agency agency = new Agency()
            .name(DEFAULT_NAME)
            .siret(DEFAULT_SIRET)
            .siren(DEFAULT_SIREN)
            .legalStatus(DEFAULT_LEGAL_STATUS)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return agency;
    }

    @Before
    public void initTest() {
        agencySearchRepository.deleteAll();
        agency = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgency() throws Exception {
        int databaseSizeBeforeCreate = agencyRepository.findAll().size();

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);
        restAgencyMockMvc.perform(post("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeCreate + 1);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgency.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testAgency.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testAgency.getLegalStatus()).isEqualTo(DEFAULT_LEGAL_STATUS);
        assertThat(testAgency.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testAgency.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);

        // Validate the Agency in Elasticsearch
        Agency agencyEs = agencySearchRepository.findOne(testAgency.getId());
        assertThat(agencyEs).isEqualToIgnoringGivenFields(testAgency);
    }

    @Test
    @Transactional
    public void createAgencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agencyRepository.findAll().size();

        // Create the Agency with an existing ID
        agency.setId(1L);
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencyMockMvc.perform(post("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAgencies() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList
        restAgencyMockMvc.perform(get("/api/agencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN.toString())))
            .andExpect(jsonPath("$.[*].legalStatus").value(hasItem(DEFAULT_LEGAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void getAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get the agency
        restAgencyMockMvc.perform(get("/api/agencies/{id}", agency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET.toString()))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN.toString()))
            .andExpect(jsonPath("$.legalStatus").value(DEFAULT_LEGAL_STATUS.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingAgency() throws Exception {
        // Get the agency
        restAgencyMockMvc.perform(get("/api/agencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);
        agencySearchRepository.save(agency);
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Update the agency
        Agency updatedAgency = agencyRepository.findOne(agency.getId());
        // Disconnect from session so that the updates on updatedAgency are not directly saved in db
        em.detach(updatedAgency);
        updatedAgency
            .name(UPDATED_NAME)
            .siret(UPDATED_SIRET)
            .siren(UPDATED_SIREN)
            .legalStatus(UPDATED_LEGAL_STATUS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        AgencyDTO agencyDTO = agencyMapper.toDto(updatedAgency);

        restAgencyMockMvc.perform(put("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isOk());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgency.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testAgency.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testAgency.getLegalStatus()).isEqualTo(UPDATED_LEGAL_STATUS);
        assertThat(testAgency.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testAgency.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);

        // Validate the Agency in Elasticsearch
        Agency agencyEs = agencySearchRepository.findOne(testAgency.getId());
        assertThat(agencyEs).isEqualToIgnoringGivenFields(testAgency);
    }

    @Test
    @Transactional
    public void updateNonExistingAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgencyMockMvc.perform(put("/api/agencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);
        agencySearchRepository.save(agency);
        int databaseSizeBeforeDelete = agencyRepository.findAll().size();

        // Get the agency
        restAgencyMockMvc.perform(delete("/api/agencies/{id}", agency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean agencyExistsInEs = agencySearchRepository.exists(agency.getId());
        assertThat(agencyExistsInEs).isFalse();

        // Validate the database is empty
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);
        agencySearchRepository.save(agency);

        // Search the agency
        restAgencyMockMvc.perform(get("/api/_search/agencies?query=id:" + agency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN.toString())))
            .andExpect(jsonPath("$.[*].legalStatus").value(hasItem(DEFAULT_LEGAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agency.class);
        Agency agency1 = new Agency();
        agency1.setId(1L);
        Agency agency2 = new Agency();
        agency2.setId(agency1.getId());
        assertThat(agency1).isEqualTo(agency2);
        agency2.setId(2L);
        assertThat(agency1).isNotEqualTo(agency2);
        agency1.setId(null);
        assertThat(agency1).isNotEqualTo(agency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencyDTO.class);
        AgencyDTO agencyDTO1 = new AgencyDTO();
        agencyDTO1.setId(1L);
        AgencyDTO agencyDTO2 = new AgencyDTO();
        assertThat(agencyDTO1).isNotEqualTo(agencyDTO2);
        agencyDTO2.setId(agencyDTO1.getId());
        assertThat(agencyDTO1).isEqualTo(agencyDTO2);
        agencyDTO2.setId(2L);
        assertThat(agencyDTO1).isNotEqualTo(agencyDTO2);
        agencyDTO1.setId(null);
        assertThat(agencyDTO1).isNotEqualTo(agencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agencyMapper.fromId(null)).isNull();
    }
}
