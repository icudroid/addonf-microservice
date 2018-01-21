package com.adloveyou.ms.web.rest;

import com.adloveyou.ms.AdApp;

import com.adloveyou.ms.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.domain.BidCategoryMedia;
import com.adloveyou.ms.repository.BidCategoryMediaRepository;
import com.adloveyou.ms.service.BidCategoryMediaService;
import com.adloveyou.ms.repository.search.BidCategoryMediaSearchRepository;
import com.adloveyou.ms.service.dto.BidCategoryMediaDTO;
import com.adloveyou.ms.service.mapper.BidCategoryMediaMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.adloveyou.ms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.adloveyou.ms.domain.enumeration.AdMediaType;
/**
 * Test class for the BidCategoryMediaResource REST controller.
 *
 * @see BidCategoryMediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class BidCategoryMediaResourceIntTest {

    private static final BigDecimal DEFAULT_BID = new BigDecimal(1);
    private static final BigDecimal UPDATED_BID = new BigDecimal(2);

    private static final AdMediaType DEFAULT_MEDIA_TYPE = AdMediaType.WEB;
    private static final AdMediaType UPDATED_MEDIA_TYPE = AdMediaType.MOBILE;

    @Autowired
    private BidCategoryMediaRepository bidCategoryMediaRepository;

    @Autowired
    private BidCategoryMediaMapper bidCategoryMediaMapper;

    @Autowired
    private BidCategoryMediaService bidCategoryMediaService;

    @Autowired
    private BidCategoryMediaSearchRepository bidCategoryMediaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBidCategoryMediaMockMvc;

    private BidCategoryMedia bidCategoryMedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BidCategoryMediaResource bidCategoryMediaResource = new BidCategoryMediaResource(bidCategoryMediaService);
        this.restBidCategoryMediaMockMvc = MockMvcBuilders.standaloneSetup(bidCategoryMediaResource)
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
    public static BidCategoryMedia createEntity(EntityManager em) {
        BidCategoryMedia bidCategoryMedia = new BidCategoryMedia()
            .bid(DEFAULT_BID)
            .mediaType(DEFAULT_MEDIA_TYPE);
        return bidCategoryMedia;
    }

    @Before
    public void initTest() {
        bidCategoryMediaSearchRepository.deleteAll();
        bidCategoryMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createBidCategoryMedia() throws Exception {
        int databaseSizeBeforeCreate = bidCategoryMediaRepository.findAll().size();

        // Create the BidCategoryMedia
        BidCategoryMediaDTO bidCategoryMediaDTO = bidCategoryMediaMapper.toDto(bidCategoryMedia);
        restBidCategoryMediaMockMvc.perform(post("/api/bid-category-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bidCategoryMediaDTO)))
            .andExpect(status().isCreated());

        // Validate the BidCategoryMedia in the database
        List<BidCategoryMedia> bidCategoryMediaList = bidCategoryMediaRepository.findAll();
        assertThat(bidCategoryMediaList).hasSize(databaseSizeBeforeCreate + 1);
        BidCategoryMedia testBidCategoryMedia = bidCategoryMediaList.get(bidCategoryMediaList.size() - 1);
        assertThat(testBidCategoryMedia.getBid()).isEqualTo(DEFAULT_BID);
        assertThat(testBidCategoryMedia.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);

        // Validate the BidCategoryMedia in Elasticsearch
        BidCategoryMedia bidCategoryMediaEs = bidCategoryMediaSearchRepository.findOne(testBidCategoryMedia.getId());
        assertThat(bidCategoryMediaEs).isEqualToIgnoringGivenFields(testBidCategoryMedia);
    }

    @Test
    @Transactional
    public void createBidCategoryMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bidCategoryMediaRepository.findAll().size();

        // Create the BidCategoryMedia with an existing ID
        bidCategoryMedia.setId(1L);
        BidCategoryMediaDTO bidCategoryMediaDTO = bidCategoryMediaMapper.toDto(bidCategoryMedia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBidCategoryMediaMockMvc.perform(post("/api/bid-category-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bidCategoryMediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BidCategoryMedia in the database
        List<BidCategoryMedia> bidCategoryMediaList = bidCategoryMediaRepository.findAll();
        assertThat(bidCategoryMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBidIsRequired() throws Exception {
        int databaseSizeBeforeTest = bidCategoryMediaRepository.findAll().size();
        // set the field null
        bidCategoryMedia.setBid(null);

        // Create the BidCategoryMedia, which fails.
        BidCategoryMediaDTO bidCategoryMediaDTO = bidCategoryMediaMapper.toDto(bidCategoryMedia);

        restBidCategoryMediaMockMvc.perform(post("/api/bid-category-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bidCategoryMediaDTO)))
            .andExpect(status().isBadRequest());

        List<BidCategoryMedia> bidCategoryMediaList = bidCategoryMediaRepository.findAll();
        assertThat(bidCategoryMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBidCategoryMedias() throws Exception {
        // Initialize the database
        bidCategoryMediaRepository.saveAndFlush(bidCategoryMedia);

        // Get all the bidCategoryMediaList
        restBidCategoryMediaMockMvc.perform(get("/api/bid-category-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bidCategoryMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].bid").value(hasItem(DEFAULT_BID.intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getBidCategoryMedia() throws Exception {
        // Initialize the database
        bidCategoryMediaRepository.saveAndFlush(bidCategoryMedia);

        // Get the bidCategoryMedia
        restBidCategoryMediaMockMvc.perform(get("/api/bid-category-medias/{id}", bidCategoryMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bidCategoryMedia.getId().intValue()))
            .andExpect(jsonPath("$.bid").value(DEFAULT_BID.intValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBidCategoryMedia() throws Exception {
        // Get the bidCategoryMedia
        restBidCategoryMediaMockMvc.perform(get("/api/bid-category-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBidCategoryMedia() throws Exception {
        // Initialize the database
        bidCategoryMediaRepository.saveAndFlush(bidCategoryMedia);
        bidCategoryMediaSearchRepository.save(bidCategoryMedia);
        int databaseSizeBeforeUpdate = bidCategoryMediaRepository.findAll().size();

        // Update the bidCategoryMedia
        BidCategoryMedia updatedBidCategoryMedia = bidCategoryMediaRepository.findOne(bidCategoryMedia.getId());
        // Disconnect from session so that the updates on updatedBidCategoryMedia are not directly saved in db
        em.detach(updatedBidCategoryMedia);
        updatedBidCategoryMedia
            .bid(UPDATED_BID)
            .mediaType(UPDATED_MEDIA_TYPE);
        BidCategoryMediaDTO bidCategoryMediaDTO = bidCategoryMediaMapper.toDto(updatedBidCategoryMedia);

        restBidCategoryMediaMockMvc.perform(put("/api/bid-category-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bidCategoryMediaDTO)))
            .andExpect(status().isOk());

        // Validate the BidCategoryMedia in the database
        List<BidCategoryMedia> bidCategoryMediaList = bidCategoryMediaRepository.findAll();
        assertThat(bidCategoryMediaList).hasSize(databaseSizeBeforeUpdate);
        BidCategoryMedia testBidCategoryMedia = bidCategoryMediaList.get(bidCategoryMediaList.size() - 1);
        assertThat(testBidCategoryMedia.getBid()).isEqualTo(UPDATED_BID);
        assertThat(testBidCategoryMedia.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);

        // Validate the BidCategoryMedia in Elasticsearch
        BidCategoryMedia bidCategoryMediaEs = bidCategoryMediaSearchRepository.findOne(testBidCategoryMedia.getId());
        assertThat(bidCategoryMediaEs).isEqualToIgnoringGivenFields(testBidCategoryMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingBidCategoryMedia() throws Exception {
        int databaseSizeBeforeUpdate = bidCategoryMediaRepository.findAll().size();

        // Create the BidCategoryMedia
        BidCategoryMediaDTO bidCategoryMediaDTO = bidCategoryMediaMapper.toDto(bidCategoryMedia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBidCategoryMediaMockMvc.perform(put("/api/bid-category-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bidCategoryMediaDTO)))
            .andExpect(status().isCreated());

        // Validate the BidCategoryMedia in the database
        List<BidCategoryMedia> bidCategoryMediaList = bidCategoryMediaRepository.findAll();
        assertThat(bidCategoryMediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBidCategoryMedia() throws Exception {
        // Initialize the database
        bidCategoryMediaRepository.saveAndFlush(bidCategoryMedia);
        bidCategoryMediaSearchRepository.save(bidCategoryMedia);
        int databaseSizeBeforeDelete = bidCategoryMediaRepository.findAll().size();

        // Get the bidCategoryMedia
        restBidCategoryMediaMockMvc.perform(delete("/api/bid-category-medias/{id}", bidCategoryMedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bidCategoryMediaExistsInEs = bidCategoryMediaSearchRepository.exists(bidCategoryMedia.getId());
        assertThat(bidCategoryMediaExistsInEs).isFalse();

        // Validate the database is empty
        List<BidCategoryMedia> bidCategoryMediaList = bidCategoryMediaRepository.findAll();
        assertThat(bidCategoryMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBidCategoryMedia() throws Exception {
        // Initialize the database
        bidCategoryMediaRepository.saveAndFlush(bidCategoryMedia);
        bidCategoryMediaSearchRepository.save(bidCategoryMedia);

        // Search the bidCategoryMedia
        restBidCategoryMediaMockMvc.perform(get("/api/_search/bid-category-medias?query=id:" + bidCategoryMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bidCategoryMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].bid").value(hasItem(DEFAULT_BID.intValue())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BidCategoryMedia.class);
        BidCategoryMedia bidCategoryMedia1 = new BidCategoryMedia();
        bidCategoryMedia1.setId(1L);
        BidCategoryMedia bidCategoryMedia2 = new BidCategoryMedia();
        bidCategoryMedia2.setId(bidCategoryMedia1.getId());
        assertThat(bidCategoryMedia1).isEqualTo(bidCategoryMedia2);
        bidCategoryMedia2.setId(2L);
        assertThat(bidCategoryMedia1).isNotEqualTo(bidCategoryMedia2);
        bidCategoryMedia1.setId(null);
        assertThat(bidCategoryMedia1).isNotEqualTo(bidCategoryMedia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BidCategoryMediaDTO.class);
        BidCategoryMediaDTO bidCategoryMediaDTO1 = new BidCategoryMediaDTO();
        bidCategoryMediaDTO1.setId(1L);
        BidCategoryMediaDTO bidCategoryMediaDTO2 = new BidCategoryMediaDTO();
        assertThat(bidCategoryMediaDTO1).isNotEqualTo(bidCategoryMediaDTO2);
        bidCategoryMediaDTO2.setId(bidCategoryMediaDTO1.getId());
        assertThat(bidCategoryMediaDTO1).isEqualTo(bidCategoryMediaDTO2);
        bidCategoryMediaDTO2.setId(2L);
        assertThat(bidCategoryMediaDTO1).isNotEqualTo(bidCategoryMediaDTO2);
        bidCategoryMediaDTO1.setId(null);
        assertThat(bidCategoryMediaDTO1).isNotEqualTo(bidCategoryMediaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bidCategoryMediaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bidCategoryMediaMapper.fromId(null)).isNull();
    }
}
