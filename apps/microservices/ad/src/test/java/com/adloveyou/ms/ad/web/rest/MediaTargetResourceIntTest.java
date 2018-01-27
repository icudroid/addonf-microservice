package com.adloveyou.ms.ad.web.rest;

import com.adloveyou.ms.ad.AdApp;

import com.adloveyou.ms.ad.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.ad.domain.MediaTarget;
import com.adloveyou.ms.ad.repository.MediaTargetRepository;
import com.adloveyou.ms.ad.service.MediaTargetService;
import com.adloveyou.ms.ad.repository.search.MediaTargetSearchRepository;
import com.adloveyou.ms.ad.service.dto.MediaTargetDTO;
import com.adloveyou.ms.ad.service.mapper.MediaTargetMapper;
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

import com.adloveyou.ms.ad.domain.enumeration.AdMediaType;
/**
 * Test class for the MediaTargetResource REST controller.
 *
 * @see MediaTargetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class MediaTargetResourceIntTest {

    private static final AdMediaType DEFAULT_MEDIA_TYPE = AdMediaType.WEB;
    private static final AdMediaType UPDATED_MEDIA_TYPE = AdMediaType.MOBILE;

    @Autowired
    private MediaTargetRepository mediaTargetRepository;

    @Autowired
    private MediaTargetMapper mediaTargetMapper;

    @Autowired
    private MediaTargetService mediaTargetService;

    @Autowired
    private MediaTargetSearchRepository mediaTargetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMediaTargetMockMvc;

    private MediaTarget mediaTarget;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaTargetResource mediaTargetResource = new MediaTargetResource(mediaTargetService);
        this.restMediaTargetMockMvc = MockMvcBuilders.standaloneSetup(mediaTargetResource)
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
    public static MediaTarget createEntity(EntityManager em) {
        MediaTarget mediaTarget = new MediaTarget()
            .mediaType(DEFAULT_MEDIA_TYPE);
        return mediaTarget;
    }

    @Before
    public void initTest() {
        mediaTargetSearchRepository.deleteAll();
        mediaTarget = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaTarget() throws Exception {
        int databaseSizeBeforeCreate = mediaTargetRepository.findAll().size();

        // Create the MediaTarget
        MediaTargetDTO mediaTargetDTO = mediaTargetMapper.toDto(mediaTarget);
        restMediaTargetMockMvc.perform(post("/api/media-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaTargetDTO)))
            .andExpect(status().isCreated());

        // Validate the MediaTarget in the database
        List<MediaTarget> mediaTargetList = mediaTargetRepository.findAll();
        assertThat(mediaTargetList).hasSize(databaseSizeBeforeCreate + 1);
        MediaTarget testMediaTarget = mediaTargetList.get(mediaTargetList.size() - 1);
        assertThat(testMediaTarget.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);

        // Validate the MediaTarget in Elasticsearch
        MediaTarget mediaTargetEs = mediaTargetSearchRepository.findOne(testMediaTarget.getId());
        assertThat(mediaTargetEs).isEqualToIgnoringGivenFields(testMediaTarget);
    }

    @Test
    @Transactional
    public void createMediaTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaTargetRepository.findAll().size();

        // Create the MediaTarget with an existing ID
        mediaTarget.setId(1L);
        MediaTargetDTO mediaTargetDTO = mediaTargetMapper.toDto(mediaTarget);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaTargetMockMvc.perform(post("/api/media-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaTargetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MediaTarget in the database
        List<MediaTarget> mediaTargetList = mediaTargetRepository.findAll();
        assertThat(mediaTargetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMediaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaTargetRepository.findAll().size();
        // set the field null
        mediaTarget.setMediaType(null);

        // Create the MediaTarget, which fails.
        MediaTargetDTO mediaTargetDTO = mediaTargetMapper.toDto(mediaTarget);

        restMediaTargetMockMvc.perform(post("/api/media-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaTargetDTO)))
            .andExpect(status().isBadRequest());

        List<MediaTarget> mediaTargetList = mediaTargetRepository.findAll();
        assertThat(mediaTargetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMediaTargets() throws Exception {
        // Initialize the database
        mediaTargetRepository.saveAndFlush(mediaTarget);

        // Get all the mediaTargetList
        restMediaTargetMockMvc.perform(get("/api/media-targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMediaTarget() throws Exception {
        // Initialize the database
        mediaTargetRepository.saveAndFlush(mediaTarget);

        // Get the mediaTarget
        restMediaTargetMockMvc.perform(get("/api/media-targets/{id}", mediaTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mediaTarget.getId().intValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaTarget() throws Exception {
        // Get the mediaTarget
        restMediaTargetMockMvc.perform(get("/api/media-targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaTarget() throws Exception {
        // Initialize the database
        mediaTargetRepository.saveAndFlush(mediaTarget);
        mediaTargetSearchRepository.save(mediaTarget);
        int databaseSizeBeforeUpdate = mediaTargetRepository.findAll().size();

        // Update the mediaTarget
        MediaTarget updatedMediaTarget = mediaTargetRepository.findOne(mediaTarget.getId());
        // Disconnect from session so that the updates on updatedMediaTarget are not directly saved in db
        em.detach(updatedMediaTarget);
        updatedMediaTarget
            .mediaType(UPDATED_MEDIA_TYPE);
        MediaTargetDTO mediaTargetDTO = mediaTargetMapper.toDto(updatedMediaTarget);

        restMediaTargetMockMvc.perform(put("/api/media-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaTargetDTO)))
            .andExpect(status().isOk());

        // Validate the MediaTarget in the database
        List<MediaTarget> mediaTargetList = mediaTargetRepository.findAll();
        assertThat(mediaTargetList).hasSize(databaseSizeBeforeUpdate);
        MediaTarget testMediaTarget = mediaTargetList.get(mediaTargetList.size() - 1);
        assertThat(testMediaTarget.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);

        // Validate the MediaTarget in Elasticsearch
        MediaTarget mediaTargetEs = mediaTargetSearchRepository.findOne(testMediaTarget.getId());
        assertThat(mediaTargetEs).isEqualToIgnoringGivenFields(testMediaTarget);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaTarget() throws Exception {
        int databaseSizeBeforeUpdate = mediaTargetRepository.findAll().size();

        // Create the MediaTarget
        MediaTargetDTO mediaTargetDTO = mediaTargetMapper.toDto(mediaTarget);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaTargetMockMvc.perform(put("/api/media-targets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaTargetDTO)))
            .andExpect(status().isCreated());

        // Validate the MediaTarget in the database
        List<MediaTarget> mediaTargetList = mediaTargetRepository.findAll();
        assertThat(mediaTargetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMediaTarget() throws Exception {
        // Initialize the database
        mediaTargetRepository.saveAndFlush(mediaTarget);
        mediaTargetSearchRepository.save(mediaTarget);
        int databaseSizeBeforeDelete = mediaTargetRepository.findAll().size();

        // Get the mediaTarget
        restMediaTargetMockMvc.perform(delete("/api/media-targets/{id}", mediaTarget.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mediaTargetExistsInEs = mediaTargetSearchRepository.exists(mediaTarget.getId());
        assertThat(mediaTargetExistsInEs).isFalse();

        // Validate the database is empty
        List<MediaTarget> mediaTargetList = mediaTargetRepository.findAll();
        assertThat(mediaTargetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMediaTarget() throws Exception {
        // Initialize the database
        mediaTargetRepository.saveAndFlush(mediaTarget);
        mediaTargetSearchRepository.save(mediaTarget);

        // Search the mediaTarget
        restMediaTargetMockMvc.perform(get("/api/_search/media-targets?query=id:" + mediaTarget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaTarget.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaTarget.class);
        MediaTarget mediaTarget1 = new MediaTarget();
        mediaTarget1.setId(1L);
        MediaTarget mediaTarget2 = new MediaTarget();
        mediaTarget2.setId(mediaTarget1.getId());
        assertThat(mediaTarget1).isEqualTo(mediaTarget2);
        mediaTarget2.setId(2L);
        assertThat(mediaTarget1).isNotEqualTo(mediaTarget2);
        mediaTarget1.setId(null);
        assertThat(mediaTarget1).isNotEqualTo(mediaTarget2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaTargetDTO.class);
        MediaTargetDTO mediaTargetDTO1 = new MediaTargetDTO();
        mediaTargetDTO1.setId(1L);
        MediaTargetDTO mediaTargetDTO2 = new MediaTargetDTO();
        assertThat(mediaTargetDTO1).isNotEqualTo(mediaTargetDTO2);
        mediaTargetDTO2.setId(mediaTargetDTO1.getId());
        assertThat(mediaTargetDTO1).isEqualTo(mediaTargetDTO2);
        mediaTargetDTO2.setId(2L);
        assertThat(mediaTargetDTO1).isNotEqualTo(mediaTargetDTO2);
        mediaTargetDTO1.setId(null);
        assertThat(mediaTargetDTO1).isNotEqualTo(mediaTargetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mediaTargetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mediaTargetMapper.fromId(null)).isNull();
    }
}
