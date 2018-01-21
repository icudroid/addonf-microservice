package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.BrandUser;
import com.adloveyou.ms.repository.BrandUserRepository;
import com.adloveyou.ms.service.BrandUserService;
import com.adloveyou.ms.repository.search.BrandUserSearchRepository;
import com.adloveyou.ms.service.dto.BrandUserDTO;
import com.adloveyou.ms.service.mapper.BrandUserMapper;
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
 * Test class for the BrandUserResource REST controller.
 *
 * @see BrandUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class BrandUserResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private BrandUserRepository brandUserRepository;

    @Autowired
    private BrandUserMapper brandUserMapper;

    @Autowired
    private BrandUserService brandUserService;

    @Autowired
    private BrandUserSearchRepository brandUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandUserMockMvc;

    private BrandUser brandUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandUserResource brandUserResource = new BrandUserResource(brandUserService);
        this.restBrandUserMockMvc = MockMvcBuilders.standaloneSetup(brandUserResource)
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
    public static BrandUser createEntity(EntityManager em) {
        BrandUser brandUser = new BrandUser()
            .userId(DEFAULT_USER_ID);
        return brandUser;
    }

    @Before
    public void initTest() {
        brandUserSearchRepository.deleteAll();
        brandUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrandUser() throws Exception {
        int databaseSizeBeforeCreate = brandUserRepository.findAll().size();

        // Create the BrandUser
        BrandUserDTO brandUserDTO = brandUserMapper.toDto(brandUser);
        restBrandUserMockMvc.perform(post("/api/brand-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandUserDTO)))
            .andExpect(status().isCreated());

        // Validate the BrandUser in the database
        List<BrandUser> brandUserList = brandUserRepository.findAll();
        assertThat(brandUserList).hasSize(databaseSizeBeforeCreate + 1);
        BrandUser testBrandUser = brandUserList.get(brandUserList.size() - 1);
        assertThat(testBrandUser.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the BrandUser in Elasticsearch
        BrandUser brandUserEs = brandUserSearchRepository.findOne(testBrandUser.getId());
        assertThat(brandUserEs).isEqualToIgnoringGivenFields(testBrandUser);
    }

    @Test
    @Transactional
    public void createBrandUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandUserRepository.findAll().size();

        // Create the BrandUser with an existing ID
        brandUser.setId(1L);
        BrandUserDTO brandUserDTO = brandUserMapper.toDto(brandUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandUserMockMvc.perform(post("/api/brand-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BrandUser in the database
        List<BrandUser> brandUserList = brandUserRepository.findAll();
        assertThat(brandUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandUserRepository.findAll().size();
        // set the field null
        brandUser.setUserId(null);

        // Create the BrandUser, which fails.
        BrandUserDTO brandUserDTO = brandUserMapper.toDto(brandUser);

        restBrandUserMockMvc.perform(post("/api/brand-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandUserDTO)))
            .andExpect(status().isBadRequest());

        List<BrandUser> brandUserList = brandUserRepository.findAll();
        assertThat(brandUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrandUsers() throws Exception {
        // Initialize the database
        brandUserRepository.saveAndFlush(brandUser);

        // Get all the brandUserList
        restBrandUserMockMvc.perform(get("/api/brand-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getBrandUser() throws Exception {
        // Initialize the database
        brandUserRepository.saveAndFlush(brandUser);

        // Get the brandUser
        restBrandUserMockMvc.perform(get("/api/brand-users/{id}", brandUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brandUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBrandUser() throws Exception {
        // Get the brandUser
        restBrandUserMockMvc.perform(get("/api/brand-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrandUser() throws Exception {
        // Initialize the database
        brandUserRepository.saveAndFlush(brandUser);
        brandUserSearchRepository.save(brandUser);
        int databaseSizeBeforeUpdate = brandUserRepository.findAll().size();

        // Update the brandUser
        BrandUser updatedBrandUser = brandUserRepository.findOne(brandUser.getId());
        // Disconnect from session so that the updates on updatedBrandUser are not directly saved in db
        em.detach(updatedBrandUser);
        updatedBrandUser
            .userId(UPDATED_USER_ID);
        BrandUserDTO brandUserDTO = brandUserMapper.toDto(updatedBrandUser);

        restBrandUserMockMvc.perform(put("/api/brand-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandUserDTO)))
            .andExpect(status().isOk());

        // Validate the BrandUser in the database
        List<BrandUser> brandUserList = brandUserRepository.findAll();
        assertThat(brandUserList).hasSize(databaseSizeBeforeUpdate);
        BrandUser testBrandUser = brandUserList.get(brandUserList.size() - 1);
        assertThat(testBrandUser.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the BrandUser in Elasticsearch
        BrandUser brandUserEs = brandUserSearchRepository.findOne(testBrandUser.getId());
        assertThat(brandUserEs).isEqualToIgnoringGivenFields(testBrandUser);
    }

    @Test
    @Transactional
    public void updateNonExistingBrandUser() throws Exception {
        int databaseSizeBeforeUpdate = brandUserRepository.findAll().size();

        // Create the BrandUser
        BrandUserDTO brandUserDTO = brandUserMapper.toDto(brandUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandUserMockMvc.perform(put("/api/brand-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandUserDTO)))
            .andExpect(status().isCreated());

        // Validate the BrandUser in the database
        List<BrandUser> brandUserList = brandUserRepository.findAll();
        assertThat(brandUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrandUser() throws Exception {
        // Initialize the database
        brandUserRepository.saveAndFlush(brandUser);
        brandUserSearchRepository.save(brandUser);
        int databaseSizeBeforeDelete = brandUserRepository.findAll().size();

        // Get the brandUser
        restBrandUserMockMvc.perform(delete("/api/brand-users/{id}", brandUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandUserExistsInEs = brandUserSearchRepository.exists(brandUser.getId());
        assertThat(brandUserExistsInEs).isFalse();

        // Validate the database is empty
        List<BrandUser> brandUserList = brandUserRepository.findAll();
        assertThat(brandUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrandUser() throws Exception {
        // Initialize the database
        brandUserRepository.saveAndFlush(brandUser);
        brandUserSearchRepository.save(brandUser);

        // Search the brandUser
        restBrandUserMockMvc.perform(get("/api/_search/brand-users?query=id:" + brandUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandUser.class);
        BrandUser brandUser1 = new BrandUser();
        brandUser1.setId(1L);
        BrandUser brandUser2 = new BrandUser();
        brandUser2.setId(brandUser1.getId());
        assertThat(brandUser1).isEqualTo(brandUser2);
        brandUser2.setId(2L);
        assertThat(brandUser1).isNotEqualTo(brandUser2);
        brandUser1.setId(null);
        assertThat(brandUser1).isNotEqualTo(brandUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandUserDTO.class);
        BrandUserDTO brandUserDTO1 = new BrandUserDTO();
        brandUserDTO1.setId(1L);
        BrandUserDTO brandUserDTO2 = new BrandUserDTO();
        assertThat(brandUserDTO1).isNotEqualTo(brandUserDTO2);
        brandUserDTO2.setId(brandUserDTO1.getId());
        assertThat(brandUserDTO1).isEqualTo(brandUserDTO2);
        brandUserDTO2.setId(2L);
        assertThat(brandUserDTO1).isNotEqualTo(brandUserDTO2);
        brandUserDTO1.setId(null);
        assertThat(brandUserDTO1).isNotEqualTo(brandUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(brandUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(brandUserMapper.fromId(null)).isNull();
    }
}
