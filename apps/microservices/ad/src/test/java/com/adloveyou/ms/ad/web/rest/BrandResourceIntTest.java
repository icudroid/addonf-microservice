package com.adloveyou.ms.ad.web.rest;

import com.adloveyou.ms.ad.AdApp;

import com.adloveyou.ms.ad.config.SecurityBeanOverrideConfiguration;

import com.adloveyou.ms.ad.domain.Brand;
import com.adloveyou.ms.ad.repository.BrandRepository;
import com.adloveyou.ms.ad.service.BrandService;
import com.adloveyou.ms.ad.repository.search.BrandSearchRepository;
import com.adloveyou.ms.ad.service.dto.BrandDTO;
import com.adloveyou.ms.ad.service.mapper.BrandMapper;
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
 * Test class for the BrandResource REST controller.
 *
 * @see BrandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdApp.class, SecurityBeanOverrideConfiguration.class})
public class BrandResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

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
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandSearchRepository brandSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandMockMvc;

    private Brand brand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandResource brandResource = new BrandResource(brandService);
        this.restBrandMockMvc = MockMvcBuilders.standaloneSetup(brandResource)
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
    public static Brand createEntity(EntityManager em) {
        Brand brand = new Brand()
            .name(DEFAULT_NAME)
            .userId(DEFAULT_USER_ID)
            .siret(DEFAULT_SIRET)
            .siren(DEFAULT_SIREN)
            .legalStatus(DEFAULT_LEGAL_STATUS)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        return brand;
    }

    @Before
    public void initTest() {
        brandSearchRepository.deleteAll();
        brand = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrand() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);
        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandDTO)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate + 1);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBrand.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBrand.getSiret()).isEqualTo(DEFAULT_SIRET);
        assertThat(testBrand.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testBrand.getLegalStatus()).isEqualTo(DEFAULT_LEGAL_STATUS);
        assertThat(testBrand.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBrand.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);

        // Validate the Brand in Elasticsearch
        Brand brandEs = brandSearchRepository.findOne(testBrand.getId());
        assertThat(brandEs).isEqualToIgnoringGivenFields(testBrand);
    }

    @Test
    @Transactional
    public void createBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand with an existing ID
        brand.setId(1L);
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRepository.findAll().size();
        // set the field null
        brand.setName(null);

        // Create the Brand, which fails.
        BrandDTO brandDTO = brandMapper.toDto(brand);

        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandDTO)))
            .andExpect(status().isBadRequest());

        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrands() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList
        restBrandMockMvc.perform(get("/api/brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN.toString())))
            .andExpect(jsonPath("$.[*].legalStatus").value(hasItem(DEFAULT_LEGAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void getBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET.toString()))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN.toString()))
            .andExpect(jsonPath("$.legalStatus").value(DEFAULT_LEGAL_STATUS.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)));
    }

    @Test
    @Transactional
    public void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);
        brandSearchRepository.save(brand);
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand
        Brand updatedBrand = brandRepository.findOne(brand.getId());
        // Disconnect from session so that the updates on updatedBrand are not directly saved in db
        em.detach(updatedBrand);
        updatedBrand
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .siret(UPDATED_SIRET)
            .siren(UPDATED_SIREN)
            .legalStatus(UPDATED_LEGAL_STATUS)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE);
        BrandDTO brandDTO = brandMapper.toDto(updatedBrand);

        restBrandMockMvc.perform(put("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandDTO)))
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBrand.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBrand.getSiret()).isEqualTo(UPDATED_SIRET);
        assertThat(testBrand.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testBrand.getLegalStatus()).isEqualTo(UPDATED_LEGAL_STATUS);
        assertThat(testBrand.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBrand.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);

        // Validate the Brand in Elasticsearch
        Brand brandEs = brandSearchRepository.findOne(testBrand.getId());
        assertThat(brandEs).isEqualToIgnoringGivenFields(testBrand);
    }

    @Test
    @Transactional
    public void updateNonExistingBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Create the Brand
        BrandDTO brandDTO = brandMapper.toDto(brand);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandMockMvc.perform(put("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandDTO)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);
        brandSearchRepository.save(brand);
        int databaseSizeBeforeDelete = brandRepository.findAll().size();

        // Get the brand
        restBrandMockMvc.perform(delete("/api/brands/{id}", brand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandExistsInEs = brandSearchRepository.exists(brand.getId());
        assertThat(brandExistsInEs).isFalse();

        // Validate the database is empty
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);
        brandSearchRepository.save(brand);

        // Search the brand
        restBrandMockMvc.perform(get("/api/_search/brands?query=id:" + brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET.toString())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN.toString())))
            .andExpect(jsonPath("$.[*].legalStatus").value(hasItem(DEFAULT_LEGAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brand.class);
        Brand brand1 = new Brand();
        brand1.setId(1L);
        Brand brand2 = new Brand();
        brand2.setId(brand1.getId());
        assertThat(brand1).isEqualTo(brand2);
        brand2.setId(2L);
        assertThat(brand1).isNotEqualTo(brand2);
        brand1.setId(null);
        assertThat(brand1).isNotEqualTo(brand2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandDTO.class);
        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        BrandDTO brandDTO2 = new BrandDTO();
        assertThat(brandDTO1).isNotEqualTo(brandDTO2);
        brandDTO2.setId(brandDTO1.getId());
        assertThat(brandDTO1).isEqualTo(brandDTO2);
        brandDTO2.setId(2L);
        assertThat(brandDTO1).isNotEqualTo(brandDTO2);
        brandDTO1.setId(null);
        assertThat(brandDTO1).isNotEqualTo(brandDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(brandMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(brandMapper.fromId(null)).isNull();
    }
}
