package com.adloveyou.ms.ad.web.rest;

import com.adloveyou.ms.ad.AdApp;

import com.adloveyou.ms.ad.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.ad.domain.Media;
import com.adloveyou.ms.ad.repository.MediaRepository;
import com.adloveyou.ms.ad.service.MediaService;
import com.adloveyou.ms.ad.repository.search.MediaSearchRepository;
import com.adloveyou.ms.ad.service.dto.MediaDTO;
import com.adloveyou.ms.ad.service.mapper.MediaMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adloveyou.ms.ad.domain.enumeration.LegalStatus;
/**
 * Test class for the MediaResource REST controller.
 *
 * @see MediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class MediaResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASS_PHRASE = "AAAAAAAAAAAAAAAA";
    private static final String UPDATED_PASS_PHRASE = "BBBBBBBBBBBBBBBB";

    private static final String DEFAULT_EXT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXT_ID = "BBBBBBBBBB";

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
    private MediaRepository mediaRepository;

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaSearchRepository mediaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMediaMockMvc;

    private Media media;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaResource mediaResource = new MediaResource(mediaService);
        this.restMediaMockMvc = MockMvcBuilders.standaloneSetup(mediaResource)
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
    public static Media createEntity(EntityManager em) {
        Media media = new Media()
            .name(DEFAULT_NAME)
            .passPhrase(DEFAULT_PASS_PHRASE)
            .extId(DEFAULT_EXT_ID)
            .siret(DEFAULT_SIRET)
            .siren(DEFAULT_SIREN)
            .legalStatus(DEFAULT_LEGAL_STATUS)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return media;
    }

    @Before
    public void initTest() {
        mediaSearchRepository.deleteAll();
        media = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedia() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);
        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isCreated());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate + 1);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedia.getPassPhrase()).isEqualTo(DEFAULT_PASS_PHRASE);
        assertThat(testMedia.getExtId()).isEqualTo(DEFAULT_EXT_ID);
        assertThat(testMedia.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testMedia.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testMedia.getLegalStatus()).isEqualTo(DEFAULT_LEGAL_STATUS);
        assertThat(testMedia.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testMedia.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);

        // Validate the Media in Elasticsearch
        Media mediaEs = mediaSearchRepository.findOne(testMedia.getId());
        assertThat(mediaEs).isEqualToIgnoringGivenFields(testMedia);
    }

    @Test
    @Transactional
    public void createMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // Create the Media with an existing ID
        media.setId(1L);
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaRepository.findAll().size();
        // set the field null
        media.setName(null);

        // Create the Media, which fails.
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPassPhraseIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaRepository.findAll().size();
        // set the field null
        media.setPassPhrase(null);

        // Create the Media, which fails.
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaRepository.findAll().size();
        // set the field null
        media.setExtId(null);

        // Create the Media, which fails.
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLogoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mediaRepository.findAll().size();
        // set the field null
        media.setLogo(null);

        // Create the Media, which fails.
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get all the mediaList
        restMediaMockMvc.perform(get("/api/media?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(media.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].passPhrase").value(hasItem(DEFAULT_PASS_PHRASE.toString())))
            .andExpect(jsonPath("$.[*].extId").value(hasItem(DEFAULT_EXT_ID.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN.toString())))
            .andExpect(jsonPath("$.[*].legalStatus").value(hasItem(DEFAULT_LEGAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void getMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get the media
        restMediaMockMvc.perform(get("/api/media/{id}", media.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(media.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.passPhrase").value(DEFAULT_PASS_PHRASE.toString()))
            .andExpect(jsonPath("$.extId").value(DEFAULT_EXT_ID.toString()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET.toString()))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN.toString()))
            .andExpect(jsonPath("$.legalStatus").value(DEFAULT_LEGAL_STATUS.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingMedia() throws Exception {
        // Get the media
        restMediaMockMvc.perform(get("/api/media/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);
        mediaSearchRepository.save(media);
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Update the media
        Media updatedMedia = mediaRepository.findOne(media.getId());
        // Disconnect from session so that the updates on updatedMedia are not directly saved in db
        em.detach(updatedMedia);
        updatedMedia
            .name(UPDATED_NAME)
            .passPhrase(UPDATED_PASS_PHRASE)
            .extId(UPDATED_EXT_ID)
            .siret(UPDATED_SIRET)
            .siren(UPDATED_SIREN)
            .legalStatus(UPDATED_LEGAL_STATUS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        MediaDTO mediaDTO = mediaMapper.toDto(updatedMedia);

        restMediaMockMvc.perform(put("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isOk());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedia.getPassPhrase()).isEqualTo(UPDATED_PASS_PHRASE);
        assertThat(testMedia.getExtId()).isEqualTo(UPDATED_EXT_ID);
        assertThat(testMedia.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testMedia.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testMedia.getLegalStatus()).isEqualTo(UPDATED_LEGAL_STATUS);
        assertThat(testMedia.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testMedia.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);

        // Validate the Media in Elasticsearch
        Media mediaEs = mediaSearchRepository.findOne(testMedia.getId());
        assertThat(mediaEs).isEqualToIgnoringGivenFields(testMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaMockMvc.perform(put("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isCreated());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);
        mediaSearchRepository.save(media);
        int databaseSizeBeforeDelete = mediaRepository.findAll().size();

        // Get the media
        restMediaMockMvc.perform(delete("/api/media/{id}", media.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean mediaExistsInEs = mediaSearchRepository.exists(media.getId());
        assertThat(mediaExistsInEs).isFalse();

        // Validate the database is empty
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);
        mediaSearchRepository.save(media);

        // Search the media
        restMediaMockMvc.perform(get("/api/_search/media?query=id:" + media.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(media.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].passPhrase").value(hasItem(DEFAULT_PASS_PHRASE.toString())))
            .andExpect(jsonPath("$.[*].extId").value(hasItem(DEFAULT_EXT_ID.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN.toString())))
            .andExpect(jsonPath("$.[*].legalStatus").value(hasItem(DEFAULT_LEGAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Media.class);
        Media media1 = new Media();
        media1.setId(1L);
        Media media2 = new Media();
        media2.setId(media1.getId());
        assertThat(media1).isEqualTo(media2);
        media2.setId(2L);
        assertThat(media1).isNotEqualTo(media2);
        media1.setId(null);
        assertThat(media1).isNotEqualTo(media2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaDTO.class);
        MediaDTO mediaDTO1 = new MediaDTO();
        mediaDTO1.setId(1L);
        MediaDTO mediaDTO2 = new MediaDTO();
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
        mediaDTO2.setId(mediaDTO1.getId());
        assertThat(mediaDTO1).isEqualTo(mediaDTO2);
        mediaDTO2.setId(2L);
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
        mediaDTO1.setId(null);
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mediaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mediaMapper.fromId(null)).isNull();
    }
}
