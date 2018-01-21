package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.contact.MeanOfContact;
import com.adloveyou.ms.repository.MeanOfContactRepository;
import com.adloveyou.ms.service.MeanOfContactService;
import com.adloveyou.ms.repository.search.MeanOfContactSearchRepository;
import com.adloveyou.ms.service.dto.MeanOfContactDTO;
import com.adloveyou.ms.service.mapper.MeanOfContactMapper;
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

import com.adloveyou.ms.domain.enumeration.MeanOfContactType;
/**
 * Test class for the MeanOfContactResource REST controller.
 *
 * @see MeanOfContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class MeanOfContactResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final MeanOfContactType DEFAULT_TYPE = MeanOfContactType.EMAIL;
    private static final MeanOfContactType UPDATED_TYPE = MeanOfContactType.ADDRESS;

    @Autowired
    private MeanOfContactRepository meanOfContactRepository;

    @Autowired
    private MeanOfContactMapper meanOfContactMapper;

    @Autowired
    private MeanOfContactService meanOfContactService;

    @Autowired
    private MeanOfContactSearchRepository meanOfContactSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeanOfContactMockMvc;

    private MeanOfContact meanOfContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeanOfContactResource meanOfContactResource = new MeanOfContactResource(meanOfContactService);
        this.restMeanOfContactMockMvc = MockMvcBuilders.standaloneSetup(meanOfContactResource)
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
    public static MeanOfContact createEntity(EntityManager em) {
        MeanOfContact meanOfContact = new MeanOfContact()
            .value(DEFAULT_VALUE)
            .type(DEFAULT_TYPE);
        return meanOfContact;
    }

    @Before
    public void initTest() {
        meanOfContactSearchRepository.deleteAll();
        meanOfContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeanOfContact() throws Exception {
        int databaseSizeBeforeCreate = meanOfContactRepository.findAll().size();

        // Create the MeanOfContact
        MeanOfContactDTO meanOfContactDTO = meanOfContactMapper.toDto(meanOfContact);
        restMeanOfContactMockMvc.perform(post("/api/mean-of-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanOfContactDTO)))
            .andExpect(status().isCreated());

        // Validate the MeanOfContact in the database
        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeCreate + 1);
        MeanOfContact testMeanOfContact = meanOfContactList.get(meanOfContactList.size() - 1);
        assertThat(testMeanOfContact.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testMeanOfContact.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the MeanOfContact in Elasticsearch
        MeanOfContact meanOfContactEs = meanOfContactSearchRepository.findOne(testMeanOfContact.getId());
        assertThat(meanOfContactEs).isEqualToIgnoringGivenFields(testMeanOfContact);
    }

    @Test
    @Transactional
    public void createMeanOfContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meanOfContactRepository.findAll().size();

        // Create the MeanOfContact with an existing ID
        meanOfContact.setId(1L);
        MeanOfContactDTO meanOfContactDTO = meanOfContactMapper.toDto(meanOfContact);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeanOfContactMockMvc.perform(post("/api/mean-of-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanOfContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeanOfContact in the database
        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = meanOfContactRepository.findAll().size();
        // set the field null
        meanOfContact.setValue(null);

        // Create the MeanOfContact, which fails.
        MeanOfContactDTO meanOfContactDTO = meanOfContactMapper.toDto(meanOfContact);

        restMeanOfContactMockMvc.perform(post("/api/mean-of-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanOfContactDTO)))
            .andExpect(status().isBadRequest());

        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meanOfContactRepository.findAll().size();
        // set the field null
        meanOfContact.setType(null);

        // Create the MeanOfContact, which fails.
        MeanOfContactDTO meanOfContactDTO = meanOfContactMapper.toDto(meanOfContact);

        restMeanOfContactMockMvc.perform(post("/api/mean-of-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanOfContactDTO)))
            .andExpect(status().isBadRequest());

        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeanOfContacts() throws Exception {
        // Initialize the database
        meanOfContactRepository.saveAndFlush(meanOfContact);

        // Get all the meanOfContactList
        restMeanOfContactMockMvc.perform(get("/api/mean-of-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meanOfContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMeanOfContact() throws Exception {
        // Initialize the database
        meanOfContactRepository.saveAndFlush(meanOfContact);

        // Get the meanOfContact
        restMeanOfContactMockMvc.perform(get("/api/mean-of-contacts/{id}", meanOfContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meanOfContact.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeanOfContact() throws Exception {
        // Get the meanOfContact
        restMeanOfContactMockMvc.perform(get("/api/mean-of-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeanOfContact() throws Exception {
        // Initialize the database
        meanOfContactRepository.saveAndFlush(meanOfContact);
        meanOfContactSearchRepository.save(meanOfContact);
        int databaseSizeBeforeUpdate = meanOfContactRepository.findAll().size();

        // Update the meanOfContact
        MeanOfContact updatedMeanOfContact = meanOfContactRepository.findOne(meanOfContact.getId());
        // Disconnect from session so that the updates on updatedMeanOfContact are not directly saved in db
        em.detach(updatedMeanOfContact);
        updatedMeanOfContact
            .value(UPDATED_VALUE)
            .type(UPDATED_TYPE);
        MeanOfContactDTO meanOfContactDTO = meanOfContactMapper.toDto(updatedMeanOfContact);

        restMeanOfContactMockMvc.perform(put("/api/mean-of-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanOfContactDTO)))
            .andExpect(status().isOk());

        // Validate the MeanOfContact in the database
        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeUpdate);
        MeanOfContact testMeanOfContact = meanOfContactList.get(meanOfContactList.size() - 1);
        assertThat(testMeanOfContact.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testMeanOfContact.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the MeanOfContact in Elasticsearch
        MeanOfContact meanOfContactEs = meanOfContactSearchRepository.findOne(testMeanOfContact.getId());
        assertThat(meanOfContactEs).isEqualToIgnoringGivenFields(testMeanOfContact);
    }

    @Test
    @Transactional
    public void updateNonExistingMeanOfContact() throws Exception {
        int databaseSizeBeforeUpdate = meanOfContactRepository.findAll().size();

        // Create the MeanOfContact
        MeanOfContactDTO meanOfContactDTO = meanOfContactMapper.toDto(meanOfContact);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeanOfContactMockMvc.perform(put("/api/mean-of-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meanOfContactDTO)))
            .andExpect(status().isCreated());

        // Validate the MeanOfContact in the database
        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeanOfContact() throws Exception {
        // Initialize the database
        meanOfContactRepository.saveAndFlush(meanOfContact);
        meanOfContactSearchRepository.save(meanOfContact);
        int databaseSizeBeforeDelete = meanOfContactRepository.findAll().size();

        // Get the meanOfContact
        restMeanOfContactMockMvc.perform(delete("/api/mean-of-contacts/{id}", meanOfContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean meanOfContactExistsInEs = meanOfContactSearchRepository.exists(meanOfContact.getId());
        assertThat(meanOfContactExistsInEs).isFalse();

        // Validate the database is empty
        List<MeanOfContact> meanOfContactList = meanOfContactRepository.findAll();
        assertThat(meanOfContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMeanOfContact() throws Exception {
        // Initialize the database
        meanOfContactRepository.saveAndFlush(meanOfContact);
        meanOfContactSearchRepository.save(meanOfContact);

        // Search the meanOfContact
        restMeanOfContactMockMvc.perform(get("/api/_search/mean-of-contacts?query=id:" + meanOfContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meanOfContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeanOfContact.class);
        MeanOfContact meanOfContact1 = new MeanOfContact();
        meanOfContact1.setId(1L);
        MeanOfContact meanOfContact2 = new MeanOfContact();
        meanOfContact2.setId(meanOfContact1.getId());
        assertThat(meanOfContact1).isEqualTo(meanOfContact2);
        meanOfContact2.setId(2L);
        assertThat(meanOfContact1).isNotEqualTo(meanOfContact2);
        meanOfContact1.setId(null);
        assertThat(meanOfContact1).isNotEqualTo(meanOfContact2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeanOfContactDTO.class);
        MeanOfContactDTO meanOfContactDTO1 = new MeanOfContactDTO();
        meanOfContactDTO1.setId(1L);
        MeanOfContactDTO meanOfContactDTO2 = new MeanOfContactDTO();
        assertThat(meanOfContactDTO1).isNotEqualTo(meanOfContactDTO2);
        meanOfContactDTO2.setId(meanOfContactDTO1.getId());
        assertThat(meanOfContactDTO1).isEqualTo(meanOfContactDTO2);
        meanOfContactDTO2.setId(2L);
        assertThat(meanOfContactDTO1).isNotEqualTo(meanOfContactDTO2);
        meanOfContactDTO1.setId(null);
        assertThat(meanOfContactDTO1).isNotEqualTo(meanOfContactDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(meanOfContactMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(meanOfContactMapper.fromId(null)).isNull();
    }
}
