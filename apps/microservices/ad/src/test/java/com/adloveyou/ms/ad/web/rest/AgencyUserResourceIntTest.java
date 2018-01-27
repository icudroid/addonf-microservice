package com.adloveyou.ms.ad.web.rest;

import com.adloveyou.ms.ad.AdApp;

import com.adloveyou.ms.ad.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.ad.domain.AgencyUser;
import com.adloveyou.ms.ad.repository.AgencyUserRepository;
import com.adloveyou.ms.ad.service.AgencyUserService;
import com.adloveyou.ms.ad.repository.search.AgencyUserSearchRepository;
import com.adloveyou.ms.ad.service.dto.AgencyUserDTO;
import com.adloveyou.ms.ad.service.mapper.AgencyUserMapper;
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
 * Test class for the AgencyUserResource REST controller.
 *
 * @see AgencyUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class AgencyUserResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private AgencyUserRepository agencyUserRepository;

    @Autowired
    private AgencyUserMapper agencyUserMapper;

    @Autowired
    private AgencyUserService agencyUserService;

    @Autowired
    private AgencyUserSearchRepository agencyUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgencyUserMockMvc;

    private AgencyUser agencyUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgencyUserResource agencyUserResource = new AgencyUserResource(agencyUserService);
        this.restAgencyUserMockMvc = MockMvcBuilders.standaloneSetup(agencyUserResource)
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
    public static AgencyUser createEntity(EntityManager em) {
        AgencyUser agencyUser = new AgencyUser()
            .userId(DEFAULT_USER_ID);
        return agencyUser;
    }

    @Before
    public void initTest() {
        agencyUserSearchRepository.deleteAll();
        agencyUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgencyUser() throws Exception {
        int databaseSizeBeforeCreate = agencyUserRepository.findAll().size();

        // Create the AgencyUser
        AgencyUserDTO agencyUserDTO = agencyUserMapper.toDto(agencyUser);
        restAgencyUserMockMvc.perform(post("/api/agency-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AgencyUser in the database
        List<AgencyUser> agencyUserList = agencyUserRepository.findAll();
        assertThat(agencyUserList).hasSize(databaseSizeBeforeCreate + 1);
        AgencyUser testAgencyUser = agencyUserList.get(agencyUserList.size() - 1);
        assertThat(testAgencyUser.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the AgencyUser in Elasticsearch
        AgencyUser agencyUserEs = agencyUserSearchRepository.findOne(testAgencyUser.getId());
        assertThat(agencyUserEs).isEqualToIgnoringGivenFields(testAgencyUser);
    }

    @Test
    @Transactional
    public void createAgencyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agencyUserRepository.findAll().size();

        // Create the AgencyUser with an existing ID
        agencyUser.setId(1L);
        AgencyUserDTO agencyUserDTO = agencyUserMapper.toDto(agencyUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencyUserMockMvc.perform(post("/api/agency-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgencyUser in the database
        List<AgencyUser> agencyUserList = agencyUserRepository.findAll();
        assertThat(agencyUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = agencyUserRepository.findAll().size();
        // set the field null
        agencyUser.setUserId(null);

        // Create the AgencyUser, which fails.
        AgencyUserDTO agencyUserDTO = agencyUserMapper.toDto(agencyUser);

        restAgencyUserMockMvc.perform(post("/api/agency-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyUserDTO)))
            .andExpect(status().isBadRequest());

        List<AgencyUser> agencyUserList = agencyUserRepository.findAll();
        assertThat(agencyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgencyUsers() throws Exception {
        // Initialize the database
        agencyUserRepository.saveAndFlush(agencyUser);

        // Get all the agencyUserList
        restAgencyUserMockMvc.perform(get("/api/agency-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAgencyUser() throws Exception {
        // Initialize the database
        agencyUserRepository.saveAndFlush(agencyUser);

        // Get the agencyUser
        restAgencyUserMockMvc.perform(get("/api/agency-users/{id}", agencyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agencyUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgencyUser() throws Exception {
        // Get the agencyUser
        restAgencyUserMockMvc.perform(get("/api/agency-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgencyUser() throws Exception {
        // Initialize the database
        agencyUserRepository.saveAndFlush(agencyUser);
        agencyUserSearchRepository.save(agencyUser);
        int databaseSizeBeforeUpdate = agencyUserRepository.findAll().size();

        // Update the agencyUser
        AgencyUser updatedAgencyUser = agencyUserRepository.findOne(agencyUser.getId());
        // Disconnect from session so that the updates on updatedAgencyUser are not directly saved in db
        em.detach(updatedAgencyUser);
        updatedAgencyUser
            .userId(UPDATED_USER_ID);
        AgencyUserDTO agencyUserDTO = agencyUserMapper.toDto(updatedAgencyUser);

        restAgencyUserMockMvc.perform(put("/api/agency-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyUserDTO)))
            .andExpect(status().isOk());

        // Validate the AgencyUser in the database
        List<AgencyUser> agencyUserList = agencyUserRepository.findAll();
        assertThat(agencyUserList).hasSize(databaseSizeBeforeUpdate);
        AgencyUser testAgencyUser = agencyUserList.get(agencyUserList.size() - 1);
        assertThat(testAgencyUser.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the AgencyUser in Elasticsearch
        AgencyUser agencyUserEs = agencyUserSearchRepository.findOne(testAgencyUser.getId());
        assertThat(agencyUserEs).isEqualToIgnoringGivenFields(testAgencyUser);
    }

    @Test
    @Transactional
    public void updateNonExistingAgencyUser() throws Exception {
        int databaseSizeBeforeUpdate = agencyUserRepository.findAll().size();

        // Create the AgencyUser
        AgencyUserDTO agencyUserDTO = agencyUserMapper.toDto(agencyUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgencyUserMockMvc.perform(put("/api/agency-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agencyUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AgencyUser in the database
        List<AgencyUser> agencyUserList = agencyUserRepository.findAll();
        assertThat(agencyUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgencyUser() throws Exception {
        // Initialize the database
        agencyUserRepository.saveAndFlush(agencyUser);
        agencyUserSearchRepository.save(agencyUser);
        int databaseSizeBeforeDelete = agencyUserRepository.findAll().size();

        // Get the agencyUser
        restAgencyUserMockMvc.perform(delete("/api/agency-users/{id}", agencyUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean agencyUserExistsInEs = agencyUserSearchRepository.exists(agencyUser.getId());
        assertThat(agencyUserExistsInEs).isFalse();

        // Validate the database is empty
        List<AgencyUser> agencyUserList = agencyUserRepository.findAll();
        assertThat(agencyUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAgencyUser() throws Exception {
        // Initialize the database
        agencyUserRepository.saveAndFlush(agencyUser);
        agencyUserSearchRepository.save(agencyUser);

        // Search the agencyUser
        restAgencyUserMockMvc.perform(get("/api/_search/agency-users?query=id:" + agencyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencyUser.class);
        AgencyUser agencyUser1 = new AgencyUser();
        agencyUser1.setId(1L);
        AgencyUser agencyUser2 = new AgencyUser();
        agencyUser2.setId(agencyUser1.getId());
        assertThat(agencyUser1).isEqualTo(agencyUser2);
        agencyUser2.setId(2L);
        assertThat(agencyUser1).isNotEqualTo(agencyUser2);
        agencyUser1.setId(null);
        assertThat(agencyUser1).isNotEqualTo(agencyUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgencyUserDTO.class);
        AgencyUserDTO agencyUserDTO1 = new AgencyUserDTO();
        agencyUserDTO1.setId(1L);
        AgencyUserDTO agencyUserDTO2 = new AgencyUserDTO();
        assertThat(agencyUserDTO1).isNotEqualTo(agencyUserDTO2);
        agencyUserDTO2.setId(agencyUserDTO1.getId());
        assertThat(agencyUserDTO1).isEqualTo(agencyUserDTO2);
        agencyUserDTO2.setId(2L);
        assertThat(agencyUserDTO1).isNotEqualTo(agencyUserDTO2);
        agencyUserDTO1.setId(null);
        assertThat(agencyUserDTO1).isNotEqualTo(agencyUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agencyUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agencyUserMapper.fromId(null)).isNull();
    }
}
