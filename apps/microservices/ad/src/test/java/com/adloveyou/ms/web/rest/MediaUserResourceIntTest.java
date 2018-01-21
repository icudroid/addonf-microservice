package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.media.MediaUser;
import com.adloveyou.ms.repository.MediaUserRepository;
import com.adloveyou.ms.service.MediaUserService;
import com.adloveyou.ms.repository.search.MediaUserSearchRepository;
import com.adloveyou.ms.service.dto.MediaUserDTO;
import com.adloveyou.ms.service.mapper.MediaUserMapper;
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
 * Test class for the MediaUserResource REST controller.
 *
 * @see MediaUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class MediaUserResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private MediaUserRepository mediaUserRepository;

    @Autowired
    private MediaUserMapper mediaUserMapper;

    @Autowired
    private MediaUserService mediaUserService;

    @Autowired
    private MediaUserSearchRepository mediaUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMediaUserMockMvc;

    private MediaUser mediaUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaUserResource mediaUserResource = new MediaUserResource(mediaUserService);
        this.restMediaUserMockMvc = MockMvcBuilders.standaloneSetup(mediaUserResource)
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
    public static MediaUser createEntity(EntityManager em) {
        MediaUser mediaUser = new MediaUser()
            .userId(DEFAULT_USER_ID);
        return mediaUser;
    }

    @Before
    public void initTest() {
        mediaUserSearchRepository.deleteAll();
        mediaUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaUser() throws Exception {
        int databaseSizeBeforeCreate = mediaUserRepository.findAll().size();

        // Create the MediaUser
        MediaUserDTO mediaUserDTO = mediaUserMapper.toDto(mediaUser);
        restMediaUserMockMvc.perform(post("/api/media-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MediaUser in the database
        List<MediaUser> mediaUserList = mediaUserRepository.findAll();
        assertThat(mediaUserList).hasSize(databaseSizeBeforeCreate + 1);
        MediaUser testMediaUser = mediaUserList.get(mediaUserList.size() - 1);
        assertThat(testMediaUser.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the MediaUser in Elasticsearch
        MediaUser mediaUserEs = mediaUserSearchRepository.findOne(testMediaUser.getId());
        assertThat(mediaUserEs).isEqualToIgnoringGivenFields(testMediaUser);
    }

    @Test
    @Transactional
    public void createMediaUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaUserRepository.findAll().size();

        // Create the MediaUser with an existing ID
        mediaUser.setId(1L);
        MediaUserDTO mediaUserDTO = mediaUserMapper.toDto(mediaUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaUserMockMvc.perform(post("/api/media-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MediaUser in the database
        List<MediaUser> mediaUserList = mediaUserRepository.findAll();
        assertThat(mediaUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaUserRepository.findAll().size();
        // set the field null
        mediaUser.setUserId(null);

        // Create the MediaUser, which fails.
        MediaUserDTO mediaUserDTO = mediaUserMapper.toDto(mediaUser);

        restMediaUserMockMvc.perform(post("/api/media-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaUserDTO)))
            .andExpect(status().isBadRequest());

        List<MediaUser> mediaUserList = mediaUserRepository.findAll();
        assertThat(mediaUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMediaUsers() throws Exception {
        // Initialize the database
        mediaUserRepository.saveAndFlush(mediaUser);

        // Get all the mediaUserList
        restMediaUserMockMvc.perform(get("/api/media-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getMediaUser() throws Exception {
        // Initialize the database
        mediaUserRepository.saveAndFlush(mediaUser);

        // Get the mediaUser
        restMediaUserMockMvc.perform(get("/api/media-users/{id}", mediaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mediaUser.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaUser() throws Exception {
        // Get the mediaUser
        restMediaUserMockMvc.perform(get("/api/media-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaUser() throws Exception {
        // Initialize the database
        mediaUserRepository.saveAndFlush(mediaUser);
        mediaUserSearchRepository.save(mediaUser);
        int databaseSizeBeforeUpdate = mediaUserRepository.findAll().size();

        // Update the mediaUser
        MediaUser updatedMediaUser = mediaUserRepository.findOne(mediaUser.getId());
        // Disconnect from session so that the updates on updatedMediaUser are not directly saved in db
        em.detach(updatedMediaUser);
        updatedMediaUser
            .userId(UPDATED_USER_ID);
        MediaUserDTO mediaUserDTO = mediaUserMapper.toDto(updatedMediaUser);

        restMediaUserMockMvc.perform(put("/api/media-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaUserDTO)))
            .andExpect(status().isOk());

        // Validate the MediaUser in the database
        List<MediaUser> mediaUserList = mediaUserRepository.findAll();
        assertThat(mediaUserList).hasSize(databaseSizeBeforeUpdate);
        MediaUser testMediaUser = mediaUserList.get(mediaUserList.size() - 1);
        assertThat(testMediaUser.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the MediaUser in Elasticsearch
        MediaUser mediaUserEs = mediaUserSearchRepository.findOne(testMediaUser.getId());
        assertThat(mediaUserEs).isEqualToIgnoringGivenFields(testMediaUser);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaUser() throws Exception {
        int databaseSizeBeforeUpdate = mediaUserRepository.findAll().size();

        // Create the MediaUser
        MediaUserDTO mediaUserDTO = mediaUserMapper.toDto(mediaUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaUserMockMvc.perform(put("/api/media-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaUserDTO)))
            .andExpect(status().isCreated());

        // Validate the MediaUser in the database
        List<MediaUser> mediaUserList = mediaUserRepository.findAll();
        assertThat(mediaUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMediaUser() throws Exception {
        // Initialize the database
        mediaUserRepository.saveAndFlush(mediaUser);
        mediaUserSearchRepository.save(mediaUser);
        int databaseSizeBeforeDelete = mediaUserRepository.findAll().size();

        // Get the mediaUser
        restMediaUserMockMvc.perform(delete("/api/media-users/{id}", mediaUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mediaUserExistsInEs = mediaUserSearchRepository.exists(mediaUser.getId());
        assertThat(mediaUserExistsInEs).isFalse();

        // Validate the database is empty
        List<MediaUser> mediaUserList = mediaUserRepository.findAll();
        assertThat(mediaUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMediaUser() throws Exception {
        // Initialize the database
        mediaUserRepository.saveAndFlush(mediaUser);
        mediaUserSearchRepository.save(mediaUser);

        // Search the mediaUser
        restMediaUserMockMvc.perform(get("/api/_search/media-users?query=id:" + mediaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaUser.class);
        MediaUser mediaUser1 = new MediaUser();
        mediaUser1.setId(1L);
        MediaUser mediaUser2 = new MediaUser();
        mediaUser2.setId(mediaUser1.getId());
        assertThat(mediaUser1).isEqualTo(mediaUser2);
        mediaUser2.setId(2L);
        assertThat(mediaUser1).isNotEqualTo(mediaUser2);
        mediaUser1.setId(null);
        assertThat(mediaUser1).isNotEqualTo(mediaUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaUserDTO.class);
        MediaUserDTO mediaUserDTO1 = new MediaUserDTO();
        mediaUserDTO1.setId(1L);
        MediaUserDTO mediaUserDTO2 = new MediaUserDTO();
        assertThat(mediaUserDTO1).isNotEqualTo(mediaUserDTO2);
        mediaUserDTO2.setId(mediaUserDTO1.getId());
        assertThat(mediaUserDTO1).isEqualTo(mediaUserDTO2);
        mediaUserDTO2.setId(2L);
        assertThat(mediaUserDTO1).isNotEqualTo(mediaUserDTO2);
        mediaUserDTO1.setId(null);
        assertThat(mediaUserDTO1).isNotEqualTo(mediaUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mediaUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mediaUserMapper.fromId(null)).isNull();
    }
}
